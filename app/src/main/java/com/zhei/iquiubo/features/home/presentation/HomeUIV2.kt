package com.zhei.iquiubo.features.home.presentation
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.zhei.iquiubo.R
import com.zhei.iquiubo.core.common.ButtonRippleColor
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.bottomLine
import com.zhei.iquiubo.core.common.dashedBorder
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.features.personal_info.presentation.PersonalInfoVM
import com.zhei.iquiubo.ui.theme.ElectricBluePallete
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.sin


/**
 * Notas:
 *
 * No utilizar rememberInfiniteTransition de manera directa sin primero
 * almacenarla primero en una variable. Y demás funcionalidades que utilizen
 * este mismo principio.
 * */

@Composable fun HomeUIV2(
    heightNavBar: Dp,
    homeVM: HomeVM,
    onNavigate: (Scenes) -> Unit
) {

    val spacing = LocalSpacing.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            homeVM.navEvents.collect {
                onNavigate(it)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ElectricBluePallete.BACKGROUND),
        // ese (+ spacing.md) significa, que le estoy agregando el
        // espacio del padding generado por el column que se encuentra
        // dentro de uno de los item {}, entonces añado tamaño del navbar
        // + padding del column hijo.
        contentPadding = PaddingValues(bottom = heightNavBar + spacing.md)
    ) {

        // En la parte incial se coloca el header el nombre
        // y el avatar con el nombre de la app.
        item { HeaderNameApplication() }

        item {

            Column(
                modifier = Modifier.padding(spacing.md),
                verticalArrangement = Arrangement.spacedBy(spacing.xl)
            ) {

                Greeting()

                CardKnowYourPriorityAccess(homeVM)

                CardInterpretYourResume(homeVM)

                ResumeLastInterpretation()
            }
        }
    }

}



/**
 * ¿Como se diseña la Jerarquia?
 * 1. Un Row() es el layout principal.
 * 2. Despues viene el Logo en la parte izquierda.
 * 3. Despues del logo un un (Column) el nombre de la app y abajo (inicio).
 * 4. A lo último el avatar.
 * */
@Composable private fun HeaderNameApplication() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .statusBarsPadding()
            .padding(spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // IconHeartHeader()

        // Spacer(modifier = Modifier.width(spacing.md))

        TextNameApp()

        AvatarHeaderName()
    }
}


@Composable private fun IconHeartHeader() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)
    val backgroundColor = IQuiuboColorPallete.moradoPrimario.copy(alpha = 0.8f)

    Image(
        painter = painterResource(id = R.drawable.heart_plus),
        contentDescription = "heart_plus",
        colorFilter = ColorFilter.tint(backgroundColor),
        modifier = Modifier
            .size(spacing.xl)
            .border(1.dp, IQuiuboColorPallete.moradoPrimario.copy(alpha = 0.1f), shape)
            .background(IQuiuboColorPallete.backgroundLight, shape)
            .padding(spacing.sm)
    )
}



@Composable private fun TextNameApp() {

    val spacing = LocalSpacing.current

    Row(
        verticalAlignment = Alignment.Bottom
    ) {

        Text(
            text = "I",
            color = ElectricBluePallete.BLUE_2,
            fontWeight = FontWeight.Bold,
            fontFamily = MyFont.jakartaSansExtrabold,
            fontSize = ResponsiveText.displayBig(),
            lineHeight = ResponsiveLineHeight.displayBig()
        )

        Image(
            painter = painterResource(R.drawable.iquiubo_log_v3_no_back),
            contentDescription = "word",
            modifier = Modifier.size(spacing.xxl)
        )

        Text(
            text = "uiubo",
            color = ElectricBluePallete.BLUE_2,
            fontWeight = FontWeight.Bold,
            fontFamily = MyFont.jakartaSansExtrabold,
            fontSize = ResponsiveText.displayBig(),
            lineHeight = ResponsiveLineHeight.displayBig()
        )
    }

}



