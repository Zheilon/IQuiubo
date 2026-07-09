package com.zhei.hikibo.core.domain.model


// Filtrando las mediciones que sí tienen ese biomarcador:
// measurements.filter { it.biomarkers.containsKey("ldl") }.

enum class BioMarker(
    val key: String,
    val displayName: String,
    val unit: String
) {

    GLUCOSE_FASTING(
        key = "glucose_fasting",
        displayName = "Glucosa en ayunas",
        unit = "mg/dL"
    ),

    GLUCOSE_POSTPRANDIAL(
        key = "glucose_postprandial",
        displayName = "Glucosa Postprandial",
        unit = "mg/dL"
    ),

    HBA1C(
        key = "hba1c",
        displayName = "Hemoglobina glicosilada",
        unit = "%"
    ),

    LDL(
        key = "ldl",
        displayName = "Colesterol LDL",
        unit = "mg/dL"
    ),

    HDL(
        key = "hdl",
        displayName = "Colesterol HDL",
        unit = "mg/dL"
    ),

    TRIGLYCERIDES(
        key = "triglycerides",
        displayName = "Triglicéridos",
        unit = "mg/dL"
    )
}