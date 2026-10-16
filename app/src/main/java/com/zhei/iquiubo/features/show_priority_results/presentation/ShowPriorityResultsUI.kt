package com.zhei.iquiubo.features.show_priority_results.presentation
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.ifIsIntToInt
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.features.put_rna_values.domain.model.PriorityInputModel
import com.zhei.iquiubo.ui.theme.ElectricBluePallete
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds


@Composable fun ShowPriorityResultsUI(
    showPriorityResultVM: ShowPriorityResultVM = hiltViewModel()
) {

    val uiState by showPriorityResultVM.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { showPriorityResultVM.lazyInit() }

    MainContainer(showPriorityResultVM)
}



@Composable private fun LoadingScreen() {

    var count by remember { mutableStateOf(false) }

    if (!count) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 6.dp
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Analizando información",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Estamos generando tu resultado de prioridad para el examen HbA1c.",
            )
        }
    }

    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        count = true
    }
}



@Composable private fun MainContainer(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ElectricBluePallete.BACKGROUND)
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(top = spacing.md, bottom = spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.xl)
    ) {

        item { HeaderTitle() }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.md),
                verticalArrangement = Arrangement.spacedBy(spacing.md)
            ) {

                DescriptionAndImage()

                PriorityTextInfoAndPercentage(showPriorityResultVM)

                SeeProbDistributions(showPriorityResultVM)

                SeeParametersAnalitics(showPriorityResultVM)

                RecomendationsArea(showPriorityResultVM)
            }
        }
    }
}



@Composable private fun HeaderTitle() {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = spacing.xl),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        Text(
            text = "Prioridad de acceso a la Hemoglobina glicosilada (HbA1c)",
            fontFamily = MyFont.jakartaSansExtrabold,
            fontSize = ResponsiveText.displaymiddle(),
            lineHeight = ResponsiveLineHeight.displayMiddle(),
            color = ElectricBluePallete.BLUE_1
        )
    }
}



@Composable private fun DescriptionAndImage() {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        ImageBrainIaInDescription()

        TitleAndDescriptionInfo()
    }
}



@Composable private fun ImageBrainIaInDescription() {

    val spacing = LocalSpacing.current

    Icon(
        imageVector = Icons.Filled.Psychology,
        contentDescription = "brain",
        tint = Color.Black,
        modifier = Modifier
            .size(spacing.xxl)
    )
}



@Composable private fun TitleAndDescriptionInfo() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Resultado del modelo",
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontFamily = MyFont.jakartaSansExtrabold
        )

        Text(
            text = "La red neuronal analizó tus datos y ha determinado la siguiente prioridad de acceso al examen HbA1c",
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = Color.Black,
            fontFamily = MyFont.workSansRegular
        )
    }
}



/**
 * Pilasssss en esta función se debe arreglar
 * lo de cuando la lista viene vacia! eso importante pues
 * tenlo en cuenta
 * */
@Composable private fun PriorityTextInfoAndPercentage(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val probsArr by showPriorityResultVM.listPriorityOutput.collectAsStateWithLifecycle()
    val probHigher = if (probsArr.isNotEmpty()) probsArr.maxBy { it.probability }.probability else 0f

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        CardWithTitle()

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {

            // Columna (UNO)
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing.sm)
            ) {

                WhichPriorityTitle(showPriorityResultVM)

                DescriptionFromPriorityTitle(showPriorityResultVM)
            }

            // Circulo de porcentaje
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                ProbabilityCircle(probHigher)
            }
        }
    }
}



@Composable private fun CardWithTitle() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    Box(
        modifier = Modifier
            .background(ElectricBluePallete.BLUE_5.copy(alpha = 0.1f), shape)
            .padding(horizontal = spacing.md, vertical = spacing.sm)
    ) {

        Text(
            text = "Predicción de prioridad",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            color = ElectricBluePallete.BLUE_2,
            fontWeight = FontWeight.Bold
        )
    }
}



/**
 * Pilassssss
 * Esta función tambien requiere ajustes respecto a
 * la carga de un array vacio, esta lógica debe ser
 * general.
 * */
@Composable private fun WhichPriorityTitle(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val arr by showPriorityResultVM.listPriorityOutput.collectAsStateWithLifecycle()
    val priority = if (arr.isNotEmpty()) arr.maxBy { it.probability }.displayName else ""

    val value = when(priority) {
        "Prioridad I" -> "Baja"
        "Prioridad II" -> "Media"
        "Prioridad III" -> "Alta"
        else -> ""
    }

    Text(
        text = value,
        fontFamily = MyFont.jakartaSansExtrabold,
        fontSize = ResponsiveText.displaymiddle(),
        lineHeight = ResponsiveLineHeight.displayMiddle(),
        color = ElectricBluePallete.BLUE_2,
        fontWeight = FontWeight.Normal
    )
}



