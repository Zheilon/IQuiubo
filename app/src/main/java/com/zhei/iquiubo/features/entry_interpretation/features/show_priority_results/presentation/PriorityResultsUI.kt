package com.zhei.iquiubo.features.entry_interpretation.features.show_priority_results.presentation
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.features.entry_interpretation.features.fillingInToInterpretation.presentation.FillingInToInterpVM
import java.util.Locale


@Composable fun PriorityResultsUI(
    fillingInToInterpVM: FillingInToInterpVM
) {

    val priorityInfo by fillingInToInterpVM.priorityResultInfo.collectAsStateWithLifecycle()
    val priorityList by fillingInToInterpVM.priorityResultList.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        item {
            Text(
                text = priorityInfo,
                color = Color.Black,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontFamily = MyFont.geistMonoNerdFontRegular
            )
        }

        item {
            PriorityPredictionChart(results = priorityList)
        }

        item { Spacer(modifier = Modifier.height(spacing.md)) }
    }
}

// ============================================================
// 3. COMPONENTE PRINCIPAL DE LA GRÁFICA
// ============================================================

@Composable
fun PriorityPredictionChart(
    results: List<PriorityResultModel>,
    modifier: Modifier = Modifier
) {

    // Clase con mayor probabilidad
    val predictedPriority = results.maxByOrNull {
        it.probability
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            PredictionHeader()

            predictedPriority?.let { result ->
                MainPredictionCard(
                    result = result
                )
            }

            ProbabilitySection(
                results = results,
                predictedPriority = predictedPriority
            )
        }
    }
}


// ============================================================
// 4. HEADER
// ============================================================

@Composable
private fun PredictionHeader() {

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Text(
            text = "Predicción de prioridad",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Distribución generada por el modelo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// ============================================================
// 5. TARJETA DE LA PREDICCIÓN PRINCIPAL
// ============================================================

@Composable
private fun MainPredictionCard(
    result: PriorityResultModel
) {

    val percentage = result.probability * 100f

    Surface(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(22.dp),

        color = MaterialTheme.colorScheme.primaryContainer
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Column(
                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = "PREDICCIÓN PRINCIPAL",

                    style = MaterialTheme.typography.labelMedium,

                    fontWeight = FontWeight.Bold,

                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = result.name,

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "Mayor probabilidad del modelo",

                    style = MaterialTheme.typography.bodySmall,

                    color = MaterialTheme.colorScheme
                        .onPrimaryContainer
                        .copy(alpha = 0.75f)
                )
            }


            // ------------------------------------------------
            // PORCENTAJE
            // ------------------------------------------------

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = "${percentage.formatPercentage()}%",

                    style = MaterialTheme.typography.headlineMedium,

                    fontWeight = FontWeight.Bold,

                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "confianza",

                    style = MaterialTheme.typography.labelSmall,

                    color = MaterialTheme.colorScheme
                        .onPrimaryContainer
                        .copy(alpha = 0.75f)
                )
            }
        }
    }
}


// ============================================================
// 6. SECCIÓN DE PROBABILIDADES
// ============================================================

@Composable
private fun ProbabilitySection(
    results: List<PriorityResultModel>,
    predictedPriority: PriorityResultModel?
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Text(
            text = "Probabilidad por clase",

            style = MaterialTheme.typography.titleMedium,

            fontWeight = FontWeight.SemiBold,

            color = MaterialTheme.colorScheme.onSurface
        )


        results.forEach { result ->

            PriorityProbabilityRow(
                result = result,

                isSelected = result == predictedPriority
            )
        }
    }
}


// ============================================================
// 7. FILA DE CADA PRIORIDAD
// ============================================================

@Composable
private fun PriorityProbabilityRow(
    result: PriorityResultModel,
    isSelected: Boolean
) {

    val percentage = result.probability * 100f

    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {

        // ----------------------------------------------------
        // NOMBRE + PORCENTAJE
        // ----------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Indicador de clase seleccionada
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary

                            } else {
                                MaterialTheme.colorScheme.outlineVariant
                            }
                        )
                )


                Text(
                    text = result.name,

                    style = MaterialTheme.typography.bodyMedium,

                    fontWeight =
                    if (isSelected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },

                    color = MaterialTheme.colorScheme.onSurface
                )
            }


            Text(
                text = "${percentage.formatPercentage()}%",

                style = MaterialTheme.typography.labelLarge,

                fontWeight = FontWeight.Bold,

                color =
                if (isSelected) {

                    MaterialTheme
                        .colorScheme
                        .primary

                } else {

                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
                }
            )
        }


        // ----------------------------------------------------
        // BARRA
        // ----------------------------------------------------

        ProbabilityBar(
            probability = result.probability,

            isSelected = isSelected
        )
    }
}


// ============================================================
// 8. BARRA DE PROBABILIDAD ANIMADA
// ============================================================

@Composable
private fun ProbabilityBar(
    probability: Float,
    isSelected: Boolean
) {

    val animatedProgress by animateFloatAsState(

        targetValue = probability.coerceIn(
            minimumValue = 0f,
            maximumValue = 1f
        ),

        animationSpec = tween(
            durationMillis = 900,

            easing = FastOutSlowInEasing
        ),

        label = "probabilityAnimation"
    )


    // --------------------------------------------------------
    // CONTENEDOR DE LA BARRA
    // --------------------------------------------------------

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(
                RoundedCornerShape(50)
            )
            .background(
                MaterialTheme
                    .colorScheme
                    .surfaceVariant
            )
    ) {

        // ----------------------------------------------------
        // PROGRESO
        // ----------------------------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(50)
                )
                .background(

                    if (isSelected) {

                        MaterialTheme
                            .colorScheme
                            .primary

                    } else {

                        MaterialTheme
                            .colorScheme
                            .primary
                            .copy(alpha = 0.30f)
                    }
                )
        )
    }
}


// ============================================================
// 9. FORMATEO DEL PORCENTAJE
// ============================================================

private fun Float.formatPercentage(): String {

    return String.format(
        Locale.US,
        "%.1f",
        this
    )
}