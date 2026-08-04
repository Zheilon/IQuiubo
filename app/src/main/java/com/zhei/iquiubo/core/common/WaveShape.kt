package com.zhei.iquiubo.core.common
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class WaveShape(private val waveHeight: Float = 20f) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val path = Path()

        val waveLength = size.width / 3f

        path.moveTo(0f, waveHeight)

        var x = 0f

        while (x < size.width) {

            path.quadraticTo(
                x + waveLength / 4f,
                0f,
                x + waveLength / 2f,
                waveHeight
            )

            path.quadraticTo(
                x + waveLength * 0.75f,
                waveHeight * 2f,
                x + waveLength,
                waveHeight
            )

            x += waveLength
        }

        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        path.close()

        return Outline.Generic(path)
    }
}