@Composable private fun DescriptionFromPriorityTitle(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val arr by showPriorityResultVM.listPriorityOutput.collectAsStateWithLifecycle()
    val priority = if (arr.isNotEmpty()) arr.maxBy { it.probability }.displayName else ""

    val value = when(priority) {
        "Prioridad I" -> "Indica una menor necesidad de priorizar el acceso al examen HbA1c."
        "Prioridad II" -> "Indica una necesidad intermedia de priorizar el acceso al examen HbA1c."
        "Prioridad III" -> "Indica una mayor necesidad de priorizar el acceso al examen HbA1c."
        else -> ""
    }

    Text(
        text = value,
        fontFamily = MyFont.workSansRegular,
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        color = Color.Black
    )
}



@Composable
private fun ProbabilityCircle(
    probability: Float,
    modifier: Modifier = Modifier
) {

    val percentage = probability * 100f

    Box(
        modifier = modifier.size(150.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.matchParentSize()
        ) {

            val strokeWidth = 12.dp.toPx()
            val diameter = size.minDimension - strokeWidth

            val topLeft = Offset(
                x = (size.width - diameter) / 2f,
                y = (size.height - diameter) / 2f
            )

            val arcSize = Size(width = diameter, height = diameter)

            // CÍRCULO BASE
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    //cap = StrokeCap.Round
                )
            )

            // PROGRESO
            drawArc(
                color = ElectricBluePallete.BLUE_2,
                startAngle = -90f,
                sweepAngle = 360f * probability,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth / 2f,
                    cap = StrokeCap.Round
                )
            )
        }

        // CONTENIDO CENTRAL
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "${percentage.formatPercentage()}%",
                fontSize = ResponsiveText.title(),
                lineHeight = ResponsiveLineHeight.small(),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = MyFont.geistMonoNerdFontRegular
            )

            Text(
                text = "Confianza",
                fontFamily = MyFont.spaceGroteskRegular,
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                color = Color.Black
            )
        }
    }
}



@Composable private fun SeeProbDistributions(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)

    ) {

        TitleProbDistrib()

        TextDescribingDistribs()

        ProbsByClassShow(showPriorityResultVM)
    }
}



@Composable private fun TitleProbDistrib() {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        Icon(
            imageVector = Icons.Filled.Analytics,
            contentDescription = "analitics",
            tint = ElectricBluePallete.BLUE_2,
            modifier = Modifier.size(spacing.xl)
        )

        Text(
            text = "Distribución",
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            fontFamily = MyFont.jakartaSansRegular,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}



@Composable private fun TextDescribingDistribs() {

    Text(
        text = "A continuación, se muestra la probabilidad de cada clase según el modelo",
        fontSize = ResponsiveText.small(),
        lineHeight = ResponsiveLineHeight.small(),
        fontFamily = MyFont.workSansRegular,
        color = Color.Black,
    )
}



@Composable private fun ProbsByClassShow(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val listProbs by showPriorityResultVM.listPriorityOutput.collectAsStateWithLifecycle()

    // Generar una lógica de carga posterior

    if (listProbs.isNotEmpty()) {

        val higherProb = listProbs.maxBy { it.probability }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {

            listProbs.forEach { ItemShowProbs(it, higherProb) }
        }
    }
}



@Composable private fun ItemShowProbs(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val isHigher = item.displayName == higherProb.displayName

    val normalBorderColor = Color.LightGray.copy(alpha = 0.5f)
    val normalBackgroundColor = Color.LightGray.copy(alpha = 0.1f)

    val borderBox = when(item.displayName) {
        "Prioridad I" -> Color(0xFF10B981)
        "Prioridad II" -> Color(0xFFF59E0B)
        "Prioridad III" -> Color(0xFFEF4444)
        else -> Color.Black
    }

    val backgroundColor = when(item.displayName) {
        "Prioridad I" -> Color(0xFFF0FDF4)
        "Prioridad II" -> Color(0xFFFFFBEB)
        "Prioridad III" -> Color(0xFFFEF2F2)
        else -> Color.Black
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (isHigher) borderBox else normalBorderColor, shape)
            .background(if (isHigher) backgroundColor else normalBackgroundColor, shape)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        InformationTextItem(item, higherProb)

        ProbabilityProgressBar(item, higherProb)
    }
}



@Composable private fun InformationTextItem(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CircleLetterForPriority(item, higherProb)

        InfoPriorityDescribe(item, higherProb)
    }
}



@Composable private fun CircleLetterForPriority(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    val spacing = LocalSpacing.current
    val letter = item.displayName.split(" ").last()

    val isHigher = item.displayName == higherProb.displayName

    val backgroundNormalCircle = when(item.displayName) {
        "Prioridad I" -> if (!isHigher) Color(0xFFF0FDF4) else Color(0xFF10B981)
        "Prioridad II" -> if (!isHigher) Color(0xFFFFFBEB) else Color(0xFFF59E0B)
        "Prioridad III" -> if (!isHigher) Color(0xFFFEF2F2) else Color(0xFFEF4444)
        else -> Color.Black
    }

    val textColor = when(item.displayName) {
        "Prioridad I" -> if (!isHigher) Color(0xFF059669) else Color(0xFFFFFFFF)
        "Prioridad II" -> if (!isHigher) Color(0xFFD97706) else Color(0xFFFFFFFF)
        "Prioridad III" -> if (!isHigher) Color(0xFFDC2696) else Color(0xFFFFFFFF)
        else -> Color.Black
    }

    val borderBox = when(item.displayName) {
        "Prioridad I" -> Color(0xFF10B981)
        "Prioridad II" -> Color(0xFFF59E0B)
        "Prioridad III" -> Color(0xFFEF4444)
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .size(spacing.xxl)
            .clip(CircleShape)
            .border(1.dp, borderBox, CircleShape)
            .background(backgroundNormalCircle),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = letter,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            color = textColor,
            fontFamily = MyFont.geistMonoNerdFontRegular,
            fontWeight = FontWeight.Bold
        )
    }
}



@Composable private fun InfoPriorityDescribe(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.displayName,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.workSansRegular,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            ShowProbabilityValue(item, higherProb)
        }


        Text(
            text = item.subtitle,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            fontFamily = MyFont.workSansRegular,
            color = Color.Gray
        )
    }
}



@Composable private fun ShowProbabilityValue(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    val isHigher = item.displayName == higherProb.displayName

    val textColor = when(item.displayName) {
        "Prioridad I" ->  Color(0xFF10B981)
        "Prioridad II" ->  Color(0xFFF59E0B)
        "Prioridad III" -> Color(0xFFEF4444)
        else -> Color.Black
    }

    Text(
        text = (item.probability * 100).formatPercentage() + "%",
        fontFamily = MyFont.geistMonoNerdFontRegular,
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = if (isHigher) textColor else Color.Black,
        fontWeight = if (isHigher) FontWeight.Bold else FontWeight.Normal
    )
}



@Composable private fun ProbabilityProgressBar(
    item: PriorityResultModel,
    higherProb: PriorityResultModel
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val isHigher = item.displayName == higherProb.displayName
    val normalTrackBar = Color.LightGray.copy(alpha = 0.5f)

    val trackBarIsHigher = when(item.displayName) {
        "Prioridad I" -> Color(0xFFD1FAE5)
        "Prioridad II" -> Color(0xFFFEF3C7)
        "Prioridad III" -> Color(0xFFFEE2E2)
        else -> Color.Black
    }

    val progressBarColor = when(item.displayName) {
        "Prioridad I" -> Color(0xFF10B981)
        "Prioridad II" -> Color(0xFFF59E0B)
        "Prioridad III" -> Color(0xFFEF4444)
        else -> Color.Black
    }

    val heightBar = spacing.md

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(heightBar)
            .background(if (isHigher) trackBarIsHigher else normalTrackBar, shape)
            .padding(horizontal = spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(item.probability)
                .height(heightBar / 2)
                .background(progressBarColor, shape)
        )
    }
}



@Composable private fun SeeParametersAnalitics(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        TitleParamsAndDecription()

        ToShowParameters(showPriorityResultVM)
    }
}



@Composable
fun TitleParamsAndDecription() {

    val spacing = LocalSpacing.current

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Text(
            text = "Parámetros de análisis",
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            fontFamily = MyFont.jakartaSansRegular
        )

        Text(
            text = "Biomarcadores y variables metabolicas procesadas",
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            color = Color.Black,
            fontFamily = MyFont.workSansRegular
        )
    }
}



