package com.zhei.iquiubo.features.home.domain.model

import com.zhei.iquiubo.core.domain.model.BioMarker

object RiskThreshold {

    private val genericMap = Generic.entries.associateBy { it.keyName }
    private val maleMap = Male.entries.associateBy { it.keyName }
    private val femaleMap = Female.entries.associateBy { it.keyName }


    // Esta plantilla será utilizada para poder
    // determinar que tipo de examen al final debe salir.
    data class Template(
        val keyName: String = "",
        val normalMin: Float = 0.0f,
        val normalMax: Float = 0.0f,
        val elevateMin: Float = 0.0f,
        val elevateMax: Float = 0.0f,
        val highMin: Float = 0.0f,
        val highMax: Float = 0.0f,
        val direction: RiskDirection = RiskDirection.DEFAULT,
        val literature: RiskLiterature = RiskLiterature.Default
    )

    // Aqui debería entrar un valor como
    fun getThrsBySex(keyExam: String, sex: String): Template {

        if (keyExam in genericMap) {
            return genericMap[keyExam]?.let {
                Template(
                    keyName = it.keyName,
                    normalMin = it.normalMin,
                    normalMax = it.normalMax,
                    elevateMin = it.elevateMin,
                    elevateMax = it.elevateMax,
                    highMin = it.highMin,
                    highMax = it.highMax,
                    direction = it.direction,
                    literature = it.literature
                )
            }!!
        }

        if (sex == "M" && keyExam in maleMap) {
            return maleMap[keyExam]?.let {
                Template(
                    keyName = it.keyName,
                    normalMin = it.normalMin,
                    normalMax = it.normalMax,
                    elevateMin = it.elevateMin,
                    elevateMax = it.elevateMax,
                    highMin = it.highMin,
                    highMax = it.highMax,
                    direction = it.direction,
                    literature = it.literature
                )
            }!!

        } else {
            return femaleMap[keyExam]?.let {
                Template(
                    keyName = it.keyName,
                    normalMin = it.normalMin,
                    normalMax = it.normalMax,
                    elevateMin = it.elevateMin,
                    elevateMax = it.elevateMax,
                    highMin = it.highMin,
                    highMax = it.highMax,
                    direction = it.direction,
                    literature = it.literature
                )
            }!!
        }
    }

