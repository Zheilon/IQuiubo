package com.zhei.hikibo.features.login.presentation
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.AttachEmail
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.hikibo.R
import com.zhei.hikibo.core.common.ButtonRippleColor
import com.zhei.hikibo.core.common.Constants
import com.zhei.hikibo.core.common.LockScreenOrientation
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


// Names: hoppy
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable fun LoginUI(
    loginVM: LoginVM = hiltViewModel(),
    onNavigate: () -> Unit
) {

    LaunchedEffect(Unit) {
        loginVM.lazyInitialization()
    }

    // En esta linea de código idiomáticamente se colecciona consciente
    // del ciclo de vida, para no escuchar eventos mientras la pantalla
    // está en segundo plano.

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            loginVM.navEvents.collect {
                when (it) { LoginVM.LoginNavEvent.ToNext -> onNavigate() }
            }
        }
    }
    
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val blurCondition = loginVM.showPolicity.value

    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(if (blurCondition) 12.dp else 0.dp)
            // Azul turqueza back = 0xFF0062A3
            .background(Color(0xFFF8F5FF))
    ) {

        // Image(
        //     painter = painterResource(id = R.drawable.back_login_v11),
        //     contentDescription = null,
        //     contentScale = ContentScale.Crop,
        //     modifier = Modifier.fillMaxSize()
        // )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.lg)
                .padding(bottom = Constants.DEVICE_BOTTOM_PADDING()),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextUpperLoginBox()

            Spacer(modifier = Modifier.height(spacing.xl))

            BoxToJoinWithSomething(loginVM)

            Spacer(modifier = Modifier.height(spacing.xs))

            PolicyAndPrivArea(loginVM)

            Spacer(modifier = Modifier.height(spacing.sm))

            FirebaseSendCapError(loginVM)

            Spacer(modifier = Modifier.height(5.dp))

            ButtonLastToContinue(loginVM)

            Spacer(modifier = Modifier.height(10.dp))
        }

    }

    ShowPolicityBox(loginVM)
} 


@Composable private fun ImagenCenter() {

    var state by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        state = true
    }

    val animeTranslation by animateFloatAsState(
        targetValue = if (state) 0f else 800f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (state) 1f else 0.6f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = ""
    )

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.hv3_sin_fondo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .widthIn(max = 220.dp) // tope para tablets
                .graphicsLayer {
                    this.translationX = animeTranslation
                    scaleX = scale
                    scaleY = scale
                }
        )
    }
}


@Composable private fun TextUpperLoginBox() {

    // Atención: Este box esta puesto con la intención de que, primero
    // por detras se vea la linea divisoria, y encima de esta la
    // palabra Hikibo.

    val spacing = LocalSpacing.current
    val shapePill = RoundedCornerShape(10.dp)

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(spacing.xxl)
                .background(IQuiuboColorPallete.moradoPrimario, shapePill),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.SettingsAccessibility,
                contentDescription = null,
                tint = IQuiuboColorPallete.backgroundLight,
                modifier = Modifier.size(spacing.xl)
            )
        }

        Spacer(modifier = Modifier.width(spacing.md))

        Text(
            text = buildAnnotatedString { append("IQuiubo") },
            color = IQuiuboColorPallete.primaryText,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.displayBig(),
            lineHeight = ResponsiveLineHeight.displayBig(),
            fontWeight = FontWeight.ExtraBold,
        )
    }
}


@Composable private fun BoxToJoinWithSomething(
    loginVM: LoginVM
) {

    val googleLogged by loginVM.isLoggedWithGoogle
    val emailLogged by loginVM.isLoggedWithEmail
    val logged = googleLogged || emailLogged

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(20.dp)


    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {


        TextInTheMiddleV2()

        //if (!logged) {
        //    TextToSignInWithSomething()
        //}
        
        Spacer(modifier = Modifier.height(spacing.xl))

        if (!emailLogged) {
            ButtonToSignInWithGoogle(loginVM)
            Spacer(modifier = Modifier.height(spacing.md))
        }
            
        if (!googleLogged) {
            ButtonToSignInWithEmail(loginVM)
            Spacer(modifier = Modifier.height(spacing.md))
        }

        if (!logged) {
            IfYouDontHaveAccount(loginVM)
            Spacer(modifier = Modifier.height(spacing.md))
        }
    }
}


