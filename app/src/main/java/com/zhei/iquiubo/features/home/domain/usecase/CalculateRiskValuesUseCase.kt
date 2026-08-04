package com.zhei.iquiubo.features.home.domain.usecase
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.zhei.iquiubo.features.home.domain.model.RiskBuild
import com.zhei.iquiubo.features.home.domain.model.RiskDirection
import com.zhei.iquiubo.features.home.domain.model.RiskThreshold
import com.zhei.iquiubo.core.domain.repository.MeasurementRepository
import com.zhei.iquiubo.core.domain.repository.UserRepository
import com.zhei.iquiubo.features.home.domain.model.RiskGradient
import javax.inject.Inject

class CalculateRiskValuesUseCase @Inject constructor(
    private val measurementRepo: MeasurementRepository,
    private val userRepo: UserRepository
) {

    /**
     * Esta función debe retornar un list<RiskBuild>
     * */
    suspend operator fun invoke(userId: String): List<RiskBuild> {

        val biomarkers = measurementRepo.getLastMeasurementByUserId(userId)
        val sex = userRepo.getUser(userId)?.sex ?: ""

        // first of all, voy a crear la lista de thresholds
        val main = biomarkers.bioMarkers.map { (key, value) ->
            Pair(value, RiskThreshold.getThrsBySex(key, sex))
        }


        // Comienzo iterando en (main)
        val arr = main.map { (examValue, riskThrs) ->

            // Declaro esta variable desde este alcance para
            // luego utilizarla dentro del when.

            var label = ""

            // Primero se elabora la programación para obtener el progreso
            // por examen respecto a los biomarcadores entrantes.

            val progress = with(riskThrs) {

                // Poner la dirección primero, pues la dirección
                // hace que el orden varie

                when(direction) {

                    RiskDirection.HIGHER_IS_WORSE -> {
                        when {
                            examValue < normalMin -> {
                                val pgress = ((examValue - normalMin) / (normalMax - normalMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.low
                                (pgress * 0.33)
                            }

                            examValue <= normalMax -> {
                                val pgress = ((examValue - normalMin) / (normalMax - normalMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.normal
                                (pgress * 0.33)
                            }

                            examValue <= elevateMax -> {
                                val pgress = ((examValue - elevateMin) / (elevateMax - elevateMin))
                                    .coerceIn(0.0, 1.0)
                                label = if (pgress <= 0.15) literature.beforeElevate
                                else literature.elevate
                                (pgress * 0.33) + 0.33
                            }

                            examValue <= highMax -> {
                                val pgress = ((examValue - highMin) / (highMax - highMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.high
                                (pgress * 0.34) + 0.66
                            }

                            else -> {
                                label = literature.high
                                1.0
                            }
                        }
                    }

                    RiskDirection.LOWER_IS_WORSE -> {
                        when {
                            examValue < highMin -> {
                                val pgress = ((examValue - highMin) / (highMax - highMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.low
                                (pgress * 0.33)
                            }

                            examValue <= highMax -> {
                                val pgress = ((examValue - highMin) / (highMax - highMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.low
                                (pgress * 0.33)
                            }

                            examValue <= elevateMax -> {
                                val pgress = ((examValue - elevateMin) / (elevateMax - elevateMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.normal
                                (pgress * 0.33) + 0.33
                            }

                            examValue <= normalMax -> {
                                val pgress = ((examValue - normalMin) / (normalMax - normalMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.elevate
                                (pgress * 0.34) + 0.66
                            }

                            else -> {
                                label = literature.high
                                1.0
                            }
                        }
                    }

                    RiskDirection.BOTH_EXTREMES -> {
                        when {
                            // Zona baja: entre más lejos por debajo de normalMin, más cerca de 0.0 (peor)
                            examValue < normalMin -> {
                                val pgress = ((examValue - normalMin) / (normalMax - normalMin))
                                    .coerceIn(-1.0, 0.0)
                                label = literature.low
                                (0.5 + pgress).coerceIn(0.0, 1.0) * 0.4
                            }

                            // Zona normal: centrada exactamente entre 0.4 y 0.6
                            examValue <= normalMax -> {
                                val pgress = ((examValue - normalMin) / (normalMax - normalMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.normal
                                0.4 + (pgress * 0.2)
                            }

                            examValue <= elevateMax -> {
                                val pgress = ((examValue - elevateMin) / (elevateMax - elevateMin))
                                    .coerceIn(0.0, 1.0)
                                label = if (pgress <= 0.15) literature.beforeElevate else literature.elevate
                                0.6 + (pgress * 0.2)
                            }

                            examValue <= highMax -> {
                                val pgress = ((examValue - highMin) / (highMax - highMin))
                                    .coerceIn(0.0, 1.0)
                                label = literature.high
                                0.8 + (pgress * 0.2)
                            }

                            else -> {
                                label = literature.high
                                1.0
                            }
                        }
                    }

                    else -> { 1.0 }
                }
            }

            // Como siguiente paso se realizará la obtención del color
            // que se encuentra en ese rango.

            val (listColor, color) = RiskGradient.getColor(progress, riskThrs.direction)

            // Por último se crea un objeto RiskBuild personalizado
            // con cada información del exámen del usuario.
            RiskBuild(
                exam = riskThrs.keyName,
                value = examValue,
                progress = progress,
                color = color,
                label = label,
                gradientList = listColor
            )
        }

        Log.e("TEST RISK VALUES", arr.toString())

        return arr
    }
}