    enum class Generic(
        val keyName: String,
        val normalMin: Float,
        val normalMax: Float,
        val elevateMin: Float,
        val elevateMax: Float,
        val highMin: Float,
        val highMax: Float,
        val direction: RiskDirection,
        val literature: RiskLiterature
    ) {

        GlucoseFasting(
            keyName = BioMarker.GLUCOSE_FASTING.displayName,
            normalMin = 70f,
            normalMax = 99f,
            elevateMin = 100f,
            elevateMax = 125f,
            highMin = 126f,
            highMax = 250f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.GlucoseFasting
        ),

        GlucosePostprandial(
            keyName = BioMarker.GLUCOSE_POSTPRANDIAL.displayName,
            normalMin = 70f,
            normalMax = 139f,
            elevateMin = 140f,
            elevateMax = 199f,
            highMin = 200f,
            highMax = 300f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.GlucosePostprandial
        ),

        HBA1C(
            keyName = BioMarker.HBA1C.displayName,
            normalMin = 4.0f,
            normalMax = 5.6f,
            elevateMin = 5.7f,
            elevateMax = 6.4f,
            highMin = 6.5f,
            highMax = 15.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.HBA1C
        ),

        LDL(
            keyName = BioMarker.LDL.displayName,
            normalMin = 0f,
            normalMax = 99f,
            elevateMin = 100f,
            elevateMax = 159f,
            highMin = 160f,
            highMax = 300f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.LDL
        ),

        TotalCholesterol(
            keyName = BioMarker.TOTAL_CHOLESTEROL.displayName,
            normalMin = 0f,
            normalMax = 199f,
            elevateMin = 200f,
            elevateMax = 239f,
            highMin = 240f,
            highMax = 500f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.TotalCholesterol
        ),

        Triglycerides(
            keyName = BioMarker.TRIGLYCERIDES.displayName,
            normalMin = 0f,
            normalMax = 149f,
            elevateMin = 150f,
            elevateMax = 199f,
            highMin = 200f,
            highMax = 1000f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.Triglycerides
        ),

        VLDL(
            keyName = BioMarker.VLDL.displayName,
            normalMin = 2f,
            normalMax = 30f,
            elevateMin = 31f,
            elevateMax = 40f,
            highMin = 41f,
            highMax = 150f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.VLDL
        ),

        Urea(
            keyName = BioMarker.UREA.displayName,
            normalMin = 15f,
            normalMax = 40f,
            elevateMin = 41f,
            elevateMax = 60f,
            highMin = 61f,
            highMax = 300f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.Urea
        ),

        Bun(
            keyName = BioMarker.BUN.displayName,
            normalMin = 7f,
            normalMax = 20f,
            elevateMin = 21f,
            elevateMax = 39f,
            highMin = 40f,
            highMax = 200f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.Bun
        ),

        EGFR(
            keyName = BioMarker.EGFR.displayName,
            highMin = 0f,
            highMax = 59f,
            elevateMin = 60f,
            elevateMax = 89f,
            normalMin = 90f,
            normalMax = 120f,
            direction = RiskDirection.LOWER_IS_WORSE,
            literature = RiskLiterature.EGFR
        ),

        ALT(
            keyName = BioMarker.ALT.displayName,
            normalMin = 7f,
            normalMax = 40f,
            elevateMin = 41f,
            elevateMax = 120f,
            highMin = 121f,
            highMax = 1000f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.ALT
        ),

        AST(
            keyName = BioMarker.AST.displayName,
            normalMin = 10f,
            normalMax = 40f,
            elevateMin = 41f,
            elevateMax = 120f,
            highMin = 121f,
            highMax = 1000f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.AST
        ),

        ALP(
            keyName = BioMarker.ALP.displayName,
            normalMin = 40f,
            normalMax = 130f,
            elevateMin = 131f,
            elevateMax = 300f,
            highMin = 301f,
            highMax = 2000f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.ALP
        ),

        TotalBilirubin(
            keyName = BioMarker.TOTAL_BILIRUBIN.displayName,
            normalMin = 0.2f,
            normalMax = 1.2f,
            elevateMin = 1.3f,
            elevateMax = 2.9f,
            highMin = 3.0f,
            highMax = 30.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.TotalBilirubin
        ),

        DirectBilirubin(
            keyName = BioMarker.DIRECT_BILIRUBIN.displayName,
            normalMin = 0.0f,
            normalMax = 0.3f,
            elevateMin = 0.31f,
            elevateMax = 1.9f,
            highMin = 2.0f,
            highMax = 20.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.DirectBilirubin
        ),

        Albumin(
            keyName = BioMarker.ALBUMIN.displayName,
            highMin = 0f,
            highMax = 2.9f,
            elevateMin = 3.0f,
            elevateMax = 3.4f,
            normalMin = 3.5f,
            normalMax = 5.0f,
            direction = RiskDirection.LOWER_IS_WORSE,
            literature = RiskLiterature.Albumin
        ),

        TotalProteins(
            keyName = BioMarker.TOTAL_PROTEINS.displayName,
            normalMin = 6.0f,
            normalMax = 8.3f,
            elevateMin = 8.4f,
            elevateMax = 9.4f,
            highMin = 9.5f,
            highMax = 12.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.TotalProteins
        ),

        Sodium(
            keyName = BioMarker.SODIUM.displayName,
            normalMin = 135f,
            normalMax = 145f,
            elevateMin = 146f,
            elevateMax = 154f,
            highMin = 155f,
            highMax = 180f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Sodium
        ),

        Potassium(
            keyName = BioMarker.POTASSIUM.displayName,
            normalMin = 3.5f,
            normalMax = 5.0f,
            elevateMin = 5.1f,
            elevateMax = 5.9f,
            highMin = 6.0f,
            highMax = 10.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Potassium
        ),

        Chloride(
            keyName = BioMarker.CHLORIDE.displayName,
            normalMin = 98f,
            normalMax = 107f,
            elevateMin = 108f,
            elevateMax = 114f,
            highMin = 115f,
            highMax = 140f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Chloride
        ),

        Calcium(
            keyName = BioMarker.CALCIUM.displayName,
            normalMin = 8.5f,
            normalMax = 10.5f,
            elevateMin = 10.6f,
            elevateMax = 11.9f,
            highMin = 12.0f,
            highMax = 20.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Calcium
        ),

        Magnesium(
            keyName = BioMarker.MAGNESIUM.displayName,
            normalMin = 1.7f,
            normalMax = 2.2f,
            elevateMin = 2.3f,
            elevateMax = 2.9f,
            highMin = 3.0f,
            highMax = 6.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Magnesium
        ),

        Phosphorus(
            keyName = BioMarker.PHOSPHORUS.displayName,
            normalMin = 2.5f,
            normalMax = 4.5f,
            elevateMin = 4.6f,
            elevateMax = 5.9f,
            highMin = 6.0f,
            highMax = 10.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Phosphorus
        ),

        Leukocytes(
            keyName = BioMarker.LEUKOCYTES.displayName,
            normalMin = 4000f,
            normalMax = 11000f,
            elevateMin = 11001f,
            elevateMax = 19999f,
            highMin = 20000f,
            highMax = 100000f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Leukocytes
        ),

        Platelets(
            keyName = BioMarker.PLATELETS.displayName,
            normalMin = 150000f,
            normalMax = 450000f,
            elevateMin = 450001f,
            elevateMax = 599999f,
            highMin = 600000f,
            highMax = 1500000f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Platelets
        ),

        MCV(
            keyName = BioMarker.MCV.displayName,
            normalMin = 80f,
            normalMax = 100f,
            elevateMin = 101f,
            elevateMax = 114f,
            highMin = 115f,
            highMax = 150f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.MCV
        ),

        MCH(
            keyName = BioMarker.MCH.displayName,
            normalMin = 27f,
            normalMax = 33f,
            elevateMin = 34f,
            elevateMax = 36f,
            highMin = 37f,
            highMax = 45f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.MCH
        ),

        MCHC(
            keyName = BioMarker.MCHC.displayName,
            normalMin = 32f,
            normalMax = 36f,
            elevateMin = 36.1f,
            elevateMax = 37.9f,
            highMin = 38f,
            highMax = 45f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.MCHC
        ),

        CRP(
            keyName = BioMarker.CRP.displayName,
            normalMin = 0f,
            normalMax = 5f,
            elevateMin = 5.1f,
            elevateMax = 10f,
            highMin = 10.1f,
            highMax = 300f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.CRP
        ),

        Transferrin(
            keyName = BioMarker.TRANSFERRIN.displayName,
            normalMin = 200f,
            normalMax = 360f,
            elevateMin = 361f,
            elevateMax = 400f,
            highMin = 401f,
            highMax = 600f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Transferrin
        ),

        TSH(
            keyName = BioMarker.TSH.displayName,
            normalMin = 0.4f,
            normalMax = 4.0f,
            elevateMin = 4.1f,
            elevateMax = 10f,
            highMin = 10.1f,
            highMax = 100f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.TSH
        ),

        T3(
            keyName = BioMarker.T3.displayName,
            normalMin = 80f,
            normalMax = 200f,
            elevateMin = 201f,
            elevateMax = 250f,
            highMin = 251f,
            highMax = 500f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.T3
        ),

        FreeT3(
            keyName = BioMarker.FREE_T3.displayName,
            normalMin = 2.3f,
            normalMax = 4.2f,
            elevateMin = 4.3f,
            elevateMax = 6f,
            highMin = 6.1f,
            highMax = 15f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.FreeT3
        ),

        T4(
            keyName = BioMarker.T4.displayName,
            normalMin = 4.5f,
            normalMax = 12f,
            elevateMin = 12.1f,
            elevateMax = 15f,
            highMin = 15.1f,
            highMax = 25f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.T4
        ),

        FreeT4(
            keyName = BioMarker.FREE_T4.displayName,
            normalMin = 0.8f,
            normalMax = 1.8f,
            elevateMin = 1.81f,
            elevateMax = 2.5f,
            highMin = 2.51f,
            highMax = 5f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.FreeT4
        ),

        VitaminD(
            keyName = BioMarker.VITAMIN_D.displayName,
            normalMin = 30f,
            normalMax = 100f,
            elevateMin = 101f,
            elevateMax = 150f,
            highMin = 151f,
            highMax = 300f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.VitaminD
        ),

        VitaminB12(
            keyName = BioMarker.VITAMIN_B12.displayName,
            normalMin = 200f,
            normalMax = 900f,
            elevateMin = 901f,
            elevateMax = 1500f,
            highMin = 1501f,
            highMax = 4000f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.VitaminB12
        ),

        FolicAcid(
            keyName = BioMarker.FOLIC_ACID.displayName,
            normalMin = 4f,
            normalMax = 20f,
            elevateMin = 20.1f,
            elevateMax = 30f,
            highMin = 30.1f,
            highMax = 60f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.FolicAcid
        ),

        Insulin(
            keyName = BioMarker.INSULIN.displayName,
            normalMin = 2f,
            normalMax = 25f,
            elevateMin = 26f,
            elevateMax = 40f,
            highMin = 41f,
            highMax = 150f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Insulin
        ),

        CPeptide(
            keyName = BioMarker.C_PEPTIDE.displayName,
            normalMin = 0.8f,
            normalMax = 3.1f,
            elevateMin = 3.2f,
            elevateMax = 5.0f,
            highMin = 5.1f,
            highMax = 15f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.CPeptide
        ),

        Cortisol(
            keyName = BioMarker.CORTISOL.displayName,
            normalMin = 5f,
            normalMax = 25f,
            elevateMin = 25.1f,
            elevateMax = 35f,
            highMin = 35.1f,
            highMax = 80f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Cortisol
        ),

        PT(
            keyName = BioMarker.PT.displayName,
            normalMin = 11f,
            normalMax = 13.5f,
            elevateMin = 13.6f,
            elevateMax = 18f,
            highMin = 18.1f,
            highMax = 60f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.PT
        ),

        INR(
            keyName = BioMarker.INR.displayName,
            normalMin = 0.8f,
            normalMax = 1.2f,
            elevateMin = 1.21f,
            elevateMax = 2f,
            highMin = 2.01f,
            highMax = 10f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.INR
        ),

        APTT(
            keyName = BioMarker.APTT.displayName,
            normalMin = 25f,
            normalMax = 35f,
            elevateMin = 35.1f,
            elevateMax = 45f,
            highMin = 45.1f,
            highMax = 120f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.APTT
        ),

        BMI(
            keyName = BioMarker.BMI.displayName,
            normalMin = 18.5f,
            normalMax = 24.9f,
            elevateMin = 25.0f,
            elevateMax = 29.9f,
            highMin = 30.0f,
            highMax = 50.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.BMI
        )
    }


