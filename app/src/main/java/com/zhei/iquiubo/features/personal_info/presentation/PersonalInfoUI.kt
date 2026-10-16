package com.zhei.iquiubo.features.personal_info.presentation
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.iquiubo.core.common.ButtonRippleColor
import com.zhei.iquiubo.core.common.Constants
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.domain.model.NeuralNetConfig
import com.zhei.iquiubo.ui.theme.ElectricBluePallete
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop


@Composable fun PersonalInfoUI(
    personalInfoVM: PersonalInfoVM = hiltViewModel(),
    onNavigate: () -> Unit
) {

    val spacing = LocalSpacing.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Log.e("WIDTH DP DEVICE", Constants.widthDpDevice().toString())

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            personalInfoVM.navEvents.collect {
                when (it) {
                    is PersonalInfoVM.NavEvent.ToNext -> { onNavigate() }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Amarillo fondo anterior: Color(0xFFFFD93D)
            .background(ElectricBluePallete.BACKGROUND)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.md, vertical = spacing.md)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {

            // ZONA QUE CEDE: ocupa el espacio sobrante y hace scroll si no cabe
            // Para column utilizar: .verticalScroll(),
            // Para Row utilizar: .horizontalScroll()
            // Bueno con ambas se puede

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {

                TitleFirst()

                Spacer(modifier = Modifier.height(spacing.md))

                TextToAdvertiserToUser()

                Spacer(modifier = Modifier.height(spacing.xl))

                TextFieldsArea(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                TypeSexAreaV2(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                SelectFamilyHistoryDiabetes(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                SelectActivityLevelArea(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                SelectHeightCmArea(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                SelectWeightKg(personalInfoVM)

            }

            // BOTÓN FIJO: fuera del weight → nunca se comprime
            Spacer(Modifier.height(spacing.xl))

            ButtonForNextScene(personalInfoVM)
        }

    }
}



/**
 * Función personalizada para poner Una (Imagen) y un (texto)
 * en algún componente. Se desarrollo de esta manera para
 * garantizar su usabilidad en distintos componentes.
 * */
@Composable private fun RowCustomTitle(
    icon: ImageVector,
    title: String,
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    Row(
        modifier = Modifier.wrapContentWidth()
            .background(ElectricBluePallete.BLUE_5.copy(alpha = 0.1f), shape)
            .padding(horizontal = spacing.md, vertical = spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = "custom_icon",
            tint = ElectricBluePallete.BLUE_2,
            modifier = Modifier.size(spacing.md)
        )

        Text(
            text = title,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            color = ElectricBluePallete.BLUE_2,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable private fun TitleFirst() {

    val spacing = LocalSpacing.current

    Text(
        text = "Información\npersonal",
        fontSize = ResponsiveText.displaymiddle(),
        lineHeight = ResponsiveText.displaymiddle(),
        textAlign = TextAlign.Center,
        fontFamily = MyFont.jakartaSansExtrabold,
        color = ElectricBluePallete.BLUE_2,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.fillMaxWidth()
    )
}



@Composable private fun TextToAdvertiserToUser() {

    val spacing = LocalSpacing.current

    Text(
        text = "Ingresa tus datos reales: con ellos calculamos tus métricas y resultados de salud.",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color(0xFF6E6685)
    )
}



/**
 * No será necesario poner Columns ya que esta
 * función se encuentra dentro de un column
 * */
@Composable private fun TextFieldsArea(
    personalInfoVM: PersonalInfoVM
) {

    val firstName by personalInfoVM.firstName.collectAsStateWithLifecycle()
    val firsLastName by personalInfoVM.firstLastName.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()

    GetUsernameBasicTextfeild(
        value = firstName,
        onValueChange = { personalInfoVM.updateFirstName(it) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = Icons.Default.DriveFileRenameOutline,
        label = "Ingresa tu primer nombre",
        textFake = "Juanito",
        isError = uiState is PersonalInfoUiState.NameError,
        errorMessage = if (uiState is PersonalInfoUiState.NameError) (uiState as PersonalInfoUiState.NameError).message else null
    )

    Spacer(modifier = Modifier.height(spacing.lg))

    GetUsernameBasicTextfeild(
        value = firsLastName,
        onValueChange = { personalInfoVM.updateFirstLastName(it) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = Icons.Default.DriveFileRenameOutline,
        label = "Ingresa tu primer apellido",
        textFake = "Montelito",
        isError = uiState is PersonalInfoUiState.FirstLastNameError,
        errorMessage = if (uiState is PersonalInfoUiState.FirstLastNameError) (uiState as PersonalInfoUiState.FirstLastNameError).message else null
    )

    Spacer(modifier = Modifier.height(spacing.lg))

    BirthDayTextFieldsArea(personalInfoVM)
}



@Composable
fun BirthDayTextFieldsArea(
    personalInfoVM: PersonalInfoVM
) {

    val day by personalInfoVM.day.collectAsState()
    val month by personalInfoVM.month.collectAsState()
    val year by personalInfoVM.year.collectAsState()

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    val lenby3 = (Constants.widthDpDevice() - (spacing.xl * 2) ) / 3

    Text(
        text = "Ingresa tu fecha de nacimiento",
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveText.caption(),
        textAlign = TextAlign.Start,
        fontFamily = MyFont.workSansRegular,
        color = Color(0xFF211A2E),
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.fillMaxWidth()

    )

    ShowStringDate(personalInfoVM)

    ShowErrorBirthDate(personalInfoVM)

    Spacer(modifier = Modifier.height(spacing.md))

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        GetUsernameBasicTextfeild(
            value = day,
            onValueChange = { personalInfoVM.updateDay(it) },
            modifier = Modifier.width(lenby3),
            leadingIcon = Icons.Default.DriveFileRenameOutline,
            keyboardType = KeyboardType.Number,
            label = "Día",
            textFake = "x",
            isError = uiState is PersonalInfoUiState.DateError
        )

        GetUsernameBasicTextfeild(
            value = month,
            onValueChange = { personalInfoVM.updateMonth(it) },
            modifier = Modifier.width(lenby3),
            leadingIcon = Icons.Default.DriveFileRenameOutline,
            keyboardType = KeyboardType.Number,
            label = "Mes",
            textFake = "x",
            isError = uiState is PersonalInfoUiState.DateError
        )

        GetUsernameBasicTextfeild(
            value = year,
            onValueChange = { personalInfoVM.updateYear(it) },
            modifier = Modifier.width(lenby3),
            leadingIcon = Icons.Default.DriveFileRenameOutline,
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number,
            label = "Año",
            textFake = "xxxx",
            isError = uiState is PersonalInfoUiState.DateError
        )
    }
}



@Composable private fun ShowStringDate(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current

    val day by personalInfoVM.day.collectAsStateWithLifecycle()
    val month by personalInfoVM.month.collectAsStateWithLifecycle()
    val year by personalInfoVM.year.collectAsStateWithLifecycle()

    val cond = day.isNotEmpty() || month.isNotEmpty() || year.isNotEmpty()

    if (cond) {
        Text(
            text = buildAnnotatedString {
                if (day.isNotEmpty())
                    append("$day ")

                if (month.isNotEmpty())
                    if (month.toInt() in 1..12)
                        append("de ${Constants.STRING_MONTHS[month.toInt() - 1]} ")

                if (year.isNotEmpty())
                    append("del $year")
            },
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.workSansRegular,
            color = Color(0xFF6E6685),
            modifier = Modifier.fillMaxWidth()
        )
    }
}



@Composable private fun ShowErrorBirthDate(
    personalInfoVM: PersonalInfoVM
) {

    val personalInfoUiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when(val s = personalInfoUiState) {
        is PersonalInfoUiState.DateError -> {
            Text(
                text = s.message,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveText.caption(),
                textAlign = TextAlign.Start,
                fontFamily = MyFont.workSansRegular,
                color = Color(0xFFD64545),
                modifier = Modifier.fillMaxWidth()
            )
        }

        else -> {}
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable private fun GetUsernameBasicTextfeild(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String? = null,
    textFake: String = "Escribe aquí",
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false
) {

    val spacing = LocalSpacing.current

    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Animación del borde según foco/error
    val borderColor by animateColorAsState(
        targetValue = when {
            isError   -> Color(0xFFD64545)
            isFocused -> ElectricBluePallete.BLUE_2
            else      -> ElectricBluePallete.BLUE_5.copy(alpha = 0.5f)
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

    // Cuando el campo gana foco, pídele al scroll que lo traiga a la vista
    LaunchedEffect(isFocused) {
        if (isFocused) {
            // pequeño delay para esperar a que el teclado termine de subir
            delay(500)
            bringIntoViewRequester.bringIntoView()
        }
    }

    Column(
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {

        label?.let {
            Text(
                text = it,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                color = if (isError) Color(0xFFD64545) else ElectricBluePallete.BLUE_2,
                fontWeight = if (isFocused) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .background(color = Color(0xFFFFFFFF), RoundedCornerShape(5.dp))
                    .padding(start = spacing.sm, end = spacing.md, bottom = spacing.xs)
            )

            Spacer(modifier = Modifier.height(5.dp))
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = ResponsiveText.body(),
                fontFamily = MyFont.spaceGroteskRegular
            ),
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,
            cursorBrush = SolidColor(Color(0xFF0062A3)),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            decorationBox = { innerTextField ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            val strokeWidth = borderWidth.toPx()
                            val y = size.height - strokeWidth / 2

                            drawLine(
                                color = borderColor,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(vertical = spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    leadingIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = borderColor,
                            modifier = Modifier.size(spacing.md)
                        )
                        Spacer(Modifier.width(spacing.sm))
                    }

                    // En esta parte es donde se encuentra el texto.
                    
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        if (value.isEmpty()) {
                            Text(
                                text = textFake,
                                color = Color.Gray,
                                fontFamily = MyFont.spaceGroteskRegular,
                                fontSize = ResponsiveText.body(),
                                lineHeight = ResponsiveLineHeight.body()
                            )
                        }

                        innerTextField()
                    }

                    if (isPassword) {

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = borderColor,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    passwordVisible = !passwordVisible
                                }
                        )
                    }
                }
            }
        )

        // 👇 Mensaje de error que APARECE animado
        AnimatedVisibility(visible = isError && errorMessage != null) {

            LaunchedEffect(Unit) {
                bringIntoViewRequester.bringIntoView()
            }

            Text(
                text = errorMessage ?: "",
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = MyFont.workSansRegular
            )
        }
    }
}



@Composable private fun TypeSexAreaV2(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RowCustomTitle(
            icon = Icons.Filled.Wc,
            "Sexo"
        )

        Text(
            text = "Selecciona tu sexo biológico",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = Color.Black
        )

        HorizontalDivider(color = ElectricBluePallete.BLUE_5.copy(alpha = 0.5f))

        ErrorSexArea(personalInfoVM)

        SelectYourSexBoxAreaV2(personalInfoVM)
    }
}



private enum class TypeSex(val sex: String) {
    Femenino("Femenino"),
    Masculino("Masculino"),
    Otro("Otro");
}



@Composable private fun SelectYourSexBoxAreaV2(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.xs)
    ) {

        ItemToSelectSexV2(
            vm = personalInfoVM,
            key = TypeSex.Femenino
        )

        ItemToSelectSexV2(
            vm = personalInfoVM,
            key = TypeSex.Masculino
        )

        ItemToSelectSexV2(
            vm = personalInfoVM,
            key = TypeSex.Otro
        )
    }
}



@Composable private fun ItemToSelectSexV2(
    vm: PersonalInfoVM,
    key: TypeSex
) {


    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)
    val sexSelected by vm.selectedSex.collectAsStateWithLifecycle()

    val defaultColor = when(key) {
        TypeSex.Femenino -> Color(0xFFFFD6FF)
        TypeSex.Masculino -> Color(0xFFD6F0FF)
        else -> Color.LightGray.copy(alpha = 0.5f)
    }

    val selectedColor = when(key) {
        TypeSex.Femenino -> Color(0xFFFF5959)
        TypeSex.Masculino -> ElectricBluePallete.BLUE_3
        else -> Color.DarkGray
    }

    val isSelected = key.sex == sexSelected

    val backgroud = if (isSelected) selectedColor else defaultColor

    ButtonRippleColor(Color.Black) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroud
            ),
            shape = shape,
            onClick = { vm.setSelectedSex(key.sex) }
        ) {

            Text(
                text = key.sex,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}



@Composable private fun TypeSexArea(
    personalInfoVM: PersonalInfoVM
) {

    val uistate by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        
        Text(
            text = "Sexo",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.jakartaSansRegular,
            color = if (uistate is PersonalInfoUiState.SexError) Color(0xFFD64545) else IQuiuboColorPallete.moradoPrimario,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .background(color = Color.White, RoundedCornerShape(20.dp))
                .padding(start = spacing.sm, end = spacing.lg, bottom = spacing.xs)
        )
        
        Spacer(modifier = Modifier.width(spacing.lg))
        
        Row(
            modifier = Modifier.weight(1f), 
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = "F",
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                fontFamily = MyFont.workSansRegular,
                fontWeight = FontWeight.ExtraBold,
            )
            
            Spacer(modifier = Modifier.width(spacing.md))
            
            SelectBoxSexArea(personalInfoVM, "F")
            
            Spacer(modifier = Modifier.width(spacing.xxl))

            Text(
                text = "M",
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                fontFamily = MyFont.workSansRegular,
                fontWeight = FontWeight.ExtraBold,
            )

            Spacer(modifier = Modifier.width(spacing.md))

            SelectBoxSexArea(personalInfoVM, "M")
        }
    }

    ErrorSexArea(personalInfoVM)
}



@Composable
private fun SelectBoxSexArea(
    personalInfoVM: PersonalInfoVM,
    key: String
) {

    val selectedSex by personalInfoVM.selectedSex.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    val defaultColor = if (key == "F") Color(0xFFFFD6FF) else Color(0xFFD6F0FF)
    val selectedColor = if (key == "F") Color(0xFFFF5959) else Color(0xFF59A4FF)

    val isSelected = key == selectedSex

    // Animación suave del color
    val boxColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else defaultColor,
        label = "sex_box_color"
    )

    val icon = if (isSelected) Icons.Default.Check else null

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = boxColor),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(spacing.xl),
        onClick = { personalInfoVM.setSelectedSex(key) }
    ) {

        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "check_box",
                modifier = Modifier.size(spacing.lg),
                tint = Color.White
            )
        }
    }
}