private fun getParametersArr(priority: PriorityInputModel): List<ParametersShowUi> =
    listOf(
        ParametersShowUi(
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            parameter = "Nivel de actividad",
            value = priority.activityLevel
        ),
        ParametersShowUi(
            icon = Icons.Filled.CalendarMonth,
            parameter = "Edad",
            value = priority.age.toInt().toString() + " años"
        ),
        ParametersShowUi(
            icon = Icons.Filled.Straighten,
            parameter = "Índice de masa\ncorporal",
            value = priority.bmi.ifIsIntToInt() + " Kg/m²"
        ),
        ParametersShowUi(
            icon = Icons.Filled.FamilyRestroom,
            parameter = "Antedentes de Diabetes",
            value = priority.familyHistoryDiabetes
        ),
        ParametersShowUi(
            icon = Icons.Filled.WaterDrop,
            parameter = "Glucosa en ayunas",
            value = priority.glucoseFasting.ifIsIntToInt() + " mg/dL"
        ),
        ParametersShowUi(
            icon = Icons.Filled.Restaurant,
            parameter = "Glucosa Postprandial",
            value = priority.glucosePostprandial.ifIsIntToInt() + " mg/dL"
        )
    )



@Composable private fun ToShowParameters(
    showPriorityResultVM: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val priorityInput by showPriorityResultVM.priorityInput.collectAsStateWithLifecycle()

    priorityInput?.let {

        val arr = getParametersArr(it)

        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.md)
        ) {

            arr.forEachIndexed { index, item  ->
                TableRowItemParam(
                    item = item,
                    index = index,
                    lastIndex = arr.indices.last
                )
            }
        }
    }
}



@Composable private fun TableRowItemParam(
    item: ParametersShowUi,
    index: Int,
    lastIndex: Int
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        // Este es el icon
        Box(
            modifier = Modifier
                .background(
                    ElectricBluePallete.BLUE_5.copy(alpha = 0.1f),
                    shape
                )
                .padding(spacing.xs),
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = "",
                tint = ElectricBluePallete.BLUE_2
            )
        }

        Spacer(Modifier.width(spacing.md))

        Text(
            text = item.parameter,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            fontFamily = MyFont.jakartaSansRegular,
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = item.value,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontFamily = MyFont.jakartaSansExtrabold
        )
    }

    if (index != lastIndex)
        HorizontalDivider(
            color = Color.LightGray.copy(alpha = 0.5f),
        )
}



@Composable private fun RecomendationsArea(
    vm: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        TitleAndSubtitForRecomendations()

        ObjectiveAndSegArea(vm)
    }
}



@Composable private fun TitleAndSubtitForRecomendations() {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Text(
            text = "Recomendaciones",
            fontFamily = MyFont.jakartaSansRegular,
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = "Encuentra aquí una retroalimentación y pasos que a continuación puedes seguir",
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            fontFamily = MyFont.workSansRegular,
            color = Color.Black
        )
    }
}



@Composable private fun ObjectiveAndSegArea(
    vm: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        TitleToDescribe(vm)

        ObjetiveDescription(vm)
    }
}



@Composable private fun TitleToDescribe(
    vm: ShowPriorityResultVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    val recomend by vm.recomendationUi.collectAsStateWithLifecycle()
    val result by vm.listPriorityOutput.collectAsStateWithLifecycle()

    val priorityMax = if (result.isNotEmpty()) result.maxBy { it.probability }.displayName else ""

    val background = when(priorityMax) {
        "Prioridad I" -> Color(0xFFF0FDF4)
        "Prioridad II" -> Color(0xFFFFFBEB)
        "Prioridad III" -> Color(0xFFFEF2F2)
        else -> Color.Black
    }

    val textColor = when(priorityMax) {
        "Prioridad I" -> Color(0xFF059669)
        "Prioridad II" -> Color(0xFFD97706)
        "Prioridad III" -> Color(0xFFDC2696)
        else -> Color.Black
    }

    if (recomend != null) {
        Box (
            modifier = Modifier
                .background(background, shape)
                .padding(horizontal = spacing.md, vertical = spacing.sm),
        ) {

            Text(
                text = recomend?.title ?: "",
                fontFamily = MyFont.workSansRegular,
                lineHeight = ResponsiveLineHeight.small(),
                fontSize = ResponsiveText.small(),
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable private fun ObjetiveDescription(vm: ShowPriorityResultVM) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.md)
    ) {

        CircularIconInObjective()

        TitleAndSubtitleObjectivePriority()
    }
}



@Composable private fun CircularIconInObjective() {
    Text(
        text = ""
    )
}



@Composable private fun TitleAndSubtitleObjectivePriority() {
    Text("imp")
}



private fun Float.formatPercentage(): String =
    String.format(Locale.US, "%.1f", this)