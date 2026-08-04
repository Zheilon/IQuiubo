package com.zhei.iquiubo.core.common
import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val original = activity.requestedOrientation // guardamos el valor previo
        activity.requestedOrientation = orientation // bloqueamos
        onDispose { activity.requestedOrientation = original } // restauramos al salir
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ButtonRippleColor(color: Color, block: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides RippleConfiguration(color = color)
    ) { block() }
}



@Composable fun keyBoardIsVisible(): State<Boolean> {
    val ime = WindowInsets.ime
    val density = LocalDensity.current
    return remember { derivedStateOf { ime.getBottom(density) > 0 } }
}




fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp = 12.dp
) = drawBehind {

    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(12f, 8f), // línea, espacio
            0f
        )
    )

    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(
            cornerRadius.toPx(),
            cornerRadius.toPx()
        )
    )
}



fun Modifier.bottomLine(
    width: Dp,
    color: Color
) = drawBehind {

    val strokeWidth = width.toPx()
    val y = size.height - strokeWidth

    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.width, y),
        strokeWidth = strokeWidth
    )
}