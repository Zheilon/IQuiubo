package com.zhei.hikibo.features.personal_info.presentation
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.hikibo.core.common.ButtonRippleColor
import com.zhei.hikibo.core.common.Constants
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay


@Composable fun PersonalInfoUI(
    personalInfoVM: PersonalInfoVM = hiltViewModel(),
    onNavigate: () -> Unit
) {

    val spacing = LocalSpacing.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Log.e("WIDTH DP DEVICE", Constants.WIDTH_DP_DEVICE().toString())

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            personalInfoVM.navEvents.collect {
                when(it) {
                    is PersonalInfoVM.NavEvent.ToNext -> { onNavigate() }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Amarillo fondo anterior: Color(0xFFFFD93D)
            .background(IQuiuboColorPallete.backgroundLight)
    ) {

        //Image(
        //    painter = painterResource(id = R.drawable.back_get_username_v4),
        //    contentDescription = "back_get_user_name",
        //    modifier = Modifier.fillMaxSize(),
        //    contentScale = ContentScale.Crop
        //)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.xl)
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
                verticalArrangement = Arrangement.Bottom
            ) {

                TitleFirst()

                Spacer(modifier = Modifier.height(spacing.md))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = spacing.xxl),
                    color = Color(0xFF9333EA).copy(0.2f),
                    thickness = 2.dp
                )

                Spacer(modifier = Modifier.height(spacing.md))

                TextToAdvertiserToUser()

                Spacer(modifier = Modifier.height(spacing.xl))

                TextFieldsArea(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.lg))

                TypeSexArea(personalInfoVM)

                Spacer(modifier = Modifier.height(spacing.xs))
            }

            // BOTÓN FIJO: fuera del weight → nunca se comprime
            Spacer(Modifier.height(spacing.xl))

            ButtonForNextScene(personalInfoVM)

            Spacer(Modifier.height(spacing.md))
        }

    }
}


@Composable private fun TitleFirst() {

    val spacing = LocalSpacing.current

    Text(
        text = "Información\npersonal",
        fontSize = ResponsiveText.displaymiddle(),
        lineHeight = ResponsiveText.displaymiddle(),
        textAlign = TextAlign.Center,
        fontFamily = MyFont.workSansRegular,
        color = Color(0xFF7E22CE),
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

    val lenby3 = (Constants.WIDTH_DP_DEVICE() - (spacing.xl * 2) ) / 3

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
            isFocused -> Color(0xFF7E22CE)
            else      -> Color(0xFFE9D5FF)
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
                fontFamily = MyFont.jakartaSansRegular,
                color = Color(0xFF7E22CE),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .background(color = Color(0xFFFFFFFF), RoundedCornerShape(20.dp))
                    .padding(start = spacing.sm, end = spacing.lg, bottom = spacing.xs)
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

        LaunchedEffect(isError) {
            if (isError)
                bringIntoViewRequester.bringIntoView()
        }

        // 👇 Mensaje de error que APARECE animado
        AnimatedVisibility(visible = isError && errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color(0xFFD64545),
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                modifier = Modifier.padding(top = 4.dp),
                fontFamily = MyFont.spaceGroteskRegular
            )
        }
    }
}



@Composable private fun TypeSexArea(
    personalInfoVM: PersonalInfoVM
) {

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
            color = Color(0xFF7E22CE),
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
                fontSize = ResponsiveText.body(),
                lineHeight = ResponsiveLineHeight.body(),
                fontFamily = MyFont.spaceGroteskRegular,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
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
                    containerColor = IQuiuboColorPallete.buttonEnabledOne
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