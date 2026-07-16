package com.zhei.hikibo.features.home.presentation
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.hikibo.R
import com.zhei.hikibo.core.common.Constants
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import com.zhei.hikibo.core.common.dashedBorder
import com.zhei.hikibo.core.domain.model.User
import kotlin.math.cos
import kotlin.math.sin


@Composable fun HomeUI(
    homeVM: HomeVM
) {

    val homeUiState by homeVM.homeUiState.collectAsStateWithLifecycle()

    when (val s = homeUiState) {
        is HomeUiState.Loading -> HomeLoader()
        is HomeUiState.Error -> Text(text = s.message)
        is HomeUiState.Success -> HomeContent(homeVM = homeVM, user = s.user)
        else -> {}
    }
}


@Composable private fun HomeLoader() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IQuiuboColorPallete.backgroundLight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { IQuiuboPulseLoader() }
}



@Composable private fun HomeContent(
    homeVM: HomeVM,
    user: User
) {

    val spacing = LocalSpacing.current

    user.toString().let { Log.e("HOME UI, USER", it) }
    //LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        FirstSection(homeVM, user)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(IQuiuboColorPallete.backgroundLight)
                .padding(horizontal = spacing.md),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item { Spacer(modifier = Modifier.height(spacing.md)) }

            item { SecondSection() }

            item { Spacer(modifier = Modifier.height(spacing.lg)) }

            item { ThirdSection() }

            item { Spacer(modifier = Modifier.height(spacing.lg)) }

            item { FourSection() }

            item { Spacer(modifier = Modifier.height(spacing.lg)) }

            item { FiveSection() }


            // Este spacer queda a lo último para generar un espacio entre
            // el LazyColumn y el NavigationBar.
            item { Spacer(modifier = Modifier.height(spacing.md)) }
        }
    }
}



@Composable private fun FirstSection(
    homeVM: HomeVM,
    user: User
) {

    val spacing = LocalSpacing.current

    // Row() principal, este contendrá en la izquierda
    // el saludo y el nombre, y en la derecha el icono
    // para entrar a las configuraciones del perfil.

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(IQuiuboColorPallete.moradoPrimario)
            .padding(spacing.lg)
            .statusBarsPadding(),
        verticalAlignment = Alignment.Bottom
    ) {

        TextFirstSection(homeVM, user)

        Spacer(modifier = Modifier.weight(1f))

        IconForProfileFirstSection(user)
    }
}



@Composable private fun TextFirstSection(
    homeVM: HomeVM,
    user: User
) {

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Hola, ${homeVM.getGreeting()}",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.workSansRegular,
            color = Color.White
        )

        Text(
            text = user.firstName,
            fontSize = ResponsiveText.headline(),
            lineHeight = ResponsiveLineHeight.headline(),
            fontFamily = MyFont.workSansRegular,
            color = IQuiuboColorPallete.backgroundLight,
            fontWeight = FontWeight.ExtraBold
        )
    }
}



@Composable private fun IconForProfileFirstSection(
    user: User
) {

    Button(
        modifier = Modifier.size(50.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = IQuiuboColorPallete.LOW_PINK
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
        shape = CircleShape,
        onClick = { }
    ) {

        Text(
            text = "${user.firstName[0]}${user.firstLastName[0]}",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body(),
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
    }
}



@Composable private fun SecondSection() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(15.dp)

    // Este será un Row el cual all cuerpo
    // será clicable, en la izquirda va a haber un icono como de un documento,
    // en la mitad va a ir un texto indicatívo, y por último en la derecha
    // una flecha apuntando a la izquierda.

    // Voy a dividir en espacio en 3 tamaños.

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        IQuiuboColorPallete.moradoPrimario,
                        IQuiuboColorPallete .moradoPrimario.copy(red = 0.8f)
                    )
                ),
                shape = shape
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
            .padding(vertical = spacing.md, horizontal = spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        RightIconSecondSection()

        MiddleTextSecondSection(Modifier.weight(1f))

        LeftIconSecondSection()
    }
}



