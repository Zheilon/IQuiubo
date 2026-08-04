package com.zhei.iquiubo.features.entry_interpretation.presentation
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.iquiubo.R
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete


@Composable fun EntryInterpretationUI(
    entryInterpretationVM: EntryInterpretationVM = hiltViewModel(),
    onNavigate: (Scenes) -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            entryInterpretationVM.navigate.collect { onNavigate(it) }
        }
    }

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .shadow(5.dp)
    ) {

        FirstSection()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(IQuiuboColorPallete.backgroundLight)
                .verticalScroll(rememberScrollState())
                .padding(spacing.sm)
        ) {

            SecondSection(entryInterpretationVM)
        }
    }
}



@Composable private fun FirstSection() {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(IQuiuboColorPallete.accentMiddle)
            .padding(spacing.lg)
            .statusBarsPadding()
    ) {

        TitleFirstSection()

        LeyendForFirstSection()
    }
}



@Composable private fun TitleFirstSection() {

    Text(
        text = "Interpretación",
        fontFamily = MyFont.jakartaSansRegular,
        fontSize = ResponsiveText.headline(),
        lineHeight = ResponsiveLineHeight.headline(),
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
}



@Composable private fun LeyendForFirstSection() {

    Text(
        text = "Elige cómo quieres ingresar tus resultados",
        fontFamily = MyFont.jakartaSansRegular,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.primaryText,
    )
}


private data class EntryItem(
    val title: String,
    val text: String,
    val scene: Scenes,
    val image: ImageVector? = null,
    val painter: Int? = null,
)

private val itemList = listOf(
    EntryItem(
        title = "Escanear exámen",
        // Escanea o sube una foto de tu examen. IQuiubo identificará automáticamente los biomarcadores.
        text = "Sube una imagen o usa la cámara para extraer automáticamente tus datos",
        painter = R.drawable.scanner_v2,
        scene = Scenes.InterpretationScenes.Manual
    ),
    EntryItem(
        title = "Ingresar manualmente",
        text = "Agrega unicamente parámetros clínicos que tengas",
        painter = R.drawable.input_data,
        scene = Scenes.InterpretationScenes.Manual
    )
)


@Composable private fun SecondSection(
    entryInterpretationVM: EntryInterpretationVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        itemList.forEach {
            ItemForDoInterpretation(entryInterpretationVM, it)
        }
    }
}


@Composable private fun ItemForDoInterpretation(
    entryInterpretationVM: EntryInterpretationVM,
    entryItem: EntryItem
) {

    val interactionSource = remember { MutableInteractionSource() }
    var pressed by remember { mutableStateOf(false) }
    val spacing = LocalSpacing.current

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.90f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "press_scale"
    )

    val elevation by animateDpAsState(
        if (pressed) 3.dp else 5.dp,
        label = "elevation"
    )

    Row (
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .scale(scale)
            //.shadow(5.dp, RoundedCornerShape(spacing.xs))
            .background(
                color = IQuiuboColorPallete.backgroundLight,
                shape = RoundedCornerShape(spacing.sm))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        pressed = true
                        tryAwaitRelease()
                        pressed = false
                    },
                    onTap = { entryInterpretationVM.onButtonSelect(entryItem.scene) }
                )
            }
            .padding(vertical = spacing.lg)
            .padding(end = spacing.lg),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SelecteImageType(entryItem)

        Spacer(Modifier.width(spacing.md))

        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = entryItem.title,
                fontFamily = MyFont.jakartaSansRegular,
                color = IQuiuboColorPallete.primaryText,
                fontSize = ResponsiveText.title(),
                lineHeight = ResponsiveLineHeight.title(),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = entryItem.text,
                fontFamily = MyFont.jakartaSansRegular,
                color = IQuiuboColorPallete.secondaryText,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
            )
        }
    }
}



@Composable private fun SelecteImageType(
    entryItem: EntryItem
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(topEnd = spacing.lg, bottomEnd = spacing.lg)

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(IQuiuboColorPallete.backgroundLight, shape),
        contentAlignment = Alignment.Center
    ) {

        entryItem.image?.let {
            Icon(
                imageVector = it,
                contentDescription = null
            )
        }

        entryItem.painter?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
        }
    }
}