@Composable private fun ErrorSexArea(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when (val s = uiState) {
        is PersonalInfoUiState.SexError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        else -> {}
    }
}



@Composable private fun SelectActivityLevelArea(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RowCustomTitle(
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            title = "Nivel de actividad"
        )

        Text(
            text = "Selecciona tu nivel de actividad",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = Color.Black
        )

        HorizontalDivider(color = ElectricBluePallete.BLUE_5.copy(alpha = 0.5f))

        ErrorActivityLevelArea(personalInfoVM)

        Box (
            contentAlignment = Alignment.Center
        ) {

            HorizontalDivider(color = ElectricBluePallete.BLUE_5.copy(alpha = 0.5f))

            AreaBoxesToSelectActivityLevel(personalInfoVM)
        }
    }
}



@Composable private fun TitleActivityLevel() {

    val spacing = LocalSpacing.current

    Text(
        text = "Nivel de actividad",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        color = ElectricBluePallete.BLUE_2,
        fontWeight = FontWeight.Bold
    )
}



@Composable private fun ErrorActivityLevelArea(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when (val s = uiState) {
        is PersonalInfoUiState.ActivityLevelError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        else -> {}
    }
}



@Composable private fun AreaBoxesToSelectActivityLevel(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        SeletedBoxActivityLevel(
            Modifier.weight(1f),
            personalInfoVM,
            NeuralNetConfig.ActivityValues.Baja.value
        )

        SeletedBoxActivityLevel(
            Modifier.weight(1f),
            personalInfoVM,
            NeuralNetConfig.ActivityValues.Media.value
        )

        SeletedBoxActivityLevel(
            Modifier.weight(1f),
            personalInfoVM,
            NeuralNetConfig.ActivityValues.Alta.value
        )
    }
}