@Composable private fun RightIconSecondSection() {

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(IQuiuboColorPallete.accentMiddle, RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Book,
            contentDescription = null,
            tint = IQuiuboColorPallete.primaryText
        )
    }
}


@Composable private fun MiddleTextSecondSection(
    modifier: Modifier
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = "Interpreta tus resultados",
            fontFamily = MyFont.jakartaSansRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Sube tus examenes y entiendelos al instante",
            fontFamily = MyFont.jakartaSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = IQuiuboColorPallete.backgroundLight
        )
    }
}



@Composable private fun LeftIconSecondSection() {

    val spacing = LocalSpacing.current

    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier.size(spacing.xl)
    )
}



@Composable private fun ThirdSection() {

    val spacing = LocalSpacing.current

    // En esta sección se compondra de un column principal,
    // posterior a este Column irá un texto que muestre: "Tu última interpretación"
    // Despues irá del texto irá otro Column que va a envolveer la
    // logica que describe, la interpretación.

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        TextTitleThirdSection()

        Spacer(modifier = Modifier.height(spacing.md))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(15.dp))
                .padding(spacing.md)
        ) {

            RowRiskAndDaysThirdSection()

            Spacer(modifier = Modifier.height(spacing.sm))

            ResumeTextThirdSection()

            Spacer(modifier = Modifier.height(spacing.sm))

            SeeTotalInterpretThirdSection()
        }
    }

}



@Composable private fun TextTitleThirdSection() {

    val spacing = LocalSpacing.current

    // Un hijo no conoce automáticamente la altura de sus hermanos.
    // Entonces el Box no puede decir "quiero medir lo mismo que el Text"...
    // a menos que el Row primero mida a todos sus hijos.
    //
    // Y para eso existe un modificador perfecto:
    //
    // Modifier.(IntrinsicSize.Min)
    //
    // ¿Qué hace IntrinsicSize.Min?
    // ----------------------------
    // Le dice al Row, "Calcula cuál deberia ser
    // la altura mínima según tus hijos".

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(spacing.sm)
                .fillMaxHeight()
                .background(Color(0xFFE5CDFF), RoundedCornerShape(spacing.sm))
        ) {}

        Spacer(modifier = Modifier.width(spacing.sm))

        Text(
            text = "Tu última interpretación",
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = IQuiuboColorPallete.primaryText,
        )
    }
}



@Composable private fun RowRiskAndDaysThirdSection() {

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        PillRisk()

        Spacer(modifier = Modifier.weight(1f))

        DaysPast()
    }
}



@Composable private fun PillRisk() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Red.copy(0.1f), CircleShape)
            .padding(vertical = spacing.xs)
            .padding(start = 12.dp, end = spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Este es el puntico que cambia de color respecto al riesgo

        Box(
            modifier = Modifier
                .size(spacing.sm)
                .background(Color.Red, CircleShape)
        )

        Spacer(modifier = Modifier.width(spacing.sm))

        // Igual el texto va a cambiar respecto al riesgo.

        Text(
            text = "Riesgo Alto",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.jakartaSansRegular,
            fontWeight = FontWeight.Bold,
            color = Color.Red.copy(red = 0.8f)
        )
    }
}



@Composable private fun DaysPast() {

    Text(
        text = "hace 3 días",
        fontFamily = MyFont.jakartaSansRegular,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.secondaryText,
    )
}



@Composable private fun ResumeTextThirdSection() {

    Text(
        text = "Tu glucosa en ayunas esta muy elevada. Conviene consultar con un medico para iniciar tratamiento",
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.secondaryText,
        fontFamily = MyFont.spaceGroteskRegular
    )
}



@Composable private fun SeeTotalInterpretThirdSection() {

    val spacing = LocalSpacing.current

    // Esta parte se vá a diseñar con un Row, donde este en la
    // parte izquierda tendrá el texto y en la parte derecha un icono.

    Row (
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Leer interpretación completa",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = IQuiuboColorPallete.moradoPrimario,
            fontWeight = FontWeight.Bold,
            fontFamily = MyFont.jakartaSansRegular
        )

        Spacer(modifier = Modifier.width(spacing.sm))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = IQuiuboColorPallete.moradoPrimario
        )
    }
}



