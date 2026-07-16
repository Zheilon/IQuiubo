package com.zhei.hikibo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhei.hikibo.core.navigation.MyNavGraph
import com.zhei.hikibo.core.presentation.AppViewModel
import com.zhei.hikibo.core.presentation.CoordinatorFlowSceneVM
import com.zhei.hikibo.core.presentation.StateSceneVM
import com.zhei.hikibo.features.home.presentation.HomeVM
import com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationVM
import com.zhei.hikibo.ui.theme.HikiboTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val appViewModel: AppViewModel = hiltViewModel()
            val stateVM: StateSceneVM = hiltViewModel()
            val homeVM: HomeVM = hiltViewModel()
            val manualInterpretationVM: ManualInterpretationVM = hiltViewModel()
            val coordinatorFlowSceneVM: CoordinatorFlowSceneVM = hiltViewModel()

            // Mantiene el splash screen hasta que deje de ser (null).
            splash.setKeepOnScreenCondition { appViewModel.reactiveLogin.value == null }


            HikiboTheme {
                MyNavGraph(
                    appViewModel,
                    stateVM,
                    homeVM,
                    manualInterpretationVM,
                    coordinatorFlowSceneVM
                )
            }
        }
    }
}