    enum class Male(
        val keyName: String,
        val normalMin: Float,
        val normalMax: Float,
        val elevateMin: Float,
        val elevateMax: Float,
        val highMin: Float,
        val highMax: Float,
        val direction: RiskDirection,
        val literature: RiskLiterature
    ) {

        HDL(
            keyName = BioMarker.HDL.displayName,
            highMin = 0f,
            highMax = 39f,
            elevateMin = 40f,
            elevateMax = 59f,
            normalMin = 60f,
            normalMax = 100f,
            direction = RiskDirection.LOWER_IS_WORSE,
            literature = RiskLiterature.HDL
        ),


        Creatinine(
            keyName = BioMarker.CREATININE.displayName,
            normalMin = 0.74f,
            normalMax = 1.35f,
            elevateMin = 1.36f,
            elevateMax = 1.99f,
            highMin = 2.0f,
            highMax = 15.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.Creatinine
        ),

        UricAcid(
            keyName = BioMarker.URIC_ACID.displayName,
            normalMin = 3.5f,
            normalMax = 7.2f,
            elevateMin = 7.3f,
            elevateMax = 8.9f,
            highMin = 9.0f,
            highMax = 20.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.UricAcid
        ),

        GGT(
            keyName = BioMarker.GGT.displayName,
            normalMin = 8f,
            normalMax = 61f,
            elevateMin = 62f,
            elevateMax = 180f,
            highMin = 181f,
            highMax = 1500f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.GGT
        ),

        Hemoglobin(
            keyName = BioMarker.HEMOGLOBIN.displayName,
            normalMin = 13.5f,
            normalMax = 17.5f,
            elevateMin = 17.6f,
            elevateMax = 18.9f,
            highMin = 19.0f,
            highMax = 25.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Hemoglobin
        ),

        Hematocrit(
            keyName = BioMarker.HEMATOCRIT.displayName,
            normalMin = 41.0f,
            normalMax = 53.0f,
            elevateMin = 54.0f,
            elevateMax = 59.0f,
            highMin = 60.0f,
            highMax = 75.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Hematocrit
        ),

        Erythrocytes(
            keyName = BioMarker.ERYTHROCYTES.displayName,
            normalMin = 4.5f,
            normalMax = 5.9f,
            elevateMin = 6.0f,
            elevateMax = 6.4f,
            highMin = 6.5f,
            highMax = 8.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Erythrocytes
        ),

        ESR(
            keyName = BioMarker.ESR.displayName,
            normalMin = 0f,
            normalMax = 15f,
            elevateMin = 16f,
            elevateMax = 30f,
            highMin = 31f,
            highMax = 150f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.ESR
        ),

        Iron(
            keyName = BioMarker.IRON.displayName,
            normalMin = 65f,
            normalMax = 175f,
            elevateMin = 176f,
            elevateMax = 220f,
            highMin = 221f,
            highMax = 350f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Iron
        ),

        Ferritin(
            keyName = BioMarker.FERRITIN.displayName,
            normalMin = 30f,
            normalMax = 400f,
            elevateMin = 401f,
            elevateMax = 800f,
            highMin = 801f,
            highMax = 3000f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Ferritin
        )
    }


