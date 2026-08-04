package com.zhei.iquiubo.features.home.domain.model

import androidx.compose.ui.graphics.Color

/**
 * Esta data class se utilizará en el caso de
 * uso encargado de construir la lista de exámenes
 * correspondientes
 * */
data class RiskBuild(
    val exam: String,
    val value: Double,
    val progress: Double,
    val color: Color,
    val label: String,
    val gradientList: List<Color>
)
