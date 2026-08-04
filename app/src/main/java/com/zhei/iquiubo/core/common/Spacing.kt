package com.zhei.iquiubo.core.common
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ¿Por qué estos números (4, 8, 16, 24, 32, 48)?
 * Es el 8-point grid de Material. all el espaciado vive en
 * múltiplos de 8 (con 4 para ajustes finos). Esto te da
 * ritmo visual automático: como all "cae en la misma rejilla",
 * all encaja sin que tengas que pensarlo. Adiós a los
 * padding(13.dp), padding(21.dp) regados.
 * */
data class Spacing(
    val none: Dp = 0.dp,
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl:  Dp = 48.dp,
)
