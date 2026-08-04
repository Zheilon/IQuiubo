package com.zhei.iquiubo.core.navigation
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zhei.iquiubo.core.presentation.AppViewModel
import com.zhei.iquiubo.core.presentation.CoordinatorFlowSceneVM
import com.zhei.iquiubo.core.presentation.StateSceneVM
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.features.fillingInToInterpretation.presentation.FillingInToInterpUI
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.features.fillingInToInterpretation.presentation.FillingInToInterpVM
import com.zhei.iquiubo.features.entry_interpretation.presentation.EntryInterpretationUI
import com.zhei.iquiubo.features.home.presentation.HomeUI
import com.zhei.iquiubo.features.home.presentation.HomeVM
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationUI
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationVM



@Composable fun MyNavGraph(
    appViewModel: AppViewModel,
    stateSceneVM: StateSceneVM,
    homeVM: HomeVM,
    manualInterpretationVM: ManualInterpretationVM,
    fillingInToInterpVM: FillingInToInterpVM,
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
                fillingInToInterpVM = fillingInToInterpVM,
                coordinatorFlowSceneVM = coordinatorFlowSceneVM
            )
        }
    )
}



@Composable private fun Main(
    nav: NavHostController,
    homeVM: HomeVM,
    manualInterpretationVM: ManualInterpretationVM,
    fillingInToInterpVM: FillingInToInterpVM,
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
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn()
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut()
            },

            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn()
            },

            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(
                        durationMillis = 500,
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
                                //popUpTo(nav.graph.startDestinationId) {
                                //    saveState = true
                                //}
                                launchSingleTop = true
                                restoreState = true
                            }
                            //coordinatorFlowSceneVM.setScene(it)
                        }
                    )
                }

                composable<Scenes.InterpretationScenes.Manual> {
                    ManualInterpretationUI(
                        manualInterpretationVM = manualInterpretationVM,
                        onNavigate = {
                            nav.navigate(it) {
                                //popUpTo(nav.graph.startDestinationId) {
                                //    saveState = true
                                //}
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

                composable<Scenes.InterpretationScenes.Filling> {
                    FillingInToInterpUI(
                        fillingInToInterpVM = fillingInToInterpVM,
                        manualInterpretationVM = manualInterpretationVM,
                        onNavigation = {
                            nav.navigate(it) {
                                //popUpTo(nav.graph.startDestinationId) {
                                //    saveState = true
                                //}
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
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