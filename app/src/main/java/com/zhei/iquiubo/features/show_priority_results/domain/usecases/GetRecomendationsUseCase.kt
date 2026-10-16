package com.zhei.iquiubo.features.show_priority_results.domain.usecases
import com.zhei.iquiubo.core.domain.model.NeuralNetConfig
import com.zhei.iquiubo.features.show_priority_results.domain.models.RecomendationsAreaUIModel
import javax.inject.Inject


class GetRecomendationsUseCase @Inject constructor() {


    operator fun invoke(priority: String?): RecomendationsAreaUIModel? {

        val value = when(priority) {

            NeuralNetConfig.PriorityNetNames.BAJA.priority -> {
                RecomendationsAreaUIModel(
                    title = "Seguimiento preventivo",
                    objective = "Mantener hábitos saludables y continuar con los controles preventivos.",
                    recomendations = listOf(
                        "Mantén una alimentación equilibrada, priorizando verduras, frutas enteras, legumbres y cereales integrales.",
                        "Realiza actividad física regularmente, adaptada a tu condición física.",
                        "Asiste a tus controles médicos habituales y consulta cuándo corresponde realizar nuevamente la prueba de HbA1c",
                        "Si presentas factores de riesgo para diabetes, consulta con un profesional de la salud sobre la necesidad y oportunidad del examen."
                    ),
                    uiMessage = "Tu resultado indica una prioridad baja dentro del modelo. Manten tus hábitos saludables y continúa con tus controles preventivos."
                )
            }

            NeuralNetConfig.PriorityNetNames.MEDIA.priority -> {
                RecomendationsAreaUIModel(
                    title = "Valoración preventiva",
                    objective = "Favorecer la evaluación oportuna y reducir factores de riesgo modificables.",
                    recomendations = listOf(
                        "Programa una consulta médica para evaluar tus factores de riesgo y determinar cuándo realizar la prueba de HbA1c.",
                        "Manten una alimentación equilibrada y reduce el consumo frecuente de bebidas azucaradas y alimentos ultraprocesados.",
                        "Incorpora actividad física regular, de acuerdo con tus capacidades y recomendaciones médicas",
                        "Si tienes resultados previos de glucosa alterados, compártelos con el profesional de salud para facilitar la valoración."
                    ),
                    uiMessage = "Tu resultado sugiere una prioridad intermedia. Considera una valoración médica para determinar la necesidad del examen HbA1c y los pasos de seguimiento adecuados."
                )
            }

            NeuralNetConfig.PriorityNetNames.ALTA.priority -> {
                RecomendationsAreaUIModel(
                    title = "Atención prioritaria",
                    objective = "Facilitar una valoración clínica oportuna y evitar retrasos innecesarios en la evaluación.",
                    recomendations = listOf(
                        "Contacta a un profesional o servicio de salud para gestionar la valoración y determinar la realización del examen HbA1c lo antes posible",
                        "Lleva los resultados disponibles de glucosa, antecedentes familiares relevantes y otros examenes previos",
                        "Sigue las indicaciones del profesional sobre pruebas complementarias y próximos controles",
                        "¡No esperes a presentar síntomas para solicitar orientación médica!"
                    ),
                    uiMessage = "Tu resultado indica una prioridad alta según la red neuronal. Se recomienda gestionar una valoración clínica oportuna para determinar los siguientes pasos."
                )
            }

            else -> null
        }

        return value
    }
}