package com.zhei.hikibo.core.domain.model


// Filtrando las mediciones que sí tienen ese biomarcador:
// measurements.filter { it.biomarkers.containsKey("ldl") }.

/**
 * Al usar los thresholds se debe usar el operador logico (<),
 * menor que.
 * */
enum class BioMarker(
    val key: String,
    val displayName: String,
    val compactName: String,
    val unit: String,
    // Esto se va a aplicar en logica de UI
    val selected: Boolean = false
) {

    GLUCOSE_FASTING(
        key = "glucose_fasting",
        displayName = "Glucosa en ayunas",
        compactName = "Glucosa en ayunas",
        unit = "mg/dL",
    ),

    GLUCOSE_POSTPRANDIAL(
        key = "glucose_postprandial",
        displayName = "Glucosa Postprandial",
        compactName = "Glucosa Postprandial",
        unit = "mg/dL"
    ),

    HBA1C(
        key = "hba1c",
        displayName = "Hemoglobina glicosilada (HbA1c)",
        compactName = "HbA1c",
        unit = "%"
    ),

    LDL(
        key = "ldl",
        displayName = "Colesterol LDL",
        compactName = "LDL",
        unit = "mg/dL"
    ),

    HDL(
        key = "hdl",
        displayName = "Colesterol HDL",
        compactName = "HDL",
        unit = "mg/dL"
    ),

    COLESTEROL_TOTAL(
        key = "colesterol_total",
        displayName = "Colesterol total",
        compactName = "Colesterol total",
        unit = "mg/dL"
    ),

    TRIGLYCERIDES(
        key = "triglycerides",
        displayName = "Triglicéridos",
        compactName = "Triglicéridos",
        unit = "mg/dL"
    )
}