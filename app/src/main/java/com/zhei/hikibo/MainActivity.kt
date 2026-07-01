package com.zhei.hikibo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.hikibo.core.navigation.MyNavGraph
import com.zhei.hikibo.core.navigation.Scenes
import com.zhei.hikibo.core.presentation.AppViewModel
import com.zhei.hikibo.ui.theme.HikiboTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val authLogin: AppViewModel = hiltViewModel()

            val startState by authLogin.startState.collectAsStateWithLifecycle()
            val userFirst = authLogin.loginFirst

            Log.e("(MainActivity)", userFirst.toString())

            HikiboTheme {
                when (startState) {
                    AppViewModel.StartState.Loading   -> {
                        // Mantengo el splash mientras valida la existencia del user
                        splash.setKeepOnScreenCondition {
                            startState is AppViewModel.StartState.Loading
                        }

                    }

                    AppViewModel.StartState.LoggedOut, AppViewModel.StartState.NeedsGate ->  {
                        MyNavGraph(scene = Scenes.LoginScene)
                    }

                    AppViewModel.StartState.Ready -> {
                        MyNavGraph(scene = Scenes.PersonalInfoScene)
                    }
                }
            }
        }
    }
}
