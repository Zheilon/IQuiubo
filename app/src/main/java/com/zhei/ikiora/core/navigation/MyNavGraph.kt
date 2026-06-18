package com.zhei.ikiora.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zhei.ikiora.features.login.presentation.LoginUI


@Composable fun MyNavGraph() {
    
    val nav = rememberNavController()
    
    Main(nav)

}


@Composable private fun Main(nav: NavHostController) {
    
    NavHost(navController = nav, startDestination = Scenes.LoginScene) {
        
        composable<Scenes.LoginScene> { 
            LoginUI()
        }
        
    }
    
}