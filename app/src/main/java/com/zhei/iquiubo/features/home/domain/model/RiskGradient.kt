package com.zhei.iquiubo.features.home.domain.model
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

object RiskGradient {

    private val colors01: List<Color> = listOf(
        Color(0xFF2196F3),
        Color(0xFF4CAF50),
        Color(0xFFFFEB3B),
        Color(0xFFFF9800),
        Color(0xFFF44336)
    )

    private val colors02: List<Color> = listOf(
        Color(0xFFF44336),
        Color(0xFFFF9800),
        Color(0xFFFFEB3B),
        Color(0xFF4CAF50),
        Color(0xFF2196F3),
    )

    private val colors03: List<Color> = listOf(
        Color(0xFFF44336),
        Color(0xFFFF9800),
        Color(0xFFFFEB3B),
        Color(0xFF4CAF50),
        Color(0xFFFFEB3B),
        Color(0xFFFF9800),
        Color(0xFFF44336)
    )


    fun getColor(progress: Double, direction: RiskDirection): Pair<List<Color>, Color> {

        val arrToUse = getColorListByDirection(direction)

        val p = progress.coerceIn(0.0, 1.0).toFloat()
        val segmentCount = arrToUse.size - 1

        // Posición dentro de los elementos
        val scaled = p * segmentCount

        // Índice del color inical
        val index = scaled.toInt().coerceAtMost(segmentCount - 1)

        // Que tanto avanzó entre ese color y el siguiente.
        val fraction = scaled - index

        return Pair(
            arrToUse,
            lerp(
                start = arrToUse[index],
                stop = arrToUse[index + 1],
                fraction = fraction
            )
        )
    }



    fun getColorFractionByGradientList(fraction: Double, arr: List<Color>): Color {

        val p = fraction.coerceIn(0.0, 1.0).toFloat()
        val segmentCount = arr.size - 1

        // Posición dentro de los elementos
        val scaled = p * segmentCount

        // Índice del color inical
        val index = scaled.toInt().coerceAtMost(segmentCount - 1)

        // Que tanto avanzó entre ese color y el siguiente.
        val f = scaled - index

        return lerp(
            start = arr[index],
            stop = arr[index + 1],
            fraction = f

        )
    }


    private fun getColorListByDirection(direction: RiskDirection): List<Color> = when(direction) {
        RiskDirection.HIGHER_IS_WORSE -> colors01
        RiskDirection.LOWER_IS_WORSE -> colors02
        RiskDirection.BOTH_EXTREMES -> colors03
        else -> emptyList()
    }
}