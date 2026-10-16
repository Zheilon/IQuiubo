package com.zhei.iquiubo.core.navigation
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zhei.iquiubo.core.presentation.AppViewModel
import com.zhei.iquiubo.core.presentation.CoordinatorFlowSceneVM
import com.zhei.iquiubo.core.presentation.StateSceneVM
import com.zhei.iquiubo.features.entry_interpretation.features.fillingInToInterpretation.presentation.FillingInToInterpUI
import com.zhei.iquiubo.features.entry_interpretation.features.fillingInToInterpretation.presentation.FillingInToInterpVM
import com.zhei.iquiubo.features.entry_interpretation.presentation.EntryInterpretationUI
import com.zhei.iquiubo.features.home.presentation.HomeVM
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationUI
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationVM
import com.zhei.iquiubo.features.show_priority_results.presentation.ShowPriorityResultsUI
import com.zhei.iquiubo.features.home.presentation.HomeUIV2
import com.zhei.iquiubo.features.monitoring.presentation.MonitoringUI
import com.zhei.iquiubo.features.put_rna_values.presentation.GetValuesForPriorityUI

private const val DURATION = 500
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

    // Variable encargada de almacenar el alto de la barra de navegación
    var heightNavBar by remember { mutableStateOf(0.dp) }

    AppNavBar(
        nav = nav,
        heightNavBar = { heightNavBar = it }
    ) {
        NavHost(
            navController = nav,
            startDestination = Scenes.HomeScene,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
                    targetOffset = { it / 4 }
                )
            },

            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
                    initialOffset = { it / 4 }
                )
            },

            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
                )
            }
        ) {

            composable<Scenes.HomeScene> {
                HomeUIV2(
                    heightNavBar =  heightNavBar,
                    homeVM = homeVM,
                    onNavigate = {
                        nav.navigate(it) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }


            composable<Scenes.GetValuesForPriorityScene> {
                GetValuesForPriorityUI(
                    onNavigate = {
                        nav.navigate(it) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
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

            composable<Scenes.ShowPriorityResultsScene> {
                ShowPriorityResultsUI()
            }


            composable<Scenes.MonitoringScene> {
                MonitoringUI()
            }


            composable<Scenes.ProfileScene> {
                Text("hola perfil")
            }
        }
    }
}