@Composable private fun AvatarHeaderName() {

    val spacing = LocalSpacing.current

    Icon(
        imageVector = Icons.Outlined.Person,
        contentDescription = "person",
        tint = Color.White,
        modifier = Modifier
            .size(spacing.xxl)
            .clip(CircleShape)
            .background(ElectricBluePallete.BLUE_2, RoundedCornerShape(spacing.md))
            .padding(spacing.sm)
    )
}



@Composable private fun Greeting() {
    Column {
        NameGreetingAndIcon()
        TextSubtitle()
    }
}



@Composable private fun NameGreetingAndIcon() {

    val spacing = LocalSpacing.current
    val color = colorForDot()

    Row (
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(spacing.md)
                .clip(CircleShape)
                .background(color = color),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(spacing.sm)
                    .clip(CircleShape)
                    .background(color = IQuiuboColorPallete.backgroundLight)
            )
        }

        Text(
            text = "Hola, Juan",
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.headline(),
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
    }
}



/**
 * No utilizar rememberInfiniteTransition de manera directa sin primero
 * almacenarla primero en una variable
 * */
@Composable private fun colorForDot(): Color {
    val animation = rememberInfiniteTransition("dot_color")
    val color by animation.animateColor(
        initialValue = Color(0xFF00867B),
        targetValue = Color(0xFF89F5E7),
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Reverse,
            animation = tween(1500  )
        ),
        label = "dot_color_anim"
    )
    return color
}




@Composable private fun TextSubtitle() {
    Text(
        text = "Monitoreando tu salud y biomarcadores clínicos",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        color = Color.Black
    )
}



@Composable private fun CardKnowYourPriorityAccess(
    homeVM: HomeVM
) {

    val spacing = LocalSpacing.current
    val mainCardShape = RoundedCornerShape(spacing.xl)

    Card(
        modifier = Modifier
            .shadow(5.dp, mainCardShape)
            .fillMaxWidth(),
        shape = mainCardShape
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(mainCardShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            ElectricBluePallete.BLUE_1,
                            ElectricBluePallete.BLUE_4
                        )
                    )
                )
        ) {

            // Fondo decorativo
            WaveBackground(
                modifier = Modifier.matchParentSize()
            )

            // Contenido real
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.xl),
                verticalArrangement = Arrangement.spacedBy(spacing.sm),
                horizontalAlignment = Alignment.Start
            ) {

                IconAndTextLeftKnowPriority()

                TitleKnowYourPriority()

                TextKnowYourPriority()

                ButtonKnowPriority(homeVM)
            }
        }
    }
}



@Composable private fun IconAndTextLeftKnowPriority() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .border(1.dp, IQuiuboColorPallete.backgroundLight.copy(alpha = 0.4f), CircleShape)
            .background(ElectricBluePallete.BLUE_5.copy(0.5f), CircleShape)
            .padding(horizontal = spacing.md, vertical = spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "star",
            tint = Color.Yellow,
            modifier = Modifier.size(spacing.md)
        )

        Text(
            text = "TRIAGE INTELIGENTE",
            lineHeight = ResponsiveLineHeight.small(),
            fontSize = ResponsiveText.small(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontFamily = MyFont.workSansRegular,
            letterSpacing = 2.sp
        )
    }
}



@Composable private fun WaveBackground(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
    ) {

        val strokeWidth = 1.5.dp.toPx()

        // Círculo 1
        drawCircle(
            color = Color.White.copy(alpha = 0.12f),
            radius = 100.dp.toPx(),
            center = Offset(
                x = size.width * 1.02f,
                y = size.height * 0.02f
            ),
            style = Stroke(width = strokeWidth)
        )

        // Círculo 2
        drawCircle(
            color = Color.White.copy(alpha = 0.12f),
            radius = 200.dp.toPx(),
            center = Offset(
                x = size.width * 1.02f,
                y = size.height * 0.02f
            ),
            style = Stroke(width = strokeWidth)
        )

        // Círculo 3
        drawCircle(
            color = Color.White.copy(alpha = 0.12f),
            radius = 300.dp.toPx(),
            center = Offset(
                x = size.width * 1.02f,
                y = size.height * 0.02f
            ),
            style = Stroke(width = strokeWidth)
        )

        // Círculo 4
        drawCircle(
            color = Color.White.copy(alpha = 0.12f),
            radius = 350.dp.toPx(),
            center = Offset(
                x = size.width * 1.02f,
                y = size.height * 0.02f
            ),
            style = Stroke(width = strokeWidth)
        )
    }
}



