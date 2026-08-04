package com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.features.fillingInToInterpretation.presentation
import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.iquiubo.core.common.ButtonRippleColor
import com.zhei.iquiubo.core.common.LockScreenOrientation
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.bottomLine
import com.zhei.iquiubo.core.common.keyBoardIsVisible
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.core.presentation.ChargeUI
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.ManualInterpretationVM
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay


@Composable fun FillingInToInterpUI(
    fillingInToInterpVM: FillingInToInterpVM,
    manualInterpretationVM: ManualInterpretationVM,
    onNavigation: (Scenes) -> Unit
) {

    val valuesSelected by manualInterpretationVM.selectedExams.collectAsStateWithLifecycle()
    val examsFilled by fillingInToInterpVM.info.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            fillingInToInterpVM.navigation.collect { onNavigation(it) }
        }
    }


    LaunchedEffect(valuesSelected) {
        fillingInToInterpVM.doFill(valuesSelected)
    }

    val keyboardVisible by keyBoardIsVisible()
    val spacing = LocalSpacing.current

    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    ChargeUI(
        detail = "Preparando [ ${valuesSelected.size} ] biomarcadores clínicos...",
        onComplete = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(IQuiuboColorPallete.backgroundLight)
                    .navigationBarsPadding()
            ) {

                // Aquí va el header

                Header(fillingInToInterpVM)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(IQuiuboColorPallete.backgroundLight)
                        .padding(horizontal = spacing.md)
                        .padding(top = spacing.md)
                        .imePadding(),
                ) {

                    LazyColumnTextFields(
                        Modifier
                            .wrapContentHeight(),
                            //.border(1.dp, IQuiuboColorPallete.accentMiddle, RoundedCornerShape(spacing.md)),
                        fillingInToInterpVM
                    )

                    Spacer(modifier = Modifier.height(spacing.md))
                }
            }
        }
    )
}



@Composable private fun Header(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .background(IQuiuboColorPallete.accentMiddle)
            .padding(spacing.lg)
            .statusBarsPadding()

    ) {

        Row (
            verticalAlignment = Alignment.Top,
        ) {

            ArrowToBack(fillingInToInterpVM)

            Spacer(modifier = Modifier.width(spacing.md))

            DescriptionHeader(Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(spacing.sm))

        LinearChargeComplete(fillingInToInterpVM)

        ButtonToNavigate(fillingInToInterpVM)
    }
}



@Composable private fun ArrowToBack(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val spacing = LocalSpacing.current

    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "arrow_back",
        modifier = Modifier
            .size(spacing.xl)
            .clip(CircleShape)
            .clickable {
                fillingInToInterpVM.onButtonToNav(
                    Scenes.InterpretationScenes.Manual
                )
            }
    )
}



@Composable private fun DescriptionHeader(
    modifier: Modifier
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Ingresa tus valores",
            fontSize = ResponsiveText.headline(),
            lineHeight = ResponsiveLineHeight.headline(),
            fontFamily = MyFont.workSansRegular,
            color = IQuiuboColorPallete.primaryText,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Escribe el resultado de cada examen tal como aparece",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.jakartaSansRegular,
            color = IQuiuboColorPallete.primaryText,
        )
    }
}



@Composable private fun LinearChargeComplete(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Canva line
        CanvaLine(
            fillingInToInterpVM = fillingInToInterpVM,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(spacing.md))

        // Text
        DescribeMissingData(fillingInToInterpVM)
    }
}



@Composable private fun CanvaLine(
    fillingInToInterpVM: FillingInToInterpVM,
    modifier: Modifier
) {

    val missingTexts by fillingInToInterpVM.missingTextfields.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    val progress by animateFloatAsState(
        targetValue = missingTexts.second,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )

    Canvas(
        modifier
            .height(spacing.sm)
            .clip(CircleShape)
    ) {

        // Line de fondo sin relleno progresivo.
        drawRoundRect(
            color = IQuiuboColorPallete.backgroundLight,
            size = size,
            cornerRadius = CornerRadius(50f)
        )

        drawRoundRect(
            color = Color.Black,
            size = size.copy(width = size.width * progress),
            cornerRadius = CornerRadius(50f)
        )
    }
}



@Composable private fun DescribeMissingData(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val selectedExams by fillingInToInterpVM.info.collectAsStateWithLifecycle()
    val missingExams by fillingInToInterpVM.missingTextfields.collectAsStateWithLifecycle()


    Text(
        text = "${missingExams.first} de ${selectedExams.size}",
        fontSize = ResponsiveText.body(), 
        lineHeight = ResponsiveLineHeight.body(), 
        fontFamily = MyFont.spaceGroteskRegular, 
        color = Color.Black
    )
}



