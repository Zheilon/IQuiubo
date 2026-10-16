package com.zhei.iquiubo.features.put_rna_values.domain.model

data class PriorityInputModel(
    val activityLevel: String,
    val age: Double,
    val bmi: Double,
    val familyHistoryDiabetes: String,
    val glucoseFasting: Double,
    val glucosePostprandial: Double
)
