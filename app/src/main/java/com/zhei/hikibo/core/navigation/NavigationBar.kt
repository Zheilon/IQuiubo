package com.zhei.hikibo.core.navigation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete



@Composable fun MyNavigationBar(nav: NavHostController) {

    val spacing = LocalSpacing.current

    val items = listOf(
        NavigationItems.Home,
        NavigationItems.Interpretation,
        NavigationItems.Monitoring,
        NavigationItems.Profile
    )

    // Personalización del navigationBar
    NavigationBar(
        containerColor = Color.White
    ) {
        
        val currentDestination = nav.currentBackStackEntryAsState().value?.destination

        items.forEach { screen ->

            // Se utiliza para determinar el destino actual en el que esta
            // la escena, usada para navegación tipada.
            val isSelected = currentDestination?.hasRoute(screen.route::class) == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    nav.navigate(screen.route) {
                        popUpTo(nav.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.filledIcon else screen.outlinedIcon,
                        contentDescription = screen.title
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = IQuiuboColorPallete.moradoPrimario,
                    unselectedTextColor = Color.Black,
                    selectedIconColor = IQuiuboColorPallete.moradoPrimario,
                    unselectedIconColor = IQuiuboColorPallete.buttonEnabledOne,
                    indicatorColor = IQuiuboColorPallete.backgroundLight
                ),
                label = {
                    Text(
                        text = screen.title,
                        fontSize = ResponsiveText.caption(),
                        lineHeight = ResponsiveLineHeight.caption(),
                        fontFamily = MyFont.geistMonoNerdFontRegular,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                    )
                }
            )
        }
    }
}