package com.zhei.iquiubo.core.navigation
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically


val ScreenEnterTransition =
    slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn()

val ScreenExitTransition =
    slideOutVertically(
        targetOffsetY = { -it },
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut()