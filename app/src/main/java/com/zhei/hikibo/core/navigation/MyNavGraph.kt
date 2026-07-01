package com.zhei.hikibo.core.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhei.hikibo.features.login.presentation.LoginUI
import com.zhei.hikibo.features.personal_info.presentation.PersonalInfoUI


@Composable fun MyNavGraph(
    scene: Scenes
) {
    
    val nav = rememberNavController()
    Main(nav, scene)

}


@Composable private fun Main(
    nav: NavHostController,
    scene: Scenes
) {

    NavHost(navController = nav, startDestination = scene) {

        composable<Scenes.LoginScene> { 
            LoginUI {
                nav.navigate(Scenes.PersonalInfoScene) {
                    popUpTo(Scenes.LoginScene) {
                        inclusive = true
                    }
                }
            }
        }

        composable<Scenes.PersonalInfoScene> {
            PersonalInfoUI {}
        }
    }
    
}