package com.zhei.iquiubo.core.domain.model
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Biotech
import androidx.compose.material.icons.rounded.Bloodtype
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Coronavirus
import androidx.compose.material.icons.rounded.DeviceHub
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Grain
import androidx.compose.material.icons.rounded.Inventory2
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Medication
import androidx.compose.material.icons.rounded.MonitorHeart
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.SyncAlt
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector


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
    val icon: ImageVector,
    //val thresholds: BiomarkerThreshold
) {

    GLUCOSE_POSTPRANDIAL(
        key = "glucose_postprandial",
        displayName = "Glucosa Postprandial",
        compactName = "Glucosa Postprandial",
        unit = "mg/dL",
        icon = Icons.Rounded.WaterDrop
    ),

    GLUCOSE_FASTING(
        key = "glucose_fasting",
        displayName = "Glucosa en ayunas",
        compactName = "Glucosa en ayunas",
        unit = "mg/dL",
        icon = Icons.Rounded.WaterDrop
    ),

    HBA1C(
        key = "hba1c",
        displayName = "Hemoglobina glicosilada (HbA1c)",
        compactName = "HbA1c",
        unit = "%",
        Icons.Rounded.MonitorHeart
    ),

    // --------------------- Perfil lipídico

    LDL(
        key = "ldl",
        displayName = "Colesterol LDL",
        compactName = "LDL",
        unit = "mg/dL",
        icon = Icons.Rounded.FavoriteBorder
    ),

    HDL(
        key = "hdl",
        displayName = "Colesterol HDL",
        compactName = "HDL",
        unit = "mg/dL",
        icon = Icons.Rounded.FavoriteBorder
    ),

    TOTAL_CHOLESTEROL(
        key = "total_cholesterol",
        displayName = "Colesterol total",
        compactName = "Colesterol total",
        unit = "mg/dL",
        icon = Icons.Rounded.FavoriteBorder
    ),

    TRIGLYCERIDES(
        key = "triglycerides",
        displayName = "Triglicéridos",
        compactName = "Triglicéridos",
        unit = "mg/dL",
        icon = Icons.Rounded.FavoriteBorder
    ),

    VLDL(
        key = "vldl",
        displayName = "Colesterol VLDL",
        compactName = "VLDL",
        unit = "mg/dL",
        icon = Icons.Rounded.FavoriteBorder
    ),

    // --------------------- Función renal

    CREATININE(
        key = "creatinine",
        displayName = "Creatinina",
        compactName = "Creatinina",
        unit = "mg/dL",
        icon = Icons.Rounded.Science
    ),

    UREA(
        key = "urea",
        displayName = "Urea",
        compactName = "Urea",
        unit = "mg/dL",
        icon = Icons.Rounded.Science
    ),

    BUN(
        key = "bun",
        displayName = "Nitrógeno ureico (BUN)",
        compactName = "BUN",
        unit = "mg/dL",
        icon = Icons.Rounded.Science
    ),

    URIC_ACID(
        key = "uric_acid",
        displayName = "Ácido úrico",
        compactName = "Ácido úrico",
        unit = "mg/dL",
        icon = Icons.Rounded.Science
    ),

    EGFR(
        key = "egfr",
        displayName = "TFG estimada (eGFR)",
        compactName = "eGFR",
        unit = "mL/min/1.73m²",
        icon = Icons.Rounded.FilterAlt
    ),

    // --------------------- Función hepática

    ALT(
        key = "alt",
        displayName = "Alanina aminotransferasa (ALT)",
        compactName = "ALT",
        unit = "U/L",
        icon = Icons.Rounded.Biotech
    ),

    AST(
        key = "ast",
        displayName = "Aspartato aminotransferasa (AST)",
        compactName = "AST",
        unit = "U/L",
        Icons.Rounded.Biotech
    ),

    GGT(
        key = "ggt",
        displayName = "Gamma-glutamil transferasa (GGT)",
        compactName = "GGT",
        unit = "U/L",
        Icons.Rounded.Biotech
    ),

    ALP(
        key = "alp",
        displayName = "Fosfatasa alcalina",
        compactName = "FA",
        unit = "U/L",
        Icons.Rounded.Biotech
    ),

    TOTAL_BILIRUBIN(
        key = "total_bilirubin",
        displayName = "Bilirrubina total",
        compactName = "Bilirrubina",
        unit = "mg/dL",
        icon = Icons.Rounded.Bloodtype
    ),

    DIRECT_BILIRUBIN(
        key = "direct_bilirubin",
        displayName = "Bilirrubina directa",
        compactName = "Bilirrubina D",
        unit =  "mg/dL",
        icon = Icons.Rounded.Bloodtype
    ),

    ALBUMIN(
        key = "albumin",
        displayName = "Albúmina",
        compactName = "Albúmina",
        unit = "g/dL",
        icon = Icons.Rounded.Medication
    ),

    TOTAL_PROTEINS(
        key = "total_proteins",
        displayName = "Proteínas totales",
        compactName = "Proteinas",
        unit = "g/dL",
        icon = Icons.Rounded.Medication
    ),

    // --------------------- Electrolitos

    SODIUM(
        key = "sodium",
        displayName = "Sodio",
        compactName = "Na",
        unit = "mmol/L",
        icon = Icons.Rounded.Bolt
    ),

    POTASSIUM(
        key = "potassium",
        displayName = "Potasio",
        compactName = "K",
        unit = "mmol/L",
        icon = Icons.Rounded.Bolt
    ),

    CHLORIDE(
        key = "chloride",
        displayName = "Cloro",
        compactName = "Cl",
        unit = "mmol/L",
        icon = Icons.Rounded.Bolt
    ),

    CALCIUM(
        key = "calcium",
        displayName = "Calcio",
        compactName = "Calcio",
        unit = "mg/dL",
        icon = Icons.Rounded.Circle
    ),

    MAGNESIUM(
        key = "magnesium",
        displayName = "Magnesio",
        compactName = "Magnesio",
        unit = "mg/dL",
        icon = Icons.Rounded.Circle
    ),

    PHOSPHORUS(
        key = "phosphorus",
        displayName = "Fósforo",
        compactName = "Fósforo",
        unit = "mg/dL",
        icon = Icons.Rounded.Circle
    ),

    // --------------------- Hemograma

    HEMOGLOBIN(
        key = "hemoglobin",
        displayName = "Hemoglobina",
        compactName = "Hb",
        unit = "g/dL",
        icon = Icons.Rounded.Bloodtype
    ),

    HEMATOCRIT(
        key = "hematocrit",
        displayName = "Hematocrito",
        compactName = "Hto",
        unit = "%",
        icon = Icons.Rounded.Bloodtype
    ),

    ERYTHROCYTES(
        key = "erythrocytes",
        displayName = "Eritrocitos",
        compactName = "RBC",
        unit = "millones/µL",
        icon = Icons.Rounded.Bloodtype
    ),

    LEUKOCYTES(
        key = "leukocytes",
        displayName = "Leucocitos",
        compactName = "WBC",
        unit = "/µL",
        icon = Icons.Rounded.Coronavirus
    ),

    PLATELETS(
        key = "platelets",
        displayName = "Plaquetas",
        compactName = "Plaquetas",
        unit = "/µL",
        icon = Icons.Rounded.Grain
    ),

    MCV(
        key = "mcv",
        displayName = "Volumen corpuscular medio",
        compactName = "VCM",
        unit = "fL",
        icon = Icons.Rounded.Analytics
    ),

    MCH(
        key = "mch",
        displayName = "Hemoglobina corpuscular media",
        compactName = "HCM",
        unit = "pg",
        icon = Icons.Rounded.Analytics
    ),

    MCHC(
        key = "mchc",
        displayName = "Concentración HCM",
        compactName = "CHCM",
        unit = "g/dL",
        icon = Icons.Rounded.Analytics
    ),

    // --------------------- Inflamación

    CRP(
        key = "crp",
        displayName = "Proteína C Reactiva (PCR)",
        compactName = "PCR",
        unit = "mg/L",
        icon = Icons.Rounded.LocalFireDepartment
    ),

    ESR(
        key = "esr",
        displayName = "Velocidad de sedimentación (VSG)",
        compactName = "VSG",
        unit = "mm/h",
        icon = Icons.Rounded.Timeline
    ),

    // --------------------- Hierro

    IRON(
        key = "iron",
        displayName = "Hierro sérico",
        compactName = "Hierro",
        unit = "µg/dL",
        icon = Icons.Rounded.FitnessCenter
    ),

    FERRITIN(
        key = "ferritin",
        displayName = "Ferritina",
        compactName = "Ferritina",
        unit = "ng/mL",
        icon = Icons.Rounded.Inventory2
    ),

    TRANSFERRIN(
        key = "transferrin",
        displayName = "Transferrina",
        compactName = "Transferrina",
        unit = "mg/dL",
        icon = Icons.Rounded.SyncAlt
    ),

    // --------------------- Tiroides

    TSH(
        key = "tsh",
        displayName = "TSH",
        compactName = "TSH",
        unit = "µIU/mL",
        icon = Icons.Rounded.Psychology
    ),

    T3(
        key = "t3",
        displayName = "Triyodotironina (T3)",
        compactName = "T3",
        unit = "ng/dL",
        icon = Icons.Rounded.Psychology
    ),

    FREE_T3(
        key = "free_t3",
        displayName = "T3 Libre",
        compactName = "T3 Libre",
        unit = "pg/mL",
        icon = Icons.Rounded.Psychology
    ),

    T4(
        key = "t4",
        displayName = "Tiroxina (T4)",
        compactName = "T4",
        unit = "µg/dL",
        icon = Icons.Rounded.Psychology
    ),

    FREE_T4(
        key = "free_t4",
        displayName = "T4 Libre",
        compactName = "T4 Libre",
        unit = "ng/dL",
        icon = Icons.Rounded.Psychology
    ),

    // --------------------- Vitaminas

    VITAMIN_D(
        key = "vitamin_d",
        displayName = "Vitamina D",
        compactName = "Vit. D",
        unit = "ng/mL",
        icon = Icons.Rounded.WbSunny
    ),

    VITAMIN_B12(
        key = "vitamin_b12",
        displayName = "Vitamina B12",
        compactName = "B12",
        unit = "pg/mL",
        icon = Icons.Rounded.EnergySavingsLeaf
    ),

    FOLIC_ACID(
        key = "folic_acid",
        displayName = "Ácido fólico",
        compactName = "Folato",
        unit = "ng/mL",
        icon = Icons.Rounded.Spa
    ),

    // --------------------- Vitaminas

    INSULIN(
        key = "insulin",
        displayName = "Insulina",
        compactName = "Insulina",
        unit = "µIU/mL",
        icon = Icons.Rounded.MonitorHeart
    ),

    C_PEPTIDE(
        key = "c_peptide",
        displayName = "Péptido C",
        compactName = "Péptido C",
        unit = "ng/mL",
        icon = Icons.Rounded.DeviceHub
    ),

    CORTISOL(
        key = "cortisol",
        displayName = "Cortisol",
        compactName = "Cortisol",
        unit = "µg/dL",
        icon = Icons.Rounded.Bedtime
    ),

    // --------------------- Coagulación

    PT(
        key = "pt",
        displayName = "Tiempo de Protrombina",
        compactName = "TP",
        unit = "s",
        icon = Icons.Rounded.Schedule
    ),

    INR(
        key = "inr",
        displayName = "INR",
        compactName = "INR",
        unit = "",
        icon = Icons.Rounded.Schedule
    ),

    APTT(
        key = "aptt",
        displayName = "Tiempo parcial de tromboplastina",
        compactName = "TPT",
        unit = "s",
        icon = Icons.Rounded.Schedule
    ),
}