package com.zhei.iquiubo.features.put_rna_values.presentation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.iquiubo.core.common.ButtonRippleColor
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.bottomLine
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.domain.model.BioMarker
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.ui.theme.ElectricBluePallete
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete


@Composable fun GetValuesForPriorityUI(
    getValuesForPriorityVM: GetValuesForPriorityVM = hiltViewModel(),
    onNavigate: (Scenes) -> Unit
) {

    val spacing = LocalSpacing.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            getValuesForPriorityVM.navEvent.collect {
                onNavigate(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ElectricBluePallete.BACKGROUND)
            .navigationBarsPadding()
            .imePadding()

    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            HeaderValuesPriority(getValuesForPriorityVM)

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = spacing.xl)
                    .padding(bottom = spacing.xl)
            ) {

                TextFieldArea(getValuesForPriorityVM)
            }
        }

        ButtonForCalculate(getValuesForPriorityVM)
    }
}



@Composable private fun HeaderValuesPriority(
    getValuesForPriorityVM: GetValuesForPriorityVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            //.bottomLine(1.dp, IQuiuboColorPallete.moradoPrimario.copy(alpha = 0.2f))
            .statusBarsPadding()
            .padding(spacing.xl),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        ArrowAndTitle(getValuesForPriorityVM)

        TextDescription()
    }
}



@Composable private fun ArrowAndTitle(
    getValuesForPriorityVM: GetValuesForPriorityVM
) {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "arrow",
            tint = IQuiuboColorPallete.backgroundLight,
            modifier = Modifier
                .size(spacing.xl)
                .clip(CircleShape)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            ElectricBluePallete.BLUE_2,
                            ElectricBluePallete.BLUE_5
                        )
                    )
                )
                .clickable { getValuesForPriorityVM.navigateTo(Scenes.HomeScene) }
                .padding(spacing.sm)
        )

        Text(
            text = "Datos de glucosas",
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            fontFamily = MyFont.jakartaSansExtrabold,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}



@Composable private fun TextDescription() {

    Text(
        text = "Ingresa tus valores de glucosa en ayunas y postprandial para calcular tu prioridad de acceder al examen de Hemoglobina glicosilada (HbA1c).",
        color = Color.Black,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        fontFamily = MyFont.workSansRegular,
        textAlign = TextAlign.Justify
    )
}



@Composable private fun ButtonForCalculate(
    getValuesForPriorityVM: GetValuesForPriorityVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Row(
        modifier = Modifier
            .padding(horizontal = spacing.xl)
            .padding(bottom = spacing.xl)
    ) {

        ButtonRippleColor(Color.Black) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.linearGradient(listOf(ElectricBluePallete.BLUE_1,
                        ElectricBluePallete.BLUE_5)), shape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = shape,
                onClick = { getValuesForPriorityVM.nextInterface(Scenes.ShowPriorityResultsScene) }
            ) {

                Text(
                    text = "Calcular prioridad",
                    color = Color.White,
                    fontFamily = MyFont.workSansRegular,
                    fontSize = ResponsiveText.button(),
                    lineHeight = ResponsiveLineHeight.button(),
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(spacing.md))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "arro",
                    tint = Color.White
                )
            }
        }
    }
}



/**
 * Este composable esta ya dentro de una (Column()), entonces,
 * no será necesario añadir otro.
 * */
