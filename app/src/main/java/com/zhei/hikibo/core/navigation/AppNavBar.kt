package com.zhei.hikibo.core.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete


@Composable fun AppNavBar(
    nav: NavHostController,
    navHost: @Composable () -> Unit
) {

    var heightPaddingBar by remember { mutableStateOf(0.dp) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroudColor(nav)),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = heightPaddingBar)
        ) {

            navHost()
        }

        MyNavigationBar(nav = nav, onPaddingBotton = { heightPaddingBar = it })
    }
}


@Composable
private fun backgroudColor(nav: NavHostController): Color {
    val current = nav.currentBackStackEntryAsState().value?.destination
    return when {
        current?.hasRoute(Scenes.HomeScene::class) == true -> IQuiuboColorPallete.moradoPrimario
        current?.hasRoute(Scenes.InterpretationScenes.Entry::class) == true -> IQuiuboColorPallete.accentMiddle
        current?.hasRoute(Scenes.MonitoringScene::class) == true -> Color(0xFF2E7D32)
        current?.hasRoute(Scenes.ProfileScene::class) == true -> Color(0xFF2196F3)
        else -> Color.White
    }
}