@Composable private fun TextTitleInMiddle(
    loginVM: LoginVM
) {
    val phrase by loginVM.valuePhrase
    val isTyping by loginVM.isTyping.collectAsState()


    val transition = rememberInfiniteTransition(label = "cursor")
    val cursorAlpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label = "cursor_alpha"
    )

    Text(
        text = buildAnnotatedString {
            append(phrase)
            if (isTyping) {
                withStyle(SpanStyle(color = Color(0xFFF0815C)))
                { append("_") }
            }
        },
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = Color(0xFF0C3A47),
        fontFamily = MyFont.geistMonoNerdFontRegular,
    )
}


@Composable private fun TextInTheMiddleV2() {
    val spacing = LocalSpacing.current

    val inlineId = "inteligente"

    Text(
        text = buildAnnotatedString {
            append("Tu camino hacia un control metabólico ")
            appendInlineContent(inlineId, "inteligente")  // 👈 placeholder
        },
        inlineContent = mapOf(
            inlineId to InlineTextContent(
                placeholder = Placeholder(
                    width = 5.9.em,          // ancho reservado (ajústalo al texto)
                    height = 1.3.em,         // alto reservado
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                // 👇 Este composable se renderiza DENTRO del texto
                Box(
                    modifier = Modifier
                        .background(
                            color = IQuiuboColorPallete.accentMiddle,
                            shape = RoundedCornerShape(8.dp)      // 👈 tu shape
                        )
                        .padding(horizontal = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "inteligente",
                        fontFamily = MyFont.workSansRegular,
                        fontSize = ResponsiveText.headline(),
                        lineHeight = ResponsiveLineHeight.headline(),
                        color = IQuiuboColorPallete.primaryText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ),
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.headline(),
        lineHeight = ResponsiveLineHeight.headline(),
        color = IQuiuboColorPallete.primaryText,
        fontWeight = FontWeight.Bold
    )
    
    Spacer(modifier = Modifier.height(spacing.md))

    Text(
        text = "Entiende tus resultados y cuida de tu salud paso a paso",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        color = IQuiuboColorPallete.secondaryText,
    )
}


@Composable private fun TextToSignInWithSomething() {

    Text(
        text = "Inicia sesión para comenzar:",
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        textAlign = TextAlign.Center,
        color = Color(0xFF0C3A47),
        fontFamily = MyFont.jakartaSansRegular
    )
}


@Composable private fun ButtonToSignInWithGoogle(
    loginVM: LoginVM
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleUiState by loginVM.googleUiState.collectAsState()
    val logged by loginVM.isLoggedWithGoogle
    val shape = RoundedCornerShape(20.dp)

    val borderColor = if (logged) Color(0xFFD5FFD4)
    else IQuiuboColorPallete.moradoPrimario

    val iconColor = if (logged) Color(0xFF089400)
    else IQuiuboColorPallete.moradoPrimario

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "press_scale"
    )


    GoogleErrorUI(loginVM)

    ButtonRippleColor(
        color = Color.Black
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .scale(scale),
            interactionSource = interactionSource,
            contentPadding = PaddingValues(spacing.sm),
            // enable color: Azul claro = 0xFFA6E4FF
            // enable color: Ultramarine = 0xFF008CE3
            colors = ButtonDefaults.buttonColors(
                // Color anterior: Color(0xFFFBF7F1)
                containerColor = IQuiuboColorPallete.buttonEnabledTwo,
                disabledContainerColor = Color(0xFFD5FFD4)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
            shape = shape,
            border = BorderStroke(1.dp, borderColor),
            // ¿Qué lógica tiene este enabled aquí?: Cuando no se ha loggeado
            // el botón funciona normalmente pero cuando se loggea el
            // botón se desactiva.
            enabled = !logged,
            onClick = {
                scope.launch {
                    loginVM.updateGoogleUiState(GoogleUiState.Idle)
                    loginVM.updateEmailUiState(EmailUiState.Idle)

                    val obj = getGoogleIdToken(context)
                    loginVM.onGoogleButton(obj)
                }
            }
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_google_v2),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(iconColor)
            )

            Spacer(modifier = Modifier.width(spacing.sm))

            // Si esta en estado Loading aparecerá un circular
            // progressor, cuando ya no esté cargando se mostrará
            // el texto por defecto.
            if (googleUiState is GoogleUiState.Loading) {
                CircularProgressIndicator(
                    color = Color(0xFF592C00),
                    modifier = Modifier.size(25.dp)
                )

            } else {
                Text(
                    text = if (logged) "¡Sesión iniciada!" else "Continuar con Google",
                    fontSize = ResponsiveText.button(),
                    lineHeight = ResponsiveLineHeight.button(),
                    fontWeight = if (logged) FontWeight.ExtraBold else FontWeight.Normal,
                    color = if (logged) Color(0xFF089400) else Color(0xFF0C3A47),
                    fontFamily = MyFont.workSansRegular,
                )
            }
        }
    }
}


@Composable private fun GoogleErrorUI(
    loginVM: LoginVM
) {

    val googleUiState by loginVM.googleUiState.collectAsState()

    when (val result = googleUiState) {
        is GoogleUiState.Error -> {
            Text(
                text = result.message,
                fontFamily = MyFont.jakartaSansRegular,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        else -> {}
    }
}


@Composable private fun ButtonToSignInWithEmail(
    loginVM: LoginVM
) {

    val spacing = LocalSpacing.current

    val emailUiState by loginVM.emailUiState.collectAsState()
    val isLogged by loginVM.isLoggedWithEmail

    val shape = RoundedCornerShape(20.dp)
    var showSheet by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "press_scale"
    )

    val iconColor = if (isLogged) Color(0xFF089400) else
        IQuiuboColorPallete.backgroundLight

    // EmailErrorUI(loginVM)

    ButtonRippleColor(
        color = Color.Black
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .scale(scale),
            interactionSource = interactionSource,
            contentPadding = PaddingValues(spacing.sm),
            colors = ButtonDefaults.buttonColors(
                containerColor = IQuiuboColorPallete.moradoPrimario,
                disabledContainerColor = Color(0xFFD5FFD4)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            // ¿Qué lógica tiene este enabled aquí?: Cuando no se ha loggeado
            // el botón funciona normalmente pero cuando se loggea el
            // botón se desactiva.
            enabled = !isLogged,
            shape = shape,
            onClick = {
                loginVM.updateGoogleUiState(GoogleUiState.Idle)
                loginVM.updateEmailUiState(EmailUiState.Idle)
                showSheet = true
            }
        ) {

            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(spacing.sm))

            // Si esta en estado Loading aparecerá un circular
            // progressor, cuando ya no esté cargando se mostrará
            // el texto por defecto.
            if (emailUiState is EmailUiState.Loading) {
                CircularProgressIndicator(
                    color = Color(0xFF592C00),
                    modifier = Modifier.size(25.dp)
                )

            } else {
                Text(
                    text = if (isLogged) "¡Sesión iniciada!" else "Continuar con Correo",
                    fontSize = ResponsiveText.button(),
                    lineHeight = ResponsiveLineHeight.button(),
                    fontWeight = if (isLogged) FontWeight.ExtraBold else FontWeight.Normal,
                    color = if (isLogged) Color(0xFF089400) else IQuiuboColorPallete.backgroundLight,
                    fontFamily = MyFont.workSansRegular,
                )
            }
        }
    }

    if (showSheet)
        CustomModalBottomEmailArea(
            loginVM = loginVM,
            onDismiss = {
                showSheet = false
                loginVM.clearEmailVars()
            }
        )
}


@Composable private fun EmailErrorUI(
    loginVM: LoginVM
) {

    val emailUiState by loginVM.emailUiState.collectAsState()

    when(val result = emailUiState) {
        is EmailUiState.Error -> {
            Text(
                text = result.message,
                fontFamily = MyFont.jakartaSansRegular,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        else -> {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun CustomModalBottomEmailArea(
    loginVM: LoginVM,
    onDismiss: () -> Unit
) {

    val spacing = LocalSpacing.current

    val isLogged by loginVM.isLoggedWithEmail
    val email by loginVM.emailText.collectAsState()
    val password by loginVM.passwordText.collectAsState()
    val emailUiState by loginVM.emailUiState.collectAsState()

    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(isLogged) {
        if (isLogged) {
            state.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topEnd = 27.dp, topStart = 27.dp),
        containerColor = Color(0xFFFBF7F1),
        scrimColor = Color.Black.copy(alpha = 0.6f),
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFD69A00)) }
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = spacing.lg)
        ) {
            Text(
                text = "Inicio de sesión",
                fontSize = ResponsiveText.title(),
                lineHeight = ResponsiveLineHeight.title(),
                fontFamily = MyFont.jakartaSansRegular,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0C3A47)
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(10.dp))

            LoginBasicTextfeild(
                value = email,
                onValueChange = { loginVM.updateEmailText(it) },
                label = "Correo electrónico",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.AttachEmail,
                keyboardType = KeyboardType.Email,
                isError = emailUiState is EmailUiState.Error,
                errorMessage = if (emailUiState is EmailUiState.Error) (emailUiState as EmailUiState.Error).message else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            LoginBasicTextfeild(
                value = password,
                onValueChange = { loginVM.updatePasswordText(it) },
                label = "Contraseña",
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Done,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            ButtonForModalArea(
                buttonText = "Continuar",
                onEmailAction = { loginVM.onEmailSignInButton() }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable private fun ButtonForModalArea(
    buttonText: String,
    onEmailAction: () -> Unit
) {

    val shape = RoundedCornerShape(20.dp)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animeScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "pressed_scale"
    )

    ButtonRippleColor(color = Color.Black) {
        Button(
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .scale(animeScale)
                .clip(shape),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0062A3)
            ),
            shape = shape,
            onClick = { onEmailAction() }
        ) {

            Text(
                text = buttonText,
                fontSize = ResponsiveText.button(),
                lineHeight = ResponsiveLineHeight.button(),
                fontFamily = MyFont.spaceGroteskRegular ,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


@Composable private fun IfYouDontHaveAccount(
    loginVM: LoginVM
) {

    var showModalButton by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = buildAnnotatedString {
                append("¿No tienes cuenta?\n")
                withStyle(
                    SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color(0xFF007CD9)
                    )
                ) { append("Registrate aquí") } },
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.workSansRegular,
            fontWeight = FontWeight.Bold,
            color = IQuiuboColorPallete.secondaryText,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    loginVM.updateGoogleUiState(GoogleUiState.Idle)
                    loginVM.updateEmailUiState(EmailUiState.Idle)
                    showModalButton = true
                },
            textAlign = TextAlign.Center
        )
    }

    if (showModalButton)
        ModalBottonForCreateUserWithEmail(
            loginVM = loginVM,
            onDismiss = {
                showModalButton = false
                loginVM.clearEmailVars()
            }
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottonForCreateUserWithEmail(
    loginVM: LoginVM,
    onDismiss: () -> Unit
) {

    val isLogged by loginVM.isLoggedWithEmail
    val emailUiState by loginVM.emailUiState.collectAsState()
    val email by loginVM.emailText.collectAsState()
    val password by loginVM.passwordText.collectAsState()

    val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect (isLogged) {
        if (isLogged) {
            state.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topEnd = 27.dp, topStart = 27.dp),
        containerColor = Color(0xFFFBF7F1),
        scrimColor = Color.Black.copy(alpha = 0.6f),
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFFD69A00)) }
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(start = 20.dp, end = 20.dp)
        ) {

            Text(
                text = "Registro de cuenta",
                fontSize = ResponsiveText.title(),
                lineHeight = ResponsiveLineHeight.title(),
                fontFamily = MyFont.jakartaSansRegular,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0C3A47)
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color.DarkGray.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(10.dp))

            LoginBasicTextfeild(
                value = email,
                onValueChange = { loginVM.updateEmailText(it) },
                label = "Ingresa tu correo electrónico",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.AttachEmail,
                keyboardType = KeyboardType.Email,
                isError = emailUiState is EmailUiState.Error,
                errorMessage = if (emailUiState is EmailUiState.Error) (emailUiState as EmailUiState.Error).message else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            LoginBasicTextfeild(
                value = password,
                onValueChange = { loginVM.updatePasswordText(it) },
                label = "Contraseña",
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Done,
                isPassword = true,
                isError = emailUiState is EmailUiState.PasswordError,
                errorMessage = if (emailUiState is EmailUiState.PasswordError) (emailUiState as EmailUiState.PasswordError).message else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            ButtonForModalArea(
                buttonText = "Registrarme",
                onEmailAction = { loginVM.onEmailSignUpButton() }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable private fun PolicyAndPrivArea(
    loginVM: LoginVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(20.dp)


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CheckBoxForPolicityAndPriv(loginVM)

        Spacer(modifier = Modifier.width(spacing.md))

        // "He leído y acepto la Política de Privacidad y el Tratamiento de mis datos"
        Text(
            text = buildAnnotatedString {
                append("Acepto la ")
                withStyle(
                    SpanStyle(
                        color = IQuiuboColorPallete.moradoPrimario,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append("política de tratamiento de datos")
                }
            },
            fontFamily = MyFont.workSansRegular,
            lineHeight = ResponsiveLineHeight.caption(),
            fontSize = ResponsiveText.caption(),
            color = Color(0xFF0C3A47),
            textAlign = TextAlign.Justify,
            modifier = Modifier.clickable { loginVM.setShowPolicity() }
        )
    }

}


@Composable private fun CheckBoxForPolicityAndPriv(
    vm: LoginVM
) {

    val spacing = LocalSpacing.current
    val toggleState by vm.acceptPolicy
    val shape = RoundedCornerShape(5.dp)

    val backgroundColor = if (toggleState != null) IQuiuboColorPallete.moradoPrimario
    else IQuiuboColorPallete.backgroundLight

    Box(
        modifier = Modifier
            .size(spacing.lg)
            .border(1.dp, color = IQuiuboColorPallete.moradoPrimario, shape)
            .background(backgroundColor, shape)
            .clip(shape)
            .clickable { vm.setCheckBox() },
        contentAlignment = Alignment.Center
    ) {

        toggleState?.let {

            Image(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                colorFilter = ColorFilter.tint(IQuiuboColorPallete.backgroundLight),
                modifier = Modifier.size(spacing.md)
            )
        }
    }
}


private data class PolicySection(
    val title: String, 
    val body: String
)

private val policySections = listOf(
    PolicySection(
        title = "1. Identificación del responsable del tratamiento",
        body = "Towers Corporation, en adelante 'Hikibo', actúa como Responsable del tratamiento de los datos personales recolectados a través de la aplicación móvil ( Hikibo )."
    ),
    PolicySection(
        title = "2. Definiciones",
        body = "1. Titular: Persona natural cuyos datos personales son objeto de tratamiento ( el usuario Hikibo ).\n\n2. Dato personal: Cualquier información vinculada o que pueda asociarse a una persona natural determinada o determinable.\n\n3. Dato sensible: Aquel que afecta la intimidad del Titular o cuyo uso indebido puede generar discriminación. Los datos de salud son datos sensibles.\n\n4. Responsable del tratamiento: Hikibo, quien decide sobre la base de datos y su tratamiento.\n\n5. Encargado del tratamiento: Persona o entidad que trata los datos por cuenta del responsable (ej.: Google LLC, a traves de los servicios de Firebase/Firestore).\n\n6. Tratamiento: Cualquier operación sobre datos personales: recolección, almacenamiento, uso, circulación o supresión."
    ),
    PolicySection(
        title = "3. Datos de identificación y contacto",
        body = "Hikibo recolecta y trata las siguiente categorías de datos:\n\na) Datos de identificación y contacto:\n* Nombre y apellidos\n* Correo electrónico\n* Credenciales de acceso (gestionadas mediante Firebase Authentication)\n\nb) Datos sensibles de salud:\n* Niveles de glucosa\n* Resultados o parámetros metabólicos ingresados por el usuario\n* Clasificaciones de riesgo y resultados generados por el sistema de detección temprana."
    ),
    PolicySection(
        title = "4. Finalidad del tratamiento",
        body = "Los datos recolectados serán tratados con las siguientes finalidades:\n\n1. Crear y administrar la cuenta del usuario y permitir el acceso a la aplicación.\n\n2. Procesar la información metabólica para ofrecer educación sobre condiciones metabólicas y herramientas de detección temprana.\n\n3. Generar clasificaciones de riesgo orientativas con fines educativos e informativos.\n\n4. Mejorar el funcionamiento, la seguridad y la experiencia de uso de la aplicación.\n\n5. Dar cumplimiento a obligaciones legales aplicables."
    ),
    PolicySection(
        title = "5. Tratamiento por terceros y transferencia internacional de datos",
        body = "Hikibo utiliza los servicios de Google Firebase / Cloud Firestore (Google LLC) como encargado del tratamiento para el almacenamiento y procesamiento de los datos.\n\nEsto implica que los datos personales pueden ser almacenados en servidores ubicados fuera del territorio colombiano, conforme a la infraestructura de Google Cloud. Al aceptar esta política, el Titular autoriza expresamente la transferencia y transmisión internacional de sus datos personales, incluidos los datos sensibles de salud, bajo los estándares de seguridad que el proveedor garantiza contractualmente."
    ),
    PolicySection(
        title = "6. Derechos del Titular",
        body = "Conforme al articulo 8 de la ley 1581 de 2012, el Titular tiene derecho a:\n\n1. Conocer, actualizar y rectificar sus datos personales.\n\n2. Solicitar prueba de la autorización otorgada.\n\n3. Ser informado sobre el uso que se ha dado a sus datos.\n\n4. Presentar quejas ante la Superintendencia de Industria y Comercio (SIC) por infracciones a la ley.\n\n5. Revocar la autorización y/o solicitar la supresión de sus datos, cuando proceda.\n\n6. Acceder de forma gratuita a sus datos personales."
    ),
    PolicySection(
        title = "7. Procedimiento para ejercer los derechos (consultas y reclamos)",
        body = "El titular puede ejercer sus derechos enviando una solicitud al correo: towerscorp62@gmail.com, indicando:\n\n* Nombre completo y datos de contacto.\n\n* Descripción clara de la solicitud (consulta, actualización, rectificación, supresión o revocatoria).\n\n Plazos legales:\n\n* Consultas: se atenderán en un máximo de diez (10) días hábiles, prorrogables por cinco (5) días hábiles más.\n\n* Reclamos: se atenderán en un máximo de quince (15) días hábiles, prorrogables por ocho (8) días hábiles más."
    ),
    PolicySection(
        title = "8. Medidas de seguridad",
        body =  "Hikibo adopta medidas técnicas y administrativas razonables para proteger los datos, entre ellas:\n\n1. Autenticación de usuarios mediante Firebase Authentication.\n\n2. Reglas de seguridad de Firestore que restringen el acceso a los datos de cada usuario.\n\n3. Cifrado en tránsito y en reposo provisto por la infraestructura de Google Cloud."
    ),
    PolicySection(
        title = "9. Consentimiento",
        body = "Al registrarse y marcar la casilla o el botón de aceptación, el Titular otorga su consentimiento libre, previo, expreso e informado para el tratamiento de sus datos personales, incluidos los datos sensibles de salud, en los términos de esa política."
    ),
    PolicySection(
        title = "10. Vigencia",
        body = "La presente Política rige a partir del 17 de Junio del 2026. Los datos personales serán conservados durante el tiempo necesario para cumplir las finalidades descritas y mientras la cuenta del usuario permanezca activa, salvo solicitud de suspensión o exigencia legal de conservación.\n\nHikibo se reserva el derecho de modificar esta Política; los cambios sustanciales serán informados a los Titulares a través de la aplicación."
    ),
    PolicySection(
        title = "Última actualización",
        body = "17 de Junio del 2026"
    )
)


@Composable private fun ShowPolicityBox(
    vm: LoginVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(20.dp)
    val policityState by vm.showPolicity

    val lazyState = rememberLazyListState()
    val scope = rememberCoroutineScope()

        
    if (policityState) {

        // Cuando el modal está abierto, el back lo cierra
        BackHandler { vm.hidePolicy() }

        // Captura todos los clicks
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                }
                .statusBarsPadding()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White, shape)
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.Start,
                state = lazyState
            ) {

                // Este item funcionará como título principal
                item {
                    Text(
                        text = "Política de tratamiento de datos personales - Hikibo",
                        fontSize = ResponsiveText.headline(),
                        lineHeight = ResponsiveLineHeight.headline(),
                        fontFamily = MyFont.spaceGroteskRegular,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
                
                items(policySections) {section ->
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(spacing.md))

                    Text(
                        text = section.title,
                        fontSize = ResponsiveText.title(),
                        lineHeight = ResponsiveLineHeight.title(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MyFont.jakartaSansRegular,
                        color = Color.Black
                    )
                    Text(
                        text = section.body,
                        color = Color.Black,
                        fontSize = ResponsiveText.body(),
                        lineHeight = ResponsiveLineHeight.body(),
                        fontFamily = MyFont.workSansRegular,
                        textAlign = TextAlign.Start
                    )
                }

                item {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IQuiuboColorPallete.moradoPrimario
                        ),
                        onClick = {
                            vm.setShowPolicity()
                            vm.setTrueInCheckBox()
                        }
                    ) {

                        Text(
                            text = "He leído y acepto",
                            color = Color.White,
                            fontFamily = MyFont.spaceGroteskRegular,
                            fontSize = ResponsiveText.button(),
                            lineHeight = ResponsiveLineHeight.button()
                        )
                    }
                }
            }

            /**
             * ¿El user ya llegó al fondo de la lista?
             *
             * Le pregunta a layoutInfo "¿qué ves ahora?" y compara: si el ÚLTIMO
             * ítem visible es el último de toda la lista (index == total - 1),
             * entonces tocamos fondo.
             *
             * El derivedStateOf es el héroe silencioso: el scroll cambia a cada
             * pixel, pero esto solo avisa cuando la respuesta pasa de false a true.
             * Un parpadeo, no mil.
             */
            val isAtBottom by remember {
                derivedStateOf { 
                    val layoutInfo = lazyState.layoutInfo
                    val visibleItems = layoutInfo.visibleItemsInfo

                    if (visibleItems.isEmpty()) {
                        false

                    } else {

                        val lastvisible = visibleItems.last()
                        lastvisible.index == layoutInfo.totalItemsCount - 1
                    }
                }
            }

            // Aquí el botón se mostrará mientras el usuario
            // no llegue hasta el fondo donde se encuentra el último (item).

            // Lo interpretaria tipo. ¿El usuario ya esta en el
            // fondo de la pantalla?.
            if (!isAtBottom) {
                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(x = (-Constants.WIDTH_DP_DEVICE() / 2) + 20.dp, y = (-40).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IQuiuboColorPallete.moradoPrimario
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(30.dp),
                    onClick = {
                        scope.launch {
                            lazyState.animateScrollToItem(policySections.size + 1)
                        }
                    }
                ) {

                    Image(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }
            }
        }
    }
}


@Composable private fun BoxSloganUpper() {

    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color(0xFF0C3A47),
                    fontSize = ResponsiveText.caption(),
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Luz de esperanza · 光 + 希望\n")
            }

            withStyle(
                SpanStyle(
                    color = Color.DarkGray,
                    fontSize = ResponsiveText.caption()
                )
            ) {
                append("Claridad para entender tu salud")
            }
        },
        lineHeight = ResponsiveLineHeight.caption(),
        fontFamily = MyFont.jakartaSansRegular,
        textAlign = TextAlign.Center
    )
}