@Composable private fun TextFieldArea(
    getValuesForPriorityVM: GetValuesForPriorityVM
) {

    val uiState by getValuesForPriorityVM.uiState.collectAsStateWithLifecycle()
    val list by getValuesForPriorityVM.examList.collectAsStateWithLifecycle()

    val isFastingError = (uiState as? GetValuesForPriorityUIState.FastingError)?.message
    val isPostprandialError = (uiState as? GetValuesForPriorityUIState.PostprandialError)?.message

    val fastingValue = list
        .firstOrNull { it.exam == BioMarker.GLUCOSE_FASTING.displayName }
        ?.value.orEmpty()

    val postprandialValue = list
        .firstOrNull { it.exam == BioMarker.GLUCOSE_POSTPRANDIAL.displayName }
        ?.value.orEmpty()


    CustomTextFieldExams(
        value = fastingValue,
        onValueChange = {
            getValuesForPriorityVM.updateBiomaker(
                biomarkerName = BioMarker.GLUCOSE_FASTING.displayName,
                value = it
            )
        },
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        isError = isFastingError != null,
        errorMessage = isFastingError,
        leyend = "Resultado de tu última prueba en ayunas, sin alimentos previos",
        label = BioMarker.GLUCOSE_FASTING.displayName,
        leadingIcon = BioMarker.GLUCOSE_FASTING.icon

        // Terminar de configurar los isError
    )

    Spacer(Modifier.height(LocalSpacing.current.xl))

    CustomTextFieldExams(
        value = postprandialValue,
        onValueChange = {
            getValuesForPriorityVM.updateBiomaker(
                biomarkerName = BioMarker.GLUCOSE_POSTPRANDIAL.displayName,
                value = it
            )
        },
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Done,
        isError = isPostprandialError != null,
        errorMessage = isPostprandialError,
        leyend = "Medición tomada 2 horas después de tu última comida",
        label = BioMarker.GLUCOSE_POSTPRANDIAL.displayName,
        leadingIcon = BioMarker.GLUCOSE_POSTPRANDIAL.icon

        // Terminar de configurar los isError
    )

}



@Composable private fun CustomTextFieldExams(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    imeAction: ImeAction,
    leyend: String,
    label: String? = null,
    textFake: String = "Escribe aquí",
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Number
) {

    val spacing = LocalSpacing.current

    val interactionResource = remember { MutableInteractionSource() }
    val isFocused by interactionResource.collectIsFocusedAsState()

    // Animación del borde según foco/error
    val borderColor by animateColorAsState(
        targetValue = when {
            isError   -> Color(0xFFD64545)
            isFocused -> Color.Gray
            else      -> Color.LightGray
        },
        animationSpec = tween(250),
        label = "border_color"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        label = "border_width"
    )

    // Crea el "solicitante de vista"
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {

        label?.let {
            Text(
                text = it,
                color = Color.Black,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionResource,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                fontFamily = MyFont.geistMonoNerdFontRegular
            ),
            cursorBrush = SolidColor(IQuiuboColorPallete.moradoPrimario),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bottomLine(borderWidth, borderColor)
                        .padding(vertical = spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.sm)
                ) {

                    leadingIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "icon",
                            tint = if (isFocused) Color(0xFFD64545) else Color.White,
                            modifier = Modifier.size(spacing.xl)
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        if (value.isEmpty()) {
                            Text(
                                text = textFake,
                                color = Color.Gray,
                                fontFamily = MyFont.workSansRegular,
                                fontSize = ResponsiveText.body(),
                                lineHeight = ResponsiveLineHeight.body()
                            )
                        }

                        innerTextField()
                    }

                    Text(
                        text = "mg/dL",
                        color = Color.LightGray,
                        fontFamily = MyFont.workSansRegular,
                        fontSize = ResponsiveText.small(),
                        lineHeight = ResponsiveLineHeight.small(),
                        fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        )

        // Mensaje de error que emerge
        AnimatedVisibility(visible = isError && errorMessage != null) {

            LaunchedEffect(Unit) {
                bringIntoViewRequester.bringIntoView()
            }

            errorMessage?.let {
                Text(
                    text = it,
                    color = Color(0xFFD64545),
                    fontSize = ResponsiveText.caption(),
                    lineHeight = ResponsiveLineHeight.caption(),
                    fontFamily = MyFont.workSansRegular
                )
            }
        }

        Text(
            text = leyend,
            color = Color.Gray,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small()
        )
    }
}