@Composable private fun TitleKnowYourPriority() {

    Text(
        text = "Conoce tu prioridad",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.title(),
        lineHeight = ResponsiveLineHeight.title(),
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}



@Composable private fun TextKnowYourPriority() {

    Text(
        text = "Conoce tu nivel de prioridad para acceder al examen de Hemoglobina glicosilada (HbA1c), mediante un análisis inteligente de tus datos de salud.",
        color = Color.White,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        textAlign = TextAlign.Justify,
        fontFamily = MyFont.workSansRegular
    )
}



@Composable private fun ButtonKnowPriority(
    homeVM: HomeVM
) {


    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {

        ButtonRippleColor(
            color = Color.Black
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                contentPadding = PaddingValues(start = spacing.md, end = spacing.md),
                shape = RoundedCornerShape(spacing.sm),
                onClick = { homeVM.navigateTo(Scenes.GetValuesForPriorityScene) }
            ) {

                Text(
                    text = "Conocer",
                    color = ElectricBluePallete.BLUE_1,
                    fontSize = ResponsiveText.button(),
                    fontFamily = MyFont.workSansRegular,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(spacing.sm))

                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "arrow_forward",
                    tint = ElectricBluePallete.BLUE_1
                )
            }
        }
    }
}



@Composable private fun CardInterpretYourResume(
    homeVM: HomeVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.shadow(5.dp, shape = shape, spotColor = Color.Black)
            .background(Color.Transparent, shape),
        contentAlignment = Alignment.TopEnd
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    ElectricBluePallete.BLUE_2,
                    RoundedCornerShape(bottomStart = 300.dp, topEnd = spacing.md)
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    ElectricBluePallete.BLUE_4,
                    RoundedCornerShape(bottomStart = 100.dp, topEnd = spacing.md)
                )
        )

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(bottomStart = spacing.xxl, topEnd = spacing.md)
                )
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.xl),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {

            HeaderRowIconAndText()

            TitleInterpretResult()

            DescriptionInterpretResult()

            ButtonDoInterpret(homeVM)
        }
    }
}


@Composable private fun HeaderRowIconAndText() {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /*IconHeaderCard()*/

        Spacer(modifier = Modifier.weight(1f))

        TextAndIcon()
    }
}


@Composable private fun IconHeaderCard() {
    val spacing = LocalSpacing.current

    Image(
        painter = painterResource(id = R.drawable.document_scanner_icon),
        contentDescription = "person",
        colorFilter = ColorFilter.tint(Color.White),
        modifier = Modifier
            .size(spacing.xl)
    )
}


@Composable private fun TextAndIcon() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = CircleShape, spotColor = Color.Black)
            .background(ElectricBluePallete.BLUE_1, CircleShape)
            .padding(horizontal = spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Image(
            painter = painterResource(id = R.drawable.flare),
            contentDescription = "person",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = "IA MÉDICA",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


@Composable private fun TitleInterpretResult() {
    Text(
        text = "Interpreta tus resultados",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.body(),
        lineHeight = ResponsiveLineHeight.body(),
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}


@Composable
fun DescriptionInterpretResult() {
    Text(
        text = "Sube tu análisis de sangre o informe de laboratorio para una interpretación instantanea con nuestros modelos de IA de IQuiubo.",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        color = Color.Black,
        textAlign = TextAlign.Justify,
    )
}


@Composable private fun ButtonDoInterpret(
    homeVM: HomeVM
) {

    val spacing = LocalSpacing.current

    ButtonRippleColor(
        color = Color.Black
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = ElectricBluePallete.BLUE_5.copy(alpha = 0.1f)
            ),
            contentPadding = PaddingValues(start = spacing.md, end = spacing.md),
            shape = RoundedCornerShape(spacing.sm),
            onClick = { homeVM.navigateTo(Scenes.InterpretationScenes.Entry) }
        ) {

            Text(
                text = "Interpretar",
                color = Color.Black,
                fontSize = ResponsiveText.button(),
                fontFamily = MyFont.workSansRegular,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(spacing.sm))

            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = "arrow_forward",
                tint = Color.Black
            )
        }
    }
}