@Composable private fun FirebaseSendCapError(
    loginVM: LoginVM
) {

    val firestoreUiState by loginVM.firestoreUiState.collectAsState()

    when (val state = firestoreUiState) {
        is FirestoreUiState.Error -> {
            Text(
                text = state.message,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.jakartaSansRegular,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFD64545)
            )
        }

        else -> {}
    }
}


@Composable private fun ButtonLastToContinue(
    loginVM: LoginVM
) {

    val spacing = LocalSpacing.current

    val firebaseUiState by loginVM.firestoreUiState.collectAsState()
    val readyToGo = loginVM.allConditionsAreTrue()
    val shape = RoundedCornerShape(spacing.xxl)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "pressed_scale"
    )

    val offsetX = remember { Animatable(0f) }

    LaunchedEffect (readyToGo) {
        if (readyToGo) {
            offsetX.animateTo(
                targetValue = 20f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        } else { offsetX.animateTo(0f, tween(300)) }
    }

    ButtonRippleColor(
        color = Color(0xFF442203)
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .blur(if (readyToGo) 0.dp else 1.dp)
                .scale(scale),
            interactionSource = interactionSource,
            contentPadding = PaddingValues(spacing.sm),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E22CE),
                disabledContainerColor = Color.White.copy(alpha = 0.2f)
            ),
            shape = shape,
            // Para cuando todas las condiciones sean cumplidas el
            // botón sea activará, de lo contrario permanecerá disabled.
            enabled = readyToGo,
            onClick = {
                loginVM.updateFirestoreUiState(FirestoreUiState.Idle)
                loginVM.onNavigateNext()
            }
        ) {

            Text(
                text = "¡Empieza tu camino!",
                fontSize = ResponsiveText.title(),
                lineHeight = ResponsiveLineHeight.title(),
                color = Color(0xFFF8F5FF),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = MyFont.jakartaSansRegular,
            )

            Spacer(modifier = Modifier.width(10.dp))

            if (readyToGo) {

                if (firebaseUiState !is FirestoreUiState.Loading) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .offset(x = offsetX.value.dp),
                        tint = Color(0xFFFBF7F1)
                    )

                } else {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}


@Composable private fun LoginBasicTextfeild(
    value: String,
    onValueChange: (String) -> Unit, 
    label: String, 
    modifier: Modifier, 
    isError: Boolean = false, 
    errorMessage: String? = null, 
    leadingIcon: ImageVector? = null, 
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false
) {

    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Animación del borde según foco/error
    val borderColor by animateColorAsState(
        targetValue = when {
            isError   -> Color(0xFFD64545)
            isFocused -> Color(0xFF0062A3)   // tu azul de marca
            else      -> Color.Gray.copy(alpha = 0.4f)
        },
        animationSpec = tween(250),
        label = "border_color"
    )

    val borderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        label = "border_width"
    )

    Column(
        modifier = modifier
    ) {

        Text(
            text = label, 
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.jakartaSansRegular,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(5.dp))

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
                        .border(borderWidth, borderColor, RoundedCornerShape(10.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    leadingIcon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = borderColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                    }

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        if (value.isEmpty()) {
                            Text(
                                text = "Escribe aquí…",
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
                                .clickable { passwordVisible = !passwordVisible }
                        )
                    }
                }
            }
        )

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