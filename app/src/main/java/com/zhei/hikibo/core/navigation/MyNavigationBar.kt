package com.zhei.hikibo.core.navigation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete



@Composable fun MyNavigationBar(
    nav: NavHostController,
    onPaddingBotton: (Dp) -> Unit
) {

    val spacing = LocalSpacing.current

    val items = listOf(
        NavigationItems.Home,
        NavigationItems.Interpretation,
        NavigationItems.Monitoring,
        NavigationItems.Profile
    )

    var navBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    // Al final hay un LaunchEffect Ejecutando el callback

    // Personalización del navigationBar
    NavigationBar(
        modifier = Modifier.onGloballyPositioned {
            navBarHeight = with(density) { (it.size.height.toDp()) }
        },
        containerColor = IQuiuboColorPallete.backgroundLight,
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
                    selectedTextColor = labelColor(screen),
                    unselectedTextColor = Color.Black,
                    selectedIconColor = iconColor(screen, isSelected),
                    unselectedIconColor = iconColor(screen, isSelected),
                    indicatorColor = indicatorColor(screen)
                ),
                label = {
                    Text(
                        text = screen.title,
                        fontSize = ResponsiveText.caption(),
                        lineHeight = ResponsiveLineHeight.caption(),
                        fontFamily = MyFont.grandstanderRegular,
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

    LaunchedEffect(navBarHeight) {
        onPaddingBotton(navBarHeight)
    }
}



private fun iconColor(screen: NavigationItems, selected: Boolean): Color =

    when (screen) {

        NavigationItems.Home ->
            if (selected)
                IQuiuboColorPallete.moradoPrimario
            else
                IQuiuboColorPallete.LOW_PINK

        NavigationItems.Interpretation ->
            if (selected)
                IQuiuboColorPallete.accentMiddle
            else
                Color(0xFFFFE88A)

        NavigationItems.Monitoring ->
            if (selected) Color(0xFF2E7D32)
            else
                Color(0xFF81C784)

        NavigationItems.Profile ->
            if (selected)
                Color(0xFF2196F3)
            else
                Color(0xFF90CAF9)
    }



private fun indicatorColor(screen: NavigationItems): Color =
    when (screen) {

        NavigationItems.Home ->
            IQuiuboColorPallete.LOW_PINK

        NavigationItems.Interpretation ->
            Color(0xFFFFF7D1)

        NavigationItems.Monitoring ->
            Color(0xFFE8F5E9)

        NavigationItems.Profile ->
            Color(0xFFE3F2FD)
    }



private fun labelColor(screen: NavigationItems): Color =
    when(screen) {
        NavigationItems.Home ->
            IQuiuboColorPallete.moradoPrimario

        NavigationItems.Interpretation ->
            Color(0xFFD4A500)

        NavigationItems.Monitoring ->
            Color(0xFF1B5E20)

        NavigationItems.Profile ->
            Color(0xFF1565C0)
    }