    enum class Female(
        val keyName: String,
        val normalMin: Float,
        val normalMax: Float,
        val elevateMin: Float,
        val elevateMax: Float,
        val highMin: Float,
        val highMax: Float,
        val direction: RiskDirection,
        val literature: RiskLiterature
    ) {

        HDL(
            keyName = BioMarker.HDL.displayName,
            highMin = 0f,
            highMax = 49f,
            elevateMin = 50f,
            elevateMax = 59f,
            normalMin = 60f,
            normalMax = 100f,
            direction = RiskDirection.LOWER_IS_WORSE,
            literature = RiskLiterature.HDL
        ),

        Creatinine(
            keyName = BioMarker.CREATININE.displayName,
            normalMin = 0.59f,
            normalMax = 1.04f,
            elevateMin = 1.05f,
            elevateMax = 1.99f,
            highMin = 2.0f,
            highMax = 15.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.Creatinine
        ),

        UricAcid(
            keyName = BioMarker.URIC_ACID.displayName,
            normalMin = 2.6f,
            normalMax = 6.0f,
            elevateMin = 6.1f,
            elevateMax = 8.9f,
            highMin = 9.0f,
            highMax = 20.0f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.UricAcid
        ),

        GGT(
            keyName = BioMarker.GGT.displayName,
            normalMin = 5f,
            normalMax = 36f,
            elevateMin = 37f,
            elevateMax = 180f,
            highMin = 181f,
            highMax = 1500f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.GGT
        ),

        Hemoglobin(
            keyName = BioMarker.HEMOGLOBIN.displayName,
            normalMin = 12.0f,
            normalMax = 15.5f,
            elevateMin = 15.6f,
            elevateMax = 16.9f,
            highMin = 17.0f,
            highMax = 25.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Hemoglobin
        ),

        Hematocrit(
            keyName = BioMarker.HEMATOCRIT.displayName,
            normalMin = 36.0f,
            normalMax = 46.0f,
            elevateMin = 47.0f,
            elevateMax = 54.0f,
            highMin = 55.0f,
            highMax = 75.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Hematocrit
        ),

        Erythrocytes(
            keyName = BioMarker.ERYTHROCYTES.displayName,
            normalMin = 4.1f,
            normalMax = 5.1f,
            elevateMin = 5.2f,
            elevateMax = 5.7f,
            highMin = 5.8f,
            highMax = 8.0f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Erythrocytes
        ),

        ESR(
            keyName = BioMarker.ESR.displayName,
            normalMin = 0f,
            normalMax = 20f,
            elevateMin = 21f,
            elevateMax = 40f,
            highMin = 41f,
            highMax = 150f,
            direction = RiskDirection.HIGHER_IS_WORSE,
            literature = RiskLiterature.ESR
        ),

        Iron(
            keyName = BioMarker.IRON.displayName,
            normalMin = 50f,
            normalMax = 170f,
            elevateMin = 171f,
            elevateMax = 220f,
            highMin = 221f,
            highMax = 350f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Iron
        ),

        Ferritin(
            keyName = BioMarker.FERRITIN.displayName,
            normalMin = 15f,
            normalMax = 150f,
            elevateMin = 151f,
            elevateMax = 500f,
            highMin = 501f,
            highMax = 3000f,
            direction = RiskDirection.BOTH_EXTREMES,
            literature = RiskLiterature.Ferritin
        )
    }
}