@Composable private fun SeletedBoxActivityLevel(
    modifier: Modifier,
    personalInfoVM: PersonalInfoVM,
    key: String
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    val activitySelected by personalInfoVM.activityLevel.collectAsStateWithLifecycle()
    val isSelected = key == activitySelected

    val color = if (isSelected) ElectricBluePallete.BLUE_3 else Color.White
    val fontColor = if (isSelected) Color.White else Color.Black
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    ButtonRippleColor(Color.Black) {
        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            ),
            shape = shape,
            border = BorderStroke(1.dp, ElectricBluePallete.BLUE_3),
            onClick = { personalInfoVM.setActivityLevel(key) }
        ) {

            Text(
                text = key,
                color = fontColor,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                fontWeight = fontWeight
            )
        }
    }
}



@Composable private fun SelectFamilyHistoryDiabetes(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RowCustomTitle(
            icon = Icons.Filled.PersonSearch,
            title = "Antecedente familiar"
        )

        Text(
            text = buildAnnotatedString {
                append("¿En tu familia hubo casos de ")
                withStyle(
                    SpanStyle(
                        color = ElectricBluePallete.BLUE_2,
                        fontWeight = FontWeight.Bold
                    )
                ) { append("Diabetes") }
                append("?")
            },
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = Color.Black
        )

        HorizontalDivider(color = ElectricBluePallete.BLUE_5.copy(alpha = 0.5f))

        ErrorFamilyHistoryDiabetesArea(personalInfoVM)

        Box (
            contentAlignment = Alignment.Center
        ) {

            HorizontalDivider(color = ElectricBluePallete.BLUE_5.copy(alpha = 0.5f))

            YesNoHistoryDiabetes(personalInfoVM)
        }


    }
}