@Composable private fun ButtonToNavigate(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val condition by fillingInToInterpVM.canContinue.collectAsStateWithLifecycle()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación del botón
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "press_scale"
    )

    AnimatedVisibility(
        visible = condition,
        enter = fadeIn(animationSpec = tween(350)) +
            slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = spring(
                    dampingRatio = 0.75f,
                    stiffness = Spring.StiffnessMediumLow
                )
            ) +
            expandVertically(
                expandFrom = Alignment.Bottom,
                animationSpec = spring(
                    dampingRatio = 0.85f,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
        exit = fadeOut(animationSpec = tween(180)) +
            shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(180)
            )
    ) {

        ButtonRippleColor(color = Color.Black) {
            Button(
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                onClick = {  }
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontFamily = MyFont.jakartaSansRegular,
                                color = IQuiuboColorPallete.secondaryText
                            )
                        ) { append("Genera tu ") }

                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = MyFont.geistMonoNerdFontRegular,
                                color = IQuiuboColorPallete.primaryText
                            )
                        ) { append("Interpretación") }
                    },
                    fontSize = ResponsiveText.button(),
                    lineHeight = ResponsiveLineHeight.button(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@Composable private fun LazyColumnTextFields(
    modifier: Modifier,
    fillingInToInterpVM: FillingInToInterpVM
) {

    val spacing = LocalSpacing.current
    val selectedExams by fillingInToInterpVM.info.collectAsStateWithLifecycle()

    LazyColumn (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        itemsIndexed(selectedExams) { index, item ->
            TextfieldData(
                selectedExams,
                index,
                fillingInToInterpVM,
                item
            )
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable private fun TextfieldData(
    list: List<FillingDataUI>,
    index: Int,
    fillingInToInterpVM: FillingInToInterpVM,
    biomarker: FillingDataUI
) {

    val spacing = LocalSpacing.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        label = "boder_width"
    )

    // Cambio de color respecto a la condición puesta.
    val colorBorder by animateColorAsState(
        targetValue = when {
            isFocused -> Color.Black
            else -> IQuiuboColorPallete.accentMiddle.copy(alpha = 0.5f)
        },
        label = "color"
    )

    // Color del fondo de la unidad de como se miden los examenes
    val backgroundUnit by animateColorAsState(
        targetValue = if (biomarker.value.isNotEmpty()) IQuiuboColorPallete.accentMiddle.copy(green = 0.5f)
        else Color(0xFFFF9A4D),
        label = "background_unit"
    )

    // Si es el último elemento de la lista, sera ImeAction.Done
    val imeAction = if (list.size - 1 == index) ImeAction.Done else ImeAction.Next

    // Crea el "solicitante de vista"
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    // Cuando el campo gana foco, pídele al scroll que lo traiga a la vista
    LaunchedEffect(isFocused) {
        if (isFocused) {
            // pequeño delay para esperar a que el teclado termine de subir
            delay(500)
            bringIntoViewRequester.bringIntoView()
        }
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            //.background(Color.White, RoundedCornerShape(spacing.md))
            .padding(spacing.md),
        verticalAlignment = Alignment.Top
    ) {

        IconForTextField(biomarker)

        Spacer(modifier = Modifier.width(spacing.lg))

        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {

            Text(
                text = biomarker.examName,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                color = IQuiuboColorPallete.primaryText,
                fontFamily = MyFont.geistMonoNerdFontRegular,
                fontWeight = FontWeight.Bold
            )

            BasicTextField(
                value = biomarker.value,
                onValueChange = {
                    fillingInToInterpVM.updateInfo(
                        info = biomarker,
                        value = it
                    )
                },
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = imeAction
                ),
                singleLine = true,
                textStyle = TextStyle(
                    color = IQuiuboColorPallete.primaryText,
                    fontFamily = MyFont.geistMonoNerdFontRegular,
                    fontSize = ResponsiveText.body(),
                ),
                decorationBox = { innerTextField ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                            //.dashedBorder(borderWidth, colorBorder),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .bottomLine(borderWidth, colorBorder)
                                .padding(bottom = spacing.xs)
                        ) {

                            if (biomarker.value.isEmpty()) {
                                Text(
                                    text = "Valor",
                                    fontSize = ResponsiveText.body(),
                                    lineHeight = ResponsiveLineHeight.body(),
                                    fontFamily = MyFont.jakartaSansRegular,
                                    color = Color.Gray,
                                )
                            }

                            innerTextField()
                        }

                        Spacer(modifier = Modifier.width(spacing.sm))

                        Text(
                            text = biomarker.unit,
                            fontSize = ResponsiveText.caption(),
                            fontFamily = MyFont.geistMonoNerdFontRegular,
                            color = IQuiuboColorPallete.secondaryText,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(
                                        topStart = spacing.md,
                                        topEnd = spacing.md,
                                        bottomStart = spacing.md
                                    )
                                )
                                .padding(horizontal = spacing.sm)
                        )
                    }
                }
            )
        }
    }

}



@Composable private fun IconForTextField(biomarker: FillingDataUI) {

    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .size(spacing.xxl)
            .background(IQuiuboColorPallete.accentSmooth, RoundedCornerShape(spacing.sm)),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = biomarker.icon,
            contentDescription = "bio_icon",
            modifier = Modifier.fillMaxSize(0.55f),
            tint = IQuiuboColorPallete.accentMiddle.copy(green = 0.5f)
        )
    }
}