@Composable private fun ResumeLastInterpretation() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.xl)

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        RowTitleOutAndDays()

        Column (
            modifier = Modifier
                .fillMaxWidth()
                //.shadow(5.dp, spotColor = Color.Black, shape = shape)
                .background(Color.White, shape)
                .padding(spacing.xl),
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {

            TextTitleResumeInterpret()

            ShowRiskCard()

            ResumeCardText()

            SeeCompleteResumeSection()
        }
    }
}


@Composable private fun RowTitleOutAndDays() {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FirstTextTitle()
        
        Spacer(modifier = Modifier.weight(1f))

        SecondDaysAgeLast()
    }
}


@Composable private fun FirstTextTitle() {
    Text(
        text = "ACTIVA",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        color = Color.Black,
        fontWeight = FontWeight.ExtraBold
    )
}


@Composable private fun SecondDaysAgeLast() {

    val spacing = LocalSpacing.current

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
        Icon(
            imageVector = Icons.Default.AccessTime,
            contentDescription = "watch",
            tint = Color.Black,
            modifier = Modifier.size(spacing.md)
        )

        Text(
            text = "Hace 3 días",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            color = Color.Black
        )
    }
}


@Composable
private fun TextTitleResumeInterpret() {

    val titleSize = ResponsiveText.body()
    val descriptionSize = ResponsiveText.small()

    Text(
        text = buildAnnotatedString {

            withStyle(
                ParagraphStyle(
                    lineHeight = ResponsiveLineHeight.body()
                )
            ) {
                withStyle(
                    SpanStyle(
                        fontFamily = MyFont.workSansRegular,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = titleSize
                    )
                ) {
                    append("Tu última interpretación")
                }
            }

            withStyle(
                ParagraphStyle(
                    lineHeight = ResponsiveLineHeight.small()
                )
            ) {
                withStyle(
                    SpanStyle(
                        fontFamily = MyFont.workSansRegular,
                        color = Color.Black,
                        fontSize = descriptionSize
                    )
                ) {
                    append("Panel de resumen de tu última interpretación")
                }
            }
        },
        textAlign = TextAlign.Start
    )
}


@Composable private fun ShowRiskCard() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .wrapContentWidth()
            .background(Color.Red.copy(alpha = 0.1f), CircleShape)
            .padding(horizontal = spacing.md, vertical = spacing.xs),
        horizontalArrangement = Arrangement.spacedBy(spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(spacing.sm)
                .background(Color.Red, CircleShape)
        )

        Text(
            text = "Riesgo Alto",
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            fontFamily = MyFont.workSansRegular,
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable private fun ResumeCardText() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = shape
            ),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        RowTitleResume()

        TextForDescriptionResume()
    }
}


@Composable private fun RowTitleResume() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircleOutline,
            contentDescription = "check",
            tint = ElectricBluePallete.BLUE_3
        )

        Text(
            text = "Resumen",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body(),
            color = Color.Black,
            fontWeight = FontWeight.W500
        )
    }
}


@Composable private fun TextForDescriptionResume() {
    Text(
        "Tus principales biomarcadores se encuentran dentro de los rangos esperados. Se identificó una ligera elevación en Ferritina y Glucosa en Ayuno. Se recomienda continuar con el seguimiento periódico.",
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = Color.Black,
        textAlign = TextAlign.Justify,
    )
}


@Composable private fun SeeCompleteResumeSection() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Leer interpretación completa",
            fontSize = ResponsiveText.caption(),
            fontFamily = MyFont.workSansRegular,
            fontWeight = FontWeight.Bold,
            lineHeight = ResponsiveLineHeight.caption(),
            color = ElectricBluePallete.BLUE_2,
            modifier = Modifier.weight(1f),
            textDecoration = TextDecoration.Underline
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "arrow",
            tint = ElectricBluePallete.BLUE_3
        )
    }
}