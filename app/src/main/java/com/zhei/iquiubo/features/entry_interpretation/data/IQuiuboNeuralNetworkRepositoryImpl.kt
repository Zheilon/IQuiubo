package com.zhei.iquiubo.features.entry_interpretation.data
import  android.content.Context
import com.zhei.iquiubo.core.domain.model.NeuralNetConfig
import com.zhei.iquiubo.features.entry_interpretation.domain.repository.IQuiuboNeuralNetworkRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Inject
import kotlin.math.ln
import kotlin.math.pow


class IQuiuboNeuralNetworkRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): IQuiuboNeuralNetworkRepository {


    // Carga y mapea en memoria el .tflite exportado para esta versión del
    // modelo. Al ser un MappedByteBuffer, TFLite lee directamente desde el
    // archivo sin copiarlo completo a heap de la JVM.
    private val interpreter by lazy {
        Interpreter(
            loadModelFile(context, "iquiubo_priority_model_version_3.0.5.2_.tflite")
        )
    }



    /**
     * Corre una inferencia completa sobre un conjunto de features crudas.
     *
     * @param map features sin transformar, indexadas por nombre (ej.
     *   "age" to 45.0, "bmi" to 27.3, ...). [buildInputVector] se encarga
     *   de aplicar el mismo preprocesamiento (Yeo-Johnson + StandardScaler)
     *   que se usó en el entrenamiento, en el orden correcto.
     * @return arreglo de probabilidades (o logits, según la capa de salida
     *   del modelo), una por cada clase en [NeuralNetConfig.CLASSES].
     */
    override fun predict(map: Map<String, Double>): FloatArray {
        val input = arrayOf(buildInputVector(map))
        val output = Array(1) { FloatArray(NeuralNetConfig.PriorityNetNames.getList().size) }
        interpreter.run(input, output)
        return output[0]
    }



    /**
     * Replica en Kotlin la transformación Yeo-Johnson que aplicó sklearn
     * (`PowerTransformer`) durante el entrenamiento, feature por feature.
     *
     * La fórmula tiene 4 ramas: signo de `x` (positivo/negativo) cruzado
     * con el valor de `lambda` (caso general vs. los casos límite
     * lambda=0 y lambda=2, donde la potencia se reemplaza por logaritmo
     * para evitar una división por cero).
     *
     * @param x valor crudo del feature.
     * @param lambda parámetro ajustado por sklearn para ese feature
     *   específico (viene de `scaler_params.json`).
     */
    private fun yeoJohnson(x: Double, lambda: Double): Double {
        return if (x >= 0) {
            if (lambda != 0.0) ((x + 1).pow(lambda) - 1) / lambda else ln(x + 1)

        } else {
            if (lambda != 2.0) -(((-x + 1).pow(2 - lambda) - 1) / (2 - lambda)) else -ln(-x + 1)
        }
    }



    /**
     * Construye el vector de entrada en el orden exacto que espera el
     * modelo ([NeuralNetConfig.FEATURE_ORDER]), aplicando el
     * preprocesamiento correcto a cada feature:
     *
     * - Si el índice está en [NeuralNetConfig.NON_NORMALIZED_INDICES]
     *   (ej. activity_level, family_history_diabetes), se pasa el valor
     *   crudo tal cual, sin transformar.
     * - En caso contrario, se aplica primero Yeo-Johnson y luego
     *   estandarización (resta la media, divide entre la escala),
     *   usando los parámetros en el mismo orden en que sklearn los
     *   generó para los features normalizados.
     *
     * `normIdx` avanza solo cuando se procesa un feature normalizado,
     * ya que los arreglos YJ_LAMBDAS/SCALER_MEAN/SCALER_SCALE solo
     * tienen una entrada por cada feature *normalizado* (no por cada
     * feature total).
     *
     * @throws IllegalStateException si `map` no incluye alguno de los
     *   features requeridos por [NeuralNetConfig.FEATURE_ORDER].
     */
    private fun buildInputVector(map: Map<String, Double>): FloatArray {
        var normIdx = 0;
        return FloatArray(NeuralNetConfig.FEATURE_ORDER.size) { z ->
            val name = NeuralNetConfig.FEATURE_ORDER[z]
            val value = map[name] ?: error("Falta el feature '$name'")

            if (z in NeuralNetConfig.NON_NORMALIZED_INDICES) {
                value.toFloat()

            } else {
                val yj = yeoJohnson(value, NeuralNetConfig.YJ_LAMBDAS[normIdx])
                val scaled = (yj - NeuralNetConfig.SCALER_MEAN[normIdx]) / NeuralNetConfig.SCALER_SCALE[normIdx]
                normIdx ++
                scaled.toFloat()
            }
        }
    }



    /**
     * Helper estándar de Android/TFLite: abre el archivo .tflite ubicado
     * en `assets/` y lo mapea en memoria (mmap) en lugar de leerlo
     * completo a un ByteArray, lo cual es más rápido y usa menos memoria,
     * especialmente para modelos grandes.
     */
    private fun loadModelFile(context: Context, fileName: String): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(fileName)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }


}