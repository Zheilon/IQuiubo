package com.zhei.hikibo.core.navigation
import android.transition.Scene
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zhei.hikibo.core.presentation.AppViewModel
import com.zhei.hikibo.core.presentation.CoordinatorFlowSceneVM
import com.zhei.hikibo.core.presentation.StateSceneVM
import com.zhei.hikibo.features.entry_interpretation.presentation.EntryInterpretationUI
import com.zhei.hikibo.features.home.presentation.HomeUI
import com.zhei.hikibo.features.home.presentation.HomeVM
import com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationUI
import com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationVM


@Composable fun MyNavGraph(
    appViewModel: AppViewModel,
    stateSceneVM: StateSceneVM,
    homeVM: HomeVM,
    manualInterpretationVM: ManualInterpretationVM,
    coordinatorFlowSceneVM: CoordinatorFlowSceneVM
) {
    
    val nav = rememberNavController()

    InitScenes(
        appViewModel = appViewModel,
        stateVM = stateSceneVM,
        middleNavigation = {
            Main(
                nav = nav,
                homeVM = homeVM,
                manualInterpretationVM = manualInterpretationVM,
                coordinatorFlowSceneVM = coordinatorFlowSceneVM
            )
        }
    )
}



@Composable private fun Main(
    nav: NavHostController,
    homeVM: HomeVM,
    manualInterpretationVM: ManualInterpretationVM,
    coordinatorFlowSceneVM: CoordinatorFlowSceneVM
) {

    AppNavBar(nav) {
        NavHost(
            navController = nav,
            startDestination = Scenes.HomeScene,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn()
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut()
            },

            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn()
            },

            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut()
            }
        ) {

            composable<Scenes.HomeScene> {
                HomeUI(homeVM)
            }


            navigation<Scenes.InterpretationGraph>(
                startDestination = Scenes.InterpretationScenes.Entry
            ) {

                composable<Scenes.InterpretationScenes.Entry> {
                    EntryInterpretationUI(
                        onNavigate = {
                            // Se traduce prácticamente a:
                            //"Ve a esta pestaña. Si ya existe, reutilízala.
                            // Si tenía estado, recupéralo. No crees copias nuevas."
                            nav.navigate(it) {
                                popUpTo(nav.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            //coordinatorFlowSceneVM.setScene(it)
                        }
                    )
                }

                composable<Scenes.InterpretationScenes.Manual> {
                    ManualInterpretationUI(manualInterpretationVM)
                }
            }


            composable<Scenes.MonitoringScene> {
                Text("hola seguimiento")
            }


            composable<Scenes.ProfileScene> {
                Text("hola perfil")
            }
        }
    }
}