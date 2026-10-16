package com.zhei.iquiubo.core.domain.model

object NeuralNetConfig {

    val FEATURE_ORDER = listOf(
        "activity_level",
        "age",
        "bmi",
        "family_history_diabetes",
        "glucose_fasting",
        "glucose_postprandial"
    )

    // índices (dentro de FEATURE_ORDER) que NO pasan por Yeo-Johnson + StandardScaler
    val NON_NORMALIZED_INDICES = setOf(0, 3)

    // age, bmi, glucose_fasting, glucose_postprandial
    val YJ_LAMBDAS = doubleArrayOf(
        0.8274008748040252,
        0.9807367437981808,
        0.898994878515299,
        0.9602696355029822
    )

    // age, bmi, glucose_fasting, glucose_postprandial
    val SCALER_MEAN = doubleArrayOf(
        29.87964948268112,
        24.454204842436436,
        76.44163780324071,
        136.8324683010734
    )

    // age, bmi, glucose_fasting, glucose_postprandial
    val SCALER_SCALE = doubleArrayOf(
        7.975207408914628,
        3.3704592457953084,
        8.368080787776725,
        24.869895910016606
    )


    enum class PriorityNetNames(val priority: String) {
        BAJA("Prioridad I"),
        MEDIA("Prioridad II"),
        ALTA("Prioridad III");

        companion object {
            fun getList(): List<String> =
                PriorityNetNames.entries.map { it.priority }
        }
    }

    sealed class ActivityValues(val value: String) {
        data object Baja: ActivityValues("Baja")
        data object Media: ActivityValues("Media")
        data object Alta: ActivityValues("Alta")
    }

    sealed class FamilyHistoryValues(val value: String) {
        data object No: FamilyHistoryValues("No")
        data object Yes: FamilyHistoryValues("Sí")
    }
}