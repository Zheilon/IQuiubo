package com.zhei.ikiora.features.login.presentation
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhei.ikiora.R
import com.zhei.ikiora.core.common.DEVICE_BOTTOM_PADDING
import com.zhei.ikiora.core.common.LockScreenOrientation
import com.zhei.ikiora.core.common.MyFont
import com.zhei.ikiora.core.common.ResponsiveText
import com.zhei.ikiora.core.common .ResponsiveLineHeight
import com.zhei.ikiora.core.common.WIDTH_DP_DEVICE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable fun LoginUI(
    loginVM: LoginVM = hiltViewModel()
) {
    
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val blurCondition = loginVM.showPolicity.value
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blur(if (blurCondition) 12.dp else 0.dp)
            .background(color = Color(0xFF0062A3)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.back_login_v7),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, bottom = DEVICE_BOTTOM_PADDING()),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImagenCenter()

            BoxToJoinWithSomething()

            Spacer(modifier = Modifier.height(20.dp))
            
            PolicityAndPrivArea(loginVM)

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

    Image(
        painter = painterResource(id = R.drawable.only_figures_logo),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth(0.5f)   // adaptativo
            .widthIn(max = 220.dp) // tope para tablets
            .graphicsLayer {
                this.translationY = animeTranslation
                scaleX = scale
                scaleY = scale
            }
    )
}


@Composable private fun TextUpperLoginBox() {

    Text(
        text = "¡Bienvenid@ a",
        fontFamily = MyFont.spaceGroteskRegular,
        fontSize = ResponsiveText.displayLarge(),
        fontWeight = FontWeight.ExtraBold,
        lineHeight = ResponsiveLineHeight.displayLarge(),
        textAlign = TextAlign.Center,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
    )

    // Atención: Este box esta puesto con la intención de que, primero
    // por detras se vea la linea divisoria, y encima de esta la
    // palabra Ikiora.

    Box(
        modifier = Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color(0xFF006FFF))) {
                        append("Iki")
                    }

                    withStyle(SpanStyle(color = Color(0xFFFF4A4A))) {
                        append("ora!")
                    }
                },
                fontFamily = MyFont.spaceGroteskRegular,
                fontSize = ResponsiveText.displayLarge(),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(20.dp))
                    .background(color = Color.White, RoundedCornerShape(20.dp))
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
            )
        }
    }
}


@Composable private fun BoxToJoinWithSomething() {

    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = shape)
            .background(color = Color.White, shape = shape)
            .padding(15.dp)
    ) {

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextUpperLoginBox()

            Spacer(modifier = Modifier.height(10.dp))

            TextTitleInMiddle()

            Spacer(modifier = Modifier.height(10.dp))

            TextToSignInWithGoogle()

            Spacer(modifier = Modifier.height(20.dp))

            ButtonToSignInWithGoogle()

            //Spacer(modifier = Modifier.height(20.dp))

            //HorizontalDivider(thickness = 1.dp, color = Color.Black.copy(alpha = 0.3f))

            //SignInWithPhoneArea()
        }
    }
}


@Composable private fun TextTitleInMiddle() {

    Text(
        text = buildAnnotatedString {
            append("Tu camino hacia un control metabólico inteligente")
        },
        fontSize = ResponsiveText.title(),
        lineHeight = ResponsiveLineHeight.title(),
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = Color.Black,
        fontFamily = MyFont.spaceGroteskRegular
    )
}


@Composable private fun TextToSignInWithGoogle() {

    val shape = RoundedCornerShape(20.dp)

    Text(
        text = "Inicia sesión para comenzar:",
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        textAlign = TextAlign.Center,
        color = Color.Black,
        fontFamily = MyFont.workSansRegular,
        letterSpacing = 0.09.sp
    )
}


@Composable private fun ButtonToSignInWithGoogle() {

    val shape = RoundedCornerShape(25.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = Color.DarkGray, shape)
            .clip(shape)
            .clickable {}
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = null, 
            modifier = Modifier.size(30.dp)
        )
        
        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "Continuar con Google",
            fontSize = ResponsiveText.button(),
            lineHeight = ResponsiveLineHeight.button(),
            color = Color.Black,
            fontFamily = MyFont.spaceGroteskRegular,
            letterSpacing = 0.01.sp
        )
    }
}


@Composable private fun SignInWithPhoneArea() {}


@Composable private fun PolicityAndPrivArea(
    vm: LoginVM
) {

    val shape = RoundedCornerShape(20.dp)

    Column(
        modifier = Modifier
            .shadow(8.dp, shape)
            .background(color = Color.White, shape)
            .padding(20.dp)
    ) {
        // Azul más claro: 00B2FF
        // Azul más oscuro: 003C6B
        Text(
            text = "Leer política de privacidad",
            color = Color(0xFF003C6B),
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body(),
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { vm.setShowPolicity() }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // "He leído y acepto la Política de Privacidad y el Tratamiento de mis datos"
            Text(
                text = buildAnnotatedString {
                    append("He leído y ")

                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append("acepto ") }

                    append("la Política de Privacidad y el tratamiento de mis datos.")
                },
                fontFamily = MyFont.spaceGroteskRegular,
                lineHeight = ResponsiveLineHeight.caption(),
                fontSize = ResponsiveText.caption(),
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier.width((WIDTH_DP_DEVICE() + 50.dp) / 2)
            )

            Spacer(modifier = Modifier.weight(1f))

            CheckBoxForPolicityAndPriv(vm)
        }
    }
}


