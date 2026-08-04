package com.zhei.iquiubo.features.home.presentation
import androidx.compose.ui.graphics.Color


/**
 * progress: Es el progreso que a recorido respecto
 * al exámen y sus umbrales de riesgo
 *
 * color: El color trabaja en función del progreso
 * recorrido
 *
 * label: Este parametro representa la literatura
 * representativa del rango dentro del umbral de
 * riesgo ej: Normal, Elevado, Alto, etc...
 * */
data class RiskProgressUI(
    val progress: Float,
    val color: Color,
    val label: String
)