@Composable private fun TitleFamilyHistoryDiabetes() {

    Text(
        text = "¿En tu familia hubo casos de Diabetes?",
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        fontFamily = MyFont.workSansRegular,
        color = Color.White
    )
}



@Composable private fun ErrorFamilyHistoryDiabetesArea(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when (val s = uiState) {
        is PersonalInfoUiState.FamilyHistoryDiabetesError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        else -> {}
    }
}



@Composable private fun YesNoHistoryDiabetes(personalInfoVM: PersonalInfoVM) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        HistoryButton(Modifier.weight(1f), personalInfoVM, NeuralNetConfig.FamilyHistoryValues.No.value)

        HistoryButton(Modifier.weight(1f), personalInfoVM, NeuralNetConfig.FamilyHistoryValues.Yes.value)
    }
}



@Composable private fun HistoryButton(
    modifier: Modifier,
    personalInfoVM: PersonalInfoVM,
    key: String
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    val familyHistory by personalInfoVM.familyHistoryDiabetes.collectAsStateWithLifecycle()
    val isSelected = key == familyHistory

    val color = if (isSelected) ElectricBluePallete.BLUE_3 else Color.White
    val fontColor = if (isSelected) Color.White else Color.Black
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    ButtonRippleColor(Color.Black) {
        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            ),
            border = BorderStroke(1.dp, ElectricBluePallete.BLUE_3),
            shape = shape,
            onClick = { personalInfoVM.setFamilyHistoryDiabetes(key) }
        ) {

            Text(
                text = key,
                color = fontColor,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                fontWeight = fontWeight
            )
        }
    }
}