@Composable private fun CheckBoxForPolicityAndPriv(
    vm: LoginVM
) {

    val toggleState by vm.acceptPolicy
    val shape = RoundedCornerShape(5.dp)

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(35.dp)
                .border(1.dp, color = Color.Black, shape)
                .background(color = Color.White, shape)
                .clip(shape)
                .clickable { vm.setCheckBox() }
        ) {

            toggleState?.let {

                Image(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF0062A3)),
                    modifier = Modifier.fillMaxSize()
                )
            }
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
        body = "Towers Corporation, en adelante 'Ikiora', actúa como Responsable del tratamiento de los datos personales recolectados a través de la aplicación móvil ( Ikiora )."
    ),
    PolicySection(
        title = "2. Definiciones",
        body = "1. Titular: Persona natural cuyos datos personales son objeto de tratamiento ( el usuario Ikiora ).\n\n2. Dato personal: Cualquier información vinculada o que pueda asociarse a una persona natural determinada o determinable.\n\n3. Dato sensible: Aquel que afecta la intimidad del Titular o cuyo uso indebido puede generar discriminación. Los datos de salud son datos sensibles.\n\n4. Responsable del tratamiento: Ikiora, quien decide sobre la base de datos y su tratamiento.\n\n5. Encargado del tratamiento: Persona o entidad que trata los datos por cuenta del responsable (ej.: Google LLC, a traves de los servicios de Firebase/Firestore).\n\n6. Tratamiento: Cualquier operación sobre datos personales: recolección, almacenamiento, uso, circulación o supresión."
    ),
    PolicySection(
        title = "3. Datos de identificación y contacto",
        body = "Ikiora recolecta y trata las siguiente categorías de datos:\n\na) Datos de identificación y contacto:\n* Nombre y apellidos\n* Correo electrónico\n* Credenciales de acceso (gestionadas mediante Firebase Authentication)\n\nb) Datos sensibles de salud:\n* Niveles de glucosa\n* Resultados o parámetros metabólicos ingresados por el usuario\n* Clasificaciones de riesgo y resultados generados por el sistema de detección temprana."
    ),
    PolicySection(
        title = "4. Finalidad del tratamiento",
        body = "Los datos recolectactados serán tratados con las siguintes finalidades:\n\n1. Crear y administrar la cuenta del usuario y permitir el acceso a la aplicación.\n\n2. Procesar la información metabólica para ofrecer educación sobre condiciones metabólicas y herramientas de detección temprana.\n\n3. Generar clasificaciones de riesgo orientativas con fines educativos e informativos.\n\n4. Mejorar el funcionamiento, la seguridad y la experiencia de uso de la aplicación.\n\n5. Dar cumplimiento a obligaciones legales aplicables."
    ),
    PolicySection(
        title = "5. Tratamiento por terceros y transferencia internacional de datos",
        body = "Ikiora utiliza los servicios de Google Firebase / Cloud Firestore (Google LLC) como encargado del tratamiento para el almacenamiento y procesamiento de los datos.\n\nEsto implica que los datos personales pueden ser almacenados en servidores ubicados fuera del territorio colombiano, conforme a la infraestructura de Google Cloud. Al aceptar esta política, el Titular autoriza expresamente la transferencia y transmisión internacional de sus datos personales, incluidos los datos sensibles de salud, bajo los estándares de seguridad que el proveedor garantiza contractualmente."
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
        body =  "Ikiora adopta medidas técnicas y administrativas razonables para proteger los datos, entre ellas:\n\n1. Autenticación de usuarios mediante Firebase Authentication.\n\n2. Reglas de seguridad de Firestore que restringen el acceso a los datos de cada usuario.\n\n3. Cifrado en transito y en reposo provisto por la infraestructura de Google Cloud."
    ),
    PolicySection(
        title = "9. Consentimiento",
        body = "Al registrarse y marcar la casilla o el botón de aceptación, el Titular otorga su consentimiento libre, previo, expreso e informado para el tratamiento de sus datos personales, incluidos los datos sensibles de salud, en los términos de esa política."
    ),
    PolicySection(
        title = "10. Vigencia",
        body = "La presente Política rige a partir del 17 de Junio del 2026. Los datos personales serán conservados durante el tiempo necesario para cumplir las finalidades descritas y mientras la cuenta del usuario permanezca activa, salvo solicitud de suspensión o exigencia legal de conservación.\n\nIkiora se reserva el derecho de modificar esta Política; los cambios sustanciales serán informados a los Titulares a través de la aplicación."
    ),
    PolicySection(
        title = "Última actualización",
        body = "17 de Junio del 2026"
    )
)


@Composable private fun ShowPolicityBox(
    vm: LoginVM
) {

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
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFFFED82).copy(0.9f), shape)
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.Start,
                state = lazyState
            ) {

                // Este item funcionará como título principal
                item {
                    Text(
                        text = "Política de tratamiento de datos personales - Ikiora",
                        fontSize = ResponsiveText.headline(),
                        lineHeight = ResponsiveLineHeight.headline(),
                        fontFamily = MyFont.spaceGroteskRegular,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
                
                items(policySections) { section ->
                    Text(
                        text = section.title,
                        fontSize = ResponsiveText.title(),
                        lineHeight = ResponsiveLineHeight.title(),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MyFont.spaceGroteskRegular,
                        color = Color.Black
                    )
                    Text(
                        text = section.body,
                        color = Color.Black,
                        fontSize = ResponsiveText.body(),
                        lineHeight = ResponsiveLineHeight.body(),
                        fontFamily = MyFont.spaceGroteskRegular
                    )
                }

                item {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF945B00)
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

            Button(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = (-20).dp, y = (-20).dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF945B00)
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(10.dp),
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