@Composable private fun FourSection() {

    val spacing = LocalSpacing.current

    // Para esta parte se realizará un column principal, luego
    // detro de ese column van a ir dentro un lazyRow el
    // cual contenga los cards con los ultimos examenes.

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        TitleFourSection()

        Spacer(modifier = Modifier.height(spacing.md))

        LastExamsItemsForFourSection()
    }
}



@Composable private fun TitleFourSection() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(spacing.sm)
                .fillMaxHeight()
                .background(Color(0xFFE5CDFF), RoundedCornerShape(spacing.sm))
        ) {}
        
        Spacer(modifier = Modifier.width(spacing.sm))

        Text(
            text = "Últimos exámenes",
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = IQuiuboColorPallete.primaryText,
        )
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable private fun LastExamsItemsForFourSection() {

    val spacing = LocalSpacing.current

    FlowRow (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
    ) {

        // Aquí en esta parte voy utilizar un foreach para mostrar
        // los examenes.

        ExamItemIntoFourSection(
            examName = "Glucosa en ayunas",
            value = 101,
            unit = "mg/dL"
        )



        ExamItemIntoFourSection(
            examName = "Hemoglobina glicocilada",
            value = 6.1 ,
            unit = "%"
        )


        ExamItemIntoFourSection(
            examName = "Colesterol LDL",
            value = 159,
            unit = "mg/dL"
        )


        ExamItemIntoFourSection(
            examName = "Triglicéridos",
            value = 324,
            unit = "mg/dL"
        )
    }
}



@Composable private fun ExamItemIntoFourSection(
    examName: String,
    value: Any,
    unit: String,
) {

    val spacing = LocalSpacing.current
    val sizeCard = (Constants.WIDTH_DP_DEVICE() - (spacing.md * 2)) / 2

    Column (
        modifier = Modifier
            // Esta es una función extend que yo diseñe!
            .dashedBorder(1.dp, IQuiuboColorPallete.LOW_PINK)
            //.background(Color.White, RoundedCornerShape(15.dp))
            .padding(horizontal = spacing.md, vertical = spacing.sm),
    ) {

        IconMoreTextInItem(examName = examName)

        ShowExamValueFourSection(
            value = value,
            unit = unit
        )
    }
}



@Composable private fun IconMoreTextInItem(
    examName: String
) {

    val spacing = LocalSpacing.current
    
    Row (
        verticalAlignment = Alignment.Top
    ) {

        Text(
            text = examName,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = IQuiuboColorPallete.secondaryText,
            fontFamily = MyFont.jakartaSansRegular
        )
    }
}


@Composable private fun ShowExamValueFourSection(
    value: Any,
    unit: String
) {

    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = ResponsiveText.title(),
                    fontFamily = MyFont.spaceGroteskRegular,
                    color = IQuiuboColorPallete.primaryText,
                    fontWeight = FontWeight.Bold
                )
            ) { append("$value ") }

            withStyle(
                SpanStyle(
                    fontSize = ResponsiveText.caption(),
                    fontFamily = MyFont.jakartaSansRegular,
                    color = IQuiuboColorPallete.secondaryText,
                )
            ) { append(unit) }
        }
    )
}



@Composable private fun FiveSection() {

    val spacing = LocalSpacing.current

    // Se utilizará un Column para almacenar primero, un Row(),
    // este Row, contendrá un texto a la izquierda y otro texto clickable
    // a la izquierda, debajo de este irá un lazyColumn el cual unicamente.
    // contenga 3 elementos.

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            LeftTextFiveSection()

            Spacer(modifier = Modifier.weight(1f))

            RightTextClickableFiveSection()
        }

        Spacer(modifier = Modifier.height(spacing.md))


    }
}