@Composable private fun SelectHeightCmArea(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val height by personalInfoVM.heightCm.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RowCustomTitle(
            icon = Icons.Filled.Straighten,
            title = "Ingresa tu altura"
        )

        Text(
            text = "${if ((height.toIntOrNull() ?: 0) == 0) 0.0 else height.toFloat() / 100.0} mts",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = ElectricBluePallete.BLUE_2,
            fontFamily = MyFont.workSansRegular,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        ErrorHeightCmArea(personalInfoVM)

        HeightRulerPicker(
            units = "Cm",
            modifier = Modifier.fillMaxWidth(),
            initialValue = 0,
            onValueChange = { personalInfoVM.setHeightCm(it.toString()) }
        )
    }
}



@Composable private fun ErrorHeightCmArea(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when (val s = uiState) {
        is PersonalInfoUiState.HeightCmError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        else -> {}
    }
}



@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HeightRulerPicker(
    units: String,
    modifier: Modifier = Modifier,
    initialValue: Int = 170,
    range: IntRange = 0..220,
    accentColor: Color = Color(0xFFFFD54F),
    tickColor: Color = Color.Black.copy(alpha = 0.6f),
    onValueChange: (Int) -> Unit
) {
    val itemWidth = 10.dp
    val itemWidthPx = with(LocalDensity.current) { itemWidth.toPx() }
    val count = range.last - range.first + 1

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialValue - range.first).coerceIn(0, count - 1)
    )
    val flingBehavior = rememberSnapFlingBehavior(listState)
    val haptic = LocalHapticFeedback.current

    // Valor "en vivo" mientras haces scroll
    val selected by remember {
        derivedStateOf {
            val extra = if (listState.firstVisibleItemScrollOffset > itemWidthPx / 2) 1 else 0
            (listState.firstVisibleItemIndex + extra).coerceIn(0, count - 1) + range.first
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { selected }.collect { onValueChange(it) }
    }
    LaunchedEffect(listState) {
        snapshotFlow { selected }.drop(1).collect {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Número grande
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "$selected",
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = MyFont.geistMonoNerdFontRegular
            )
            Text(
                text = " $units",
                fontSize = 24.sp,
                color = Color.Black.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            // Padding lateral para que el primer y último valor puedan llegar al centro
            val sidePadding = (maxWidth - itemWidth) / 2

            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(horizontal = sidePadding),
                modifier = Modifier
                    .fillMaxWidth()
                    // Difumina los bordes izquierdo y derecho
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.horizontalGradient(
                                0f to Color.Transparent,
                                0.2f to Color.Black,
                                0.8f to Color.Black,
                                1f to Color.Transparent
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    }
            ) {
                items(count) { index ->
                    RulerTick(
                        value = index + range.first,
                        itemWidth = itemWidth,
                        tickColor = tickColor
                    )
                }
            }

            // Indicador fijo al centro
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .width(4.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(accentColor)
            )
        }
    }
}



@Composable
private fun RulerTick(
    value: Int,
    itemWidth: Dp,
    tickColor: Color
) {
    val tickHeight = when {
        value % 10 == 0 -> 44.dp
        value % 5 == 0 -> 32.dp
        else -> 20.dp
    }

    Column(
        modifier = Modifier.width(itemWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(tickHeight)
                .clip(RoundedCornerShape(1.dp))
                .background(tickColor)
        )

        if (value % 10 == 0) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = "$value",
                fontSize = 12.sp,
                color = tickColor,
                softWrap = false,
                // El texto es más ancho que el item, no debe recortarse
                modifier = Modifier.wrapContentWidth(unbounded = true)
            )
        }
    }
}



@Composable private fun SelectWeightKg(
    personalInfoVM: PersonalInfoVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val height by personalInfoVM.heightCm.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RowCustomTitle(
            icon = Icons.Filled.MonitorWeight,
            title = "Ingresa tu peso"
        )

        ErrorWeightKgArea(personalInfoVM)

        HeightRulerPicker(
            units = "Kg",
            modifier = Modifier.fillMaxWidth(),
            initialValue = 0,
            onValueChange = { personalInfoVM.setWeightKg(it.toString()) }
        )
    }
}



@Composable private fun ErrorWeightKgArea(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when (val s = uiState) {
        is PersonalInfoUiState.WeightKgError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        else -> {}
    }
}



@Composable private fun OnButtonErrorShow(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    when(val s = uiState) {
        is PersonalInfoUiState.OnButtonSendError -> {
            Text(
                text = s.message,
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                fontFamily = MyFont.spaceGroteskRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(spacing.md))
        }

        else -> {}
    }
}



@Composable private fun ButtonForNextScene(
    personalInfoVM: PersonalInfoVM
) {

    val uiState by personalInfoVM.personalInfoUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.xxl)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "button_scaling"
    )


    // Este error lo puedo poner porque, el cuerpo
    // general del composable esta embevido dentro de un
    // Column().

    OnButtonErrorShow(personalInfoVM)

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        ButtonRippleColor(color = Color.Black) {

            Button(
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ElectricBluePallete.BLUE_2
                ),
                shape = shape,
                onClick = {
                    personalInfoVM.readyToNav()
                }
            ) {

                Text(
                    text = "¡Adelante!",
                    fontFamily = MyFont.workSansRegular,
                    color = IQuiuboColorPallete.textButtonOne,
                    fontSize = ResponsiveText.title(),
                    fontWeight = FontWeight.Bold
                )

                if (uiState is PersonalInfoUiState.Loading) {
                    Spacer(modifier = Modifier.width(spacing.lg))
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }


    }
}