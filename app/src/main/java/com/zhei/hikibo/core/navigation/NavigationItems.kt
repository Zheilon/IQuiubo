package com.zhei.hikibo.core.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItems(
    val route: Scenes,
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {

    data object Home: NavigationItems(
        route = Scenes.HomeScene,
        title = "Home",
        filledIcon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home
    )

    data object Interpretation: NavigationItems(
        route = Scenes.InterpretationScenes.Entry,
        title = "Interpretar",
        filledIcon = Icons.Filled.Rocket,
        outlinedIcon = Icons.Outlined.Rocket
    )

    data object Monitoring: NavigationItems(
        route = Scenes.MonitoringScene,
        title = "Seguimiento",
        filledIcon = Icons.Filled.MonitorHeart,
        outlinedIcon = Icons.Outlined.MonitorHeart,
    )

    data object Profile: NavigationItems(
        route = Scenes.ProfileScene,
        title = "Perfil",
        filledIcon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person
    )
}