@Composable
fun LeftTextFiveSection() {

    val spacing = LocalSpacing.current

    // Un hijo no conoce automáticamente la altura de sus hermanos.
    // Entonces el Box no puede decir "quiero medir lo mismo que el Text"...
    // a menos que el Row primero mida a todos sus hijos.
    //
    // Y para eso existe un modificador perfecto:
    //
    // Modifier.(IntrinsicSize.Min)
    //
    // ¿Qué hace IntrinsicSize.Min?
    // ----------------------------
    // Le dice al Row, "Calcula cuál deberia ser
    // la altura mínima según tus hijos".

    Row (
        modifier = Modifier
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(spacing.sm)
                .fillMaxHeight()
                .background(IQuiuboColorPallete.LOW_PINK, RoundedCornerShape(spacing.sm))
        ) {}

        Spacer(modifier = Modifier.width(spacing.sm))

        Text(
            text = "Tus interpretaciones",
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = IQuiuboColorPallete.primaryText
        )
    }
}



@Composable
fun RightTextClickableFiveSection() {

    val spacing = LocalSpacing.current

    Text(
        text = "Ver todas",
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.moradoPrimario,
        fontFamily = MyFont.jakartaSansRegular,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = (spacing.md + spacing.sm))
            .clickable { }
    )
}





@Composable private fun BackgroundBlur(
    block: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        IQuiuboColorPallete.backgroundLight,
                        Color(0xFFE7EAFF)
                    )
                )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp)
        ) {}

        block()
    }
}



@Composable
fun IQuiuboPulseLoader(
    modifier: Modifier = Modifier,
    diameter: Dp = 56.dp,
    ringColor: Color = Color(0xFF9333EA),   // morado primario
    accentColor: Color = Color(0xFFFFD93D), // amarillo acento
    trackColor: Color = Color(0xFFE9D5FF)   // morado claro
) {
    val transition = rememberInfiniteTransition(label = "pulse_loader")
    val cycle = 1400  // duración de un latido completo (ms)

    // 1) Giro continuo del arco
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(cycle, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // 2) EL LATIDO: doble golpe, como un corazón (tum-tum ... pausa)
    val beat by transition.animateFloat(
        initialValue = 0.90f,
        targetValue = 0.90f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = cycle
                0.90f at 0
                1.00f at 150 using FastOutSlowInEasing   // primer golpe
                0.94f at 300
                1.00f at 430 using FastOutSlowInEasing   // segundo golpe
                0.90f at 750                             // relajación + pausa
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "beat"
    )

    // 3) Onda que se expande y se desvanece (el "eco" del pulso)
    val wave by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(cycle, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    Canvas(modifier = modifier.size(diameter)) {
        val stroke = size.minDimension * 0.085f
        val baseRadius = (size.minDimension - stroke) / 2f * 0.78f
        val center = Offset(size.width / 2f, size.height / 2f)
        val r = baseRadius * beat            // radio que late

        // --- Onda expansiva (se va abriendo y desapareciendo)
        drawCircle(
            color = ringColor.copy(alpha = (1f - wave) * 0.22f),
            radius = baseRadius + baseRadius * 0.55f * wave,
            center = center,
            style = Stroke(width = stroke * 0.55f)
        )

        // --- Anillo base (la "pista" tenue)
        drawCircle(
            color = trackColor,
            radius = r,
            center = center,
            style = Stroke(width = stroke)
        )

        // --- Arco morado que gira y late
        val sweep = 100f
        rotate(degrees = rotation, pivot = center) {
            drawArc(
                color = ringColor,
                startAngle = 0f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(center.x - r, center.y - r),
                size = Size(r * 2, r * 2),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }

        // --- Punto amarillo en la punta del arco (el acento 10%)
        val tipAngle = Math.toRadians((rotation + sweep).toDouble())
        drawCircle(
            color = accentColor,
            radius = stroke * 0.62f,
            center = Offset(
                x = center.x + r * cos(tipAngle).toFloat(),
                y = center.y + r * sin(tipAngle).toFloat()
            )
        )
    }
}