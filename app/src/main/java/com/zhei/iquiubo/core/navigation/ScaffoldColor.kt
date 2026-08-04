package com.zhei.iquiubo.core.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete


@Composable fun scaffoldColorByScene(
    nav: NavHostController
): Color {

    val current = nav.currentBackStackEntryAsState().value?.destination

    return when {
        current?.hasRoute(Scenes.HomeScene::class) == true -> IQuiuboColorPallete.moradoPrimario
        current?.hasRoute(Scenes.InterpretationScenes.Entry::class) == true -> IQuiuboColorPallete.moradoPrimario
        current?.hasRoute(Scenes.InterpretationScenes.Manual::class) == true -> IQuiuboColorPallete.moradoPrimario
        else -> Color.Yellow
    }
}