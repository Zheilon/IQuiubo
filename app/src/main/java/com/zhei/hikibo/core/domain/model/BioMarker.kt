package com.zhei.hikibo.core.domain.model

import androidx.compose.ui.input.key.Key


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
) {

    GLUCOSE_POSTPRANDIAL(
        key = "glucose_postprandial",
        displayName = "Glucosa Postprandial",
        compactName = "Glucosa Postprandial",
        unit = "mg/dL"
    ),

    GLUCOSE_FASTING(
        key = "glucose_fasting",
        displayName = "Glucosa en ayunas",
        compactName = "Glucosa en ayunas",
        unit = "mg/dL",
    ),

    HBA1C(
        key = "hba1c",
        displayName = "Hemoglobina glicosilada (HbA1c)",
        compactName = "HbA1c",
        unit = "%"
    ),

    // --------------------- Perfil lipídico

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
    ),

    VLDL(
        key = "vldl",
        displayName = "Colesterol VLDL",
        compactName = "VLDL",
        unit = "mg/dL"
    ),

    // --------------------- Función renal

    CREATININE(
        key = "creatinine",
        displayName = "Creatinina",
        compactName = "Creatinina",
        unit = "mg/dL"
    ),

    UREA(
        key = "urea",
        displayName = "Urea",
        compactName = "Urea",
        unit = "mg/dL"
    ),

    BUN(
        key = "bun",
        displayName = "Nitrógeno ureico (BUN)",
        compactName = "BUN",
        unit = "mg/dL"
    ),

    URIC_ACID(
        key = "uric_acid",
        displayName = "Ácido úrico",
        compactName = "Ácido úrico",
        unit = "mg/dL"
    ),

    EGFR(
        key = "egfr",
        displayName = "TFG estimada (eGFR)",
        compactName = "eGFR",
        unit = "mL/min/1.73m²"
    ),

    // --------------------- Función hepática

    ALT(
        key = "alt",
        displayName = "Alanina aminotransferasa (ALT)",
        compactName = "ALT",
        unit = "U/L"
    ),

    AST(
        key = "ast",
        displayName = "Aspartato aminotransferasa (AST)",
        compactName = "AST",
        unit = "U/L"
    ),

    GGT(
        key = "ggt",
        displayName = "Gamma-glutamil transferasa (GGT)",
        compactName = "GGT",
        unit = "U/L"
    ),

    ALP(
        key = "alp",
        displayName = "Fosfatasa alcalina",
        compactName = "FA",
        unit = "U/L"
    ),

    TOTAL_BILIRUBIN(
        key = "total_bilirubin",
        displayName = "Bilirrubina total",
        compactName = "Bilirrubina",
        unit = "mg/dL"
    ),

    DIRECT_BILIRUBIN(
        key = "direct_bilirubin",
        displayName = "Bilirrubina directa",
        compactName = "Bilirrubina D",
        unit =  "mg/dL"
    ),

    ALBUMIN(
        key = "albumin",
        displayName = "Albúmina",
        compactName = "Albúmina",
        unit = "g/dL"
    ),

    TOTAL_PROTEINS(
        key = "total_proteins",
        displayName = "Proteínas totales",
        compactName = "Proteinas",
        unit = "g/dL"
    ),

    // --------------------- Electrolitos

    SODIUM(
        key = "sodium",
        displayName = "Sodio",
        compactName = "Na",
        unit = "mmol/L"
    ),

    POTASSIUM(
        key = "potassium",
        displayName = "Potasio",
        compactName = "K",
        unit = "mmol/L"
    ),

    CHLORIDE(
        key = "chloride",
        displayName = "Cloro",
        compactName = "Cl",
        unit = "mmol/L"
    ),

    CALCIUM(
        key = "calcium",
        displayName = "Calcio",
        compactName = "Calcio",
        unit = "mg/dL"
    ),

    MAGNESIUM(
        key = "magnesium",
        displayName = "Magnesio",
        compactName = "Magnesio",
        unit = "mg/dL"
    ),

    PHOSPHORUS(
        key = "phosphorus",
        displayName = "Fósforo",
        compactName = "Fósforo",
        unit = "mg/dL"
    ),

    // --------------------- Hemograma

    HEMOGLOBIN(
        key = "hemoglobin",
        displayName = "Hemoglobina",
        compactName = "Hb",
        unit = "g/dL"
    ),

    HEMATOCRIT(
        key = "hematocrit",
        displayName = "Hematocrito",
        compactName = "Hto",
        unit = "%"
    ),

    ERYTHROCYTES(
        key = "erythrocytes",
        displayName = "Eritrocitos",
        compactName = "RBC",
        unit = "millones/µL"
    ),

    LEUKOCYTES(
        key = "leukocytes",
        displayName = "Leucocitos",
        compactName = "WBC",
        unit = "/µL"
    ),

    PLATELETS(
        key = "platelets",
        displayName = "Plaquetas",
        compactName = "Plaquetas",
        unit = "/µL"
    ),

    MCV(
        key = "mcv",
        displayName = "Volumen corpuscular medio",
        compactName = "VCM",
        unit = "fL"
    ),

    MCH(
        key = "mch",
        displayName = "Hemoglobina corpuscular media",
        compactName = "HCM",
        unit = "pg"
    ),

    MCHC(
        key = "mchc",
        displayName = "Concentración HCM",
        compactName = "CHCM",
        unit = "g/dL"
    ),

    // --------------------- Inflamación

    CRP(
        key = "crp",
        displayName = "Proteína C Reactiva (PCR)",
        compactName = "PCR",
        unit = "mg/L"
    ),

    ESR(
        key = "esr",
        displayName = "Velocidad de sedimentación (VSG)",
        compactName = "VSG",
        unit = "mm/h"
    ),

    // --------------------- Hierro

    IRON(
        key = "iron",
        displayName = "Hierro sérico",
        compactName = "Hierro",
        unit = "µg/dL"
    ),

    FERRITIN(
        key = "ferritin",
        displayName = "Ferritina",
        compactName = "Ferritina",
        unit = "ng/mL"
    ),

    TRANSFERRIN(
        key = "transferrin",
        displayName = "Transferrina",
        compactName = "Transferrina",
        unit = "mg/dL"
    ),

    // --------------------- Tiroides

    TSH(
        key = "tsh",
        displayName = "TSH",
        compactName = "TSH",
        unit = "µIU/mL"
    ),

    T3(
        key = "t3",
        displayName = "Triyodotironina (T3)",
        compactName = "T3",
        unit = "ng/dL"
    ),

    FREE_T3(
        key = "free_t3",
        displayName = "T3 Libre",
        compactName = "T3 Libre",
        unit = "pg/mL"
    ),

    T4(
        key = "t4",
        displayName = "Tiroxina (T4)",
        compactName = "T4",
        unit = "µg/dL"
    ),

    FREE_T4(
        key = "free_t4",
        displayName = "T4 Libre",
        compactName = "T4 Libre",
        unit = "ng/dL"
    ),

    // --------------------- Vitaminas

    VITAMIN_D(
        key = "vitamin_d",
        displayName = "Vitamina D",
        compactName = "Vit. D",
        unit = "ng/mL"
    ),

    VITAMIN_B12(
        key = "vitamin_b12",
        displayName = "Vitamina B12",
        compactName = "B12",
        unit = "pg/mL"
    ),

    FOLIC_ACID(
        key = "folic_acid",
        displayName = "Ácido fólico",
        compactName = "Folato",
        unit = "ng/mL"
    ),

    // --------------------- Vitaminas

    INSULIN(
        key = "insulin",
        displayName = "Insulina",
        compactName = "Insulina",
        unit = "µIU/mL"
    ),

    C_PEPTIDE(
        key = "c_peptide",
        displayName = "Péptido C",
        compactName = "Péptido C",
        unit = "ng/mL"
    ),

    CORTISOL(
        key = "cortisol",
        displayName = "Cortisol",
        compactName = "Cortisol",
        unit = "µg/dL"
    ),

    // --------------------- Coagulación

    PT(
        key = "pt",
        displayName = "Tiempo de Protrombina",
        compactName = "TP",
        unit = "s"
    ),

    INR(
        key = "inr",
        displayName = "INR",
        compactName = "INR",
        unit = ""
    ),

    APTT(
        key = "aptt",
        displayName = "Tiempo parcial de tromboplastina",
        compactName = "TPT",
        unit = "s"
    ),
}