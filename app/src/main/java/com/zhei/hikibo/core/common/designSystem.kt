package com.zhei.hikibo.core.common
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


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
@Composable fun ButtonColorWaveConfig(color: Color, block: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides RippleConfiguration(color = color)
    ) { block() }
}