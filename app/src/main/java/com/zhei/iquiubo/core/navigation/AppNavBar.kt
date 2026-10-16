package com.zhei.iquiubo.core.navigation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.presentation.CoordinatorFlowSceneVM
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState


@Composable fun AppNavBar(
    nav: NavHostController,
    heightNavBar: (Dp) -> Unit,
    navHost: @Composable () -> Unit
) {

    val spacing = LocalSpacing.current
    val toShow = toShow(nav)
    val hazeState = rememberHazeState()

    Box {

        Box (
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .background(color = Color.Transparent)
        ) {

            // Este navHost() basicamente es la UI
            navHost()
        }

        if (toShow) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = spacing.md),
                contentAlignment = Alignment.BottomCenter
            ) {

                MyNavigationBar(
                    nav = nav,
                    hazeState = hazeState,
                    onPaddingBotton = { heightNavBar(it) }
                )
            }
        }
    }
}


@Composable
private fun backgroudColor(nav: NavHostController): Color {
    val current = nav.currentBackStackEntryAsState().value?.destination
    return when {
        current?.hasRoute(Scenes.HomeScene::class) == true ->
            IQuiuboColorPallete.backgroundLight

        current?.hasRoute(Scenes.GetValuesForPriorityScene::class) == true ->
            Color(0xFF6B21A8)

        current?.hasRoute(Scenes.InterpretationScenes.Entry::class) == true ->
            IQuiuboColorPallete.accentMiddle

        current?.hasRoute(Scenes.InterpretationScenes.Manual::class) == true ->
            IQuiuboColorPallete.accentMiddle

        current?.hasRoute(Scenes.MonitoringScene::class) == true ->
            Color.White

        current?.hasRoute(Scenes.ProfileScene::class) == true ->
            Color(0xFF2196F3)
        else -> Color.White
    }
}



/**
 * A estas pantallas se les permitirá el uso de su
 * respectivo navigationBar.
 * */
@Composable
private fun toShow(
    nav: NavHostController,
): Boolean {

    val current = nav.currentBackStackEntryAsState().value?.destination
    val cond1 = current?.hasRoute(Scenes.HomeScene::class) == true
    val cond2 = current?.hasRoute(Scenes.InterpretationScenes.Entry::class) == true
    val cond3 = current?.hasRoute(Scenes.MonitoringScene::class) == true
    val cond4 = current?.hasRoute(Scenes.ProfileScene::class) == true
    return (cond1 || cond2 || cond3 || cond4)
}