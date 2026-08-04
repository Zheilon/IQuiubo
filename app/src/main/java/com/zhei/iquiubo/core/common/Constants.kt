package com.zhei.iquiubo.core.common
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.YearMonth

object Constants {

    val DEVICE_TOP_PADDING: @Composable () -> Dp = {
        WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    }

    val DEVICE_BOTTOM_PADDING: @Composable () -> Dp = {
        WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()
    }

    val HEIGHT_DP_DEVICE: @Composable () -> Dp = {
        LocalConfiguration.current.screenHeightDp.dp
    }

    val WIDTH_DP_DEVICE: @Composable () -> Dp = {
        LocalConfiguration.current.screenWidthDp.dp
    }

    const val SMALL_DEVICE_WIDTH_DP = 350

    val LENGHT_CURRENT_MONTH = YearMonth.now().lengthOfMonth()

    val CURRENT_YEAR = YearMonth.now().year

    val STRING_MONTHS = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")
}






