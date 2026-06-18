package com.zhei.ikiora.core.common
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val DEVICE_TOP_PADDING: @Composable () -> Dp = {
    WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
}
val DEVICE_BOTTOM_PADDING: @Composable () -> Dp = {
    WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
}

val HEIGHT_DP_DEVICE: @Composable () -> Dp = { LocalConfiguration.current.screenHeightDp.dp }
val WIDTH_DP_DEVICE: @Composable () -> Dp = { LocalConfiguration.current.screenWidthDp.dp }