package com.zhei.ikiora.core.common
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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