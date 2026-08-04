package com.zhei.iquiubo.features.home.domain.model

import com.zhei.iquiubo.core.domain.model.BioMarker

enum class RiskLiterature (
    val keyName: String,
    val low: String,
    val normal: String,
    val beforeElevate: String,
    val elevate: String,
    val high: String
) {

    Default(
        keyName = "",
        low = "",
        normal = "",
        beforeElevate = "",
        elevate = "",
        high = ""
    ),

    GlucoseFasting(
        keyName = BioMarker.GLUCOSE_FASTING.displayName,
        low = "Hipoglucemia",
        normal = "Optimo",
        beforeElevate = "Ligeramente elevado",
        elevate = "Elevado",
        high = "Alto"
    ),

    GlucosePostprandial(
        keyName = BioMarker.GLUCOSE_POSTPRANDIAL.displayName,
        low = "Hipoglucemia postprandial",
        normal = "Normoglucemia postprandial",
        beforeElevate = "Leve intolerancia a la glucosa",
        elevate = "Intolerancia a la glucosa",
        high = "Hiperglucemia postprandial"
    ),

    HBA1C(
        keyName = BioMarker.HBA1C.displayName,
        low = "HbA1c disminuida",
        normal = "Control glucémico normal",
        beforeElevate = "Prediabetes inicial",
        elevate = "Prediabetes",
        high = "Diabetes mellitus"
    ),

    LDL(
        keyName = BioMarker.LDL.displayName,
        low = "Bajo",
        normal = "Optimo",
        beforeElevate = "LDL ligeramente elevado",
        elevate = "Elevado",
        high = "Hipercolesterolemia"
    ),

    HDL(
        keyName = BioMarker.HDL.displayName,
        low = "Muy bajo (Mayor riesgo cardiovascular)",
        normal = "Bajo",
        beforeElevate = "",
        elevate = "HDL protector",
        high = "Muy elevado"
    ),

    TotalCholesterol(
        keyName = BioMarker.TOTAL_CHOLESTEROL.displayName,
        low = "Colesterol bajo",
        normal = "Deseable",
        beforeElevate = "Ligeramente elevado",
        elevate = "Colesterol elevado",
        high = "Hipercolesterolemia"
    ),

    Triglycerides(
        keyName = BioMarker.TRIGLYCERIDES.displayName,
        low = "Triglicéridos bajos",
        normal = "Normal",
        beforeElevate = "Ligeramente elevados",
        elevate = "Hipertrigliceridemia",
        high = "Hipertrigliceridemia severa"
    ),

    VLDL(
        keyName = BioMarker.VLDL.displayName,
        low = "VLDL bajo",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "VLDL elevado",
        high = "VLDL muy elevado"
    ),

    Urea(
        keyName = BioMarker.UREA.displayName,
        low = "Urea baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Urea elevada",
        high = "Azotemia"
    ),

    Bun(
        keyName = BioMarker.BUN.displayName,
        low = "BUN bajo",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "BUN elevado",
        high = "Azotemia"
    ),

    EGFR(
        keyName = BioMarker.EGFR.displayName,
        low = "Enfermedad renal crónica",
        normal = "Función renal normal",
        beforeElevate = "",
        elevate = "Función renal levemente disminuida",
        high = "Enfermedad renal crónica avanzada"
    ),

    ALT(
        keyName = BioMarker.ALT.displayName,
        low = "ALT baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Lesión hepática leve",
        high = "Lesión hepática significativa"
    ),

    AST(
        keyName = BioMarker.AST.displayName,
        low = "AST baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Lesión tisular leve",
        high = "Lesión tisular significativa"
    ),

    ALP(
        keyName = BioMarker.ALP.displayName,
        low = "Fosfatasa alcalina baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Colestasis probable",
        high = "Colestasis significativa"
    ),

    TotalBilirubin(
        keyName = BioMarker.TOTAL_BILIRUBIN.displayName,
        low = "Bilirrubina baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Hiperbilirrubinemia",
        high = "Hiperbilirrubinemia significativa"
    ),

    DirectBilirubin(
        keyName = BioMarker.DIRECT_BILIRUBIN.displayName,
        low = "Bilirrubina directa baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Hiperbilirrubinemia conjugada",
        high = "Colestasis significativa"
    ),

    Albumin(
        keyName = BioMarker.ALBUMIN.displayName,
        low = "Hipoalbuminemia grave",
        normal = "Normal",
        beforeElevate = "",
        elevate = "Hipoalbuminemia leve",
        high = "Hipoalbuminemia"
    ),

    TotalProteins(
        keyName = BioMarker.TOTAL_PROTEINS.displayName,
        low = "Hipoproteinemia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevadas",
        elevate = "Hiperproteinemia",
        high = "Hiperproteinemia significativa"
    ),

    Sodium(
        keyName = BioMarker.SODIUM.displayName,
        low = "Hiponatremia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hipernatremia leve",
        high = "Hipernatremia"
    ),

    Potassium(
        keyName = BioMarker.POTASSIUM.displayName,
        low = "Hipopotasemia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hiperpotasemia leve",
        high = "Hiperpotasemia"
    ),

    Chloride(
        keyName = BioMarker.CHLORIDE.displayName,
        low = "Hipocloremia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hipercloremia leve",
        high = "Hipercloremia"
    ),

    Calcium(
        keyName = BioMarker.CALCIUM.displayName,
        low = "Hipocalcemia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hipercalcemia leve",
        high = "Hipercalcemia"
    ),

    Magnesium(
        keyName = BioMarker.MAGNESIUM.displayName,
        low = "Hipomagnesemia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hipermagnesemia leve",
        high = "Hipermagnesemia"
    ),

    Phosphorus(
        keyName = BioMarker.PHOSPHORUS.displayName,
        low = "Hipofosfatemia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevado",
        elevate = "Hiperfosfatemia leve",
        high = "Hiperfosfatemia"
    ),

    Leukocytes(
        keyName = BioMarker.LEUKOCYTES.displayName,
        low = "Leucopenia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevados",
        elevate = "Leucocitosis",
        high = "Leucocitosis marcada"
    ),

    Platelets(
        keyName = BioMarker.PLATELETS.displayName,
        low = "Trombocitopenia",
        normal = "Normal",
        beforeElevate = "Ligeramente elevadas",
        elevate = "Trombocitosis",
        high = "Trombocitosis marcada"
    ),

    MCV(
        keyName = BioMarker.MCV.displayName,
        low = "Microcitosis",
        normal = "Normocitosis",
        beforeElevate = "Ligeramente elevado",
        elevate = "Macrocitosis",
        high = "Macrocitosis marcada"
    ),

    MCH(
        keyName = BioMarker.MCH.displayName,
        low = "Hipocromía",
        normal = "Normocromía",
        beforeElevate = "Ligeramente elevada",
        elevate = "Hipercromía",
        high = "Hipercromía marcada"
    ),

    MCHC(
        keyName = BioMarker.MCHC.displayName,
        low = "Hipocromía",
        normal = "Normocromía",
        beforeElevate = "Ligeramente elevada",
        elevate = "CHCM elevada",
        high = "CHCM marcadamente elevada"
    ),

    CRP(
        keyName = BioMarker.CRP.displayName,
        low = "Muy baja",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Inflamación leve",
        high = "Inflamación significativa"
    ),

    Transferrin(
        keyName = BioMarker.TRANSFERRIN.displayName,
        low = "Transferrina disminuida",
        normal = "Normal",
        beforeElevate = "Ligeramente elevada",
        elevate = "Transferrina elevada",
        high = "Transferrina marcadamente elevada"
    ),
}