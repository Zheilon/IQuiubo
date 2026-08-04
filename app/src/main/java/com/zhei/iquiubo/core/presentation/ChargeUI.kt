package com.zhei.iquiubo.core.presentation
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.features.home.presentation.IQuiuboPulseLoader
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay


@Composable fun ChargeUI(
    detail: String,
    onComplete: @Composable () -> Unit
) {

    var charged by remember { mutableStateOf(false) }

    Crossfade(
        targetState = charged,
        animationSpec = tween(350),
        label = "loading"
    ) { loaded ->

        if (loaded) { onComplete() } else { UI(detail) }
    }

//    if (!charged) UI() else onComplete()
//
    LaunchedEffect(Unit) {
        delay(1100)
        charged = true
    }
}

@Composable private fun UI(
    text: String
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IQuiuboColorPallete.accentMiddle),
        verticalArrangement = Arrangement.Bottom
    ) {

        Column(
            modifier = Modifier
                .background(IQuiuboColorPallete.accentMiddle)
                .padding(spacing.lg)
        ) {

            Text(
                text = text,
                fontFamily = MyFont.spaceGroteskRegular,
                fontSize = ResponsiveText.displayBig(),
                lineHeight = ResponsiveLineHeight.displayBig(),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

    }
}
