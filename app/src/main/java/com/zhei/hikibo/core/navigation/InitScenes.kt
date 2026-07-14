package com.zhei.hikibo.core.navigation
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.hikibo.core.presentation.AppViewModel
import com.zhei.hikibo.core.presentation.StateSceneVM
import com.zhei.hikibo.features.login.presentation.LoginUI
import com.zhei.hikibo.features.personal_info.presentation.PersonalInfoUI
import kotlinx.coroutines.delay


enum class DataBaseStates(val scene: String) {
    PERSONAL_INFO("PERSONAL_INFO");
}


@Composable fun InitScenes(
    appViewModel: AppViewModel,
    stateVM: StateSceneVM,
    middleNavigation: @Composable () -> Unit
) {

    val isLogged by appViewModel.reactiveLogin.collectAsStateWithLifecycle()
    val personalInfoScene by stateVM.personalInfoScene.collectAsStateWithLifecycle()

    LaunchedEffect(isLogged)
    { Log.e("IS LOGGED", "state: $isLogged") }

    when {

        isLogged == null -> { /* Sin lógica por acá */ }

        // Si el usuario aún no se a loggeado y cumplido las
        // respectivas condiciones entrará a la pantalla de LoginUI().
        isLogged == false -> {
            LoginUI {
                Log.e("LOGIN to NEXT", "PERSONAL INFO: $personalInfoScene")
            }
        }

        personalInfoScene -> {
            PersonalInfoUI {
                stateVM.updateStateByNameInRoom(DataBaseStates.PERSONAL_INFO.scene, false)
                stateVM.updateState(DataBaseStates.PERSONAL_INFO.scene, false)
            }
        }

        else -> { middleNavigation() }
    }
}