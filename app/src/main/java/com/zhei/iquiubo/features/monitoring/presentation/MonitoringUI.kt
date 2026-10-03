package com.zhei.iquiubo.features.monitoring.presentation
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.iquiubo.core.common.Constants
import com.zhei.iquiubo.core.common.MyFont
import com.zhei.iquiubo.core.common.ResponsiveLineHeight
import com.zhei.iquiubo.core.common.ResponsiveText
import com.zhei.iquiubo.core.common.ifIsIntToInt
import com.zhei.iquiubo.core.common.locals.LocalSpacing
import com.zhei.iquiubo.ui.theme.IQuiuboColorPallete
import kotlinx.coroutines.launch


@Composable fun MonitoringUI(
    monitoringVM: MonitoringVM = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF1FF).copy(alpha = 0.1f))
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.xl)
    ) {

        item { HeaderMonitoring() }

        item { MonitoringActiveBiomarkers() }

        item { SectionToShowMyBiomarkers(monitoringVM) }

        item { SeeDetailsOfSelectedBiomarker(monitoringVM) }
    }
}


@Composable private fun HeaderMonitoring() {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .fillMaxWidth()
            //.background(Color.White)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ImageLeftTitle()

        Spacer(modifier = Modifier.width(spacing.md ))

        TextHeaderTitle(Modifier.weight(1f))
    }
}


@Composable private fun ImageLeftTitle() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Icon(
        imageVector = Icons.Default.Equalizer,
        contentDescription = "icon",
        tint = IQuiuboColorPallete.moradoPrimario,
        modifier = Modifier
            .size(spacing.xxl)
            //.border(1.dp, IQuiuboColorPallete.moradoPrimario.copy(alpha = 0.1f), shape)
            .background(IQuiuboColorPallete.backgroundLight, shape)
            .padding(spacing.sm)
    )
}


@Composable private fun TextHeaderTitle(modifier: Modifier) {
    Text(
        text = "Seguimiento",
        fontSize = ResponsiveText.headline(),
        lineHeight = ResponsiveLineHeight.headline(),
        fontFamily = MyFont.workSansRegular,
        fontWeight = FontWeight.ExtraBold,
        modifier = modifier
    )
}


@Composable private fun MonitoringActiveBiomarkers() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    Row (
        modifier = Modifier
            .fillMaxWidth()
            //.shadow(2.dp, shape = shape)
            .background(Color.White, shape),
        horizontalArrangement = Arrangement.spacedBy(spacing.md)
    ) {

        //IconCheckActive()

        ColumnForInfoIntoMonitoringActive()
    }
}


@Composable private fun IconCheckActive() {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)

    Icon(
        imageVector = Icons.Outlined.CheckCircle,
        contentDescription = "check",
        tint = IQuiuboColorPallete.moradoPrimario,
        modifier = Modifier
            .size(spacing.xxl)
            .background(IQuiuboColorPallete.backgroundLight, shape)
            .padding(spacing.sm)
    )
}


@Composable private fun ColumnForInfoIntoMonitoringActive() {

    val spacing = LocalSpacing.current

    Column (
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        Row (
            horizontalArrangement = Arrangement.spacedBy(spacing.sm)
        ) {

            Text(
                text = "SEGUIMIENTO ACTIVO",
                color = Color(0xFF005049),
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFF6BD8CB).copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = spacing.sm, vertical = spacing.xs)
            )

            Text(
                text = "Panel ${Constants.CURRENT_YEAR}",
                color = Color(0xFF505F76),
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFFD3E4FE).copy(alpha = 0.5f), CircleShape)
                    .padding(horizontal = spacing.sm, vertical = spacing.xs)
            )
        }

        Text(
            text = buildAnnotatedString {
                append("Monitoreando ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = ResponsiveText.caption())) { append("5 ") }
                append("biomarcadores clave")
            },
            color = Color(0xFF505F76),
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
        )
    }
}


@Composable private fun SectionToShowMyBiomarkers(
    monitoringVM: MonitoringVM
) {

    val spacing = LocalSpacing.current

    Column (
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        BiomarkersAndSeeAll()

        AvailableBiomarkersInRow(monitoringVM = monitoringVM)
    }
}


@Composable private fun BiomarkersAndSeeAll() {
    Row (
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "BIOMARCADORES",
            color = Color(0xFF8292AA),
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption() ,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "ver todos",
            color = IQuiuboColorPallete.moradoPrimario,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.caption(),
            lineHeight = ResponsiveLineHeight.caption(),
            fontWeight = FontWeight.ExtraBold,
        )
    }
}


@Composable private fun AvailableBiomarkersInRow(
    monitoringVM: MonitoringVM
) {

    val listBiomarkers by monitoringVM.activeBiomarkers.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current

    val state = rememberScrollState()
    val scope = rememberCoroutineScope()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state)
            .padding(horizontal = 3.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
        listBiomarkers.forEach {
            ItemForRowBiomarkers(
                monitoringVM = monitoringVM,
                biomarker = it,
                onMoveOnFirst = {
                    scope.launch {
                        state.animateScrollTo(0)
                    }
                }
            )
        }
    }
}


@Composable private fun ItemForRowBiomarkers(
    monitoringVM: MonitoringVM,
    biomarker: MonitoringVM.BiomarkerSelection,
    onMoveOnFirst: () -> Unit
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    // Condicionales

    val background = if (biomarker.selected) IQuiuboColorPallete.moradoPrimario else Color.White
    val spotColor  = if (biomarker.selected) IQuiuboColorPallete.moradoPrimario else Color.Black
    val color = if (biomarker.selected) Color.White else Color.Black
    val fontWeight = if (biomarker.selected) FontWeight.Bold else FontWeight.Normal

    Row (
        modifier = Modifier
            .shadow(3.dp, shape = shape, spotColor = spotColor)
            .background(background, shape)
            .clickable {
                monitoringVM.selectDataAndRelocate(biomarker.biomarker)
                onMoveOnFirst()
            }
            .padding(horizontal = spacing.md, vertical = spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        if (biomarker.selected) {
            Box(
                modifier = Modifier
                    .size(spacing.sm)
                    .background(color, CircleShape)
            )
        }

        Text(
            text = biomarker.biomarker,
            color = color,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
            fontWeight = fontWeight,
        )
    }
}


@Composable private fun SeeDetailsOfSelectedBiomarker(
    monitoringVM: MonitoringVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.sm)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape)
            //.shadow(2.dp, shape = shape, spotColor = Color.Black)
            .background(Color.White, shape)
            .padding(spacing.md),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {

        TitleForBiomarkerInDetails(monitoringVM)

        RowIntoDetailsBiomarker(monitoringVM)

        BottomForExposePocentualChange()
    }
}


@Composable private fun TitleForBiomarkerInDetails(
    monitoringVM: MonitoringVM
) {

    val biomarker by monitoringVM.biomarkerSelected.collectAsStateWithLifecycle()

    biomarker?.let {
        Text(
            text = it.biomarker,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.title(),
            lineHeight = ResponsiveLineHeight.title(),
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


@Composable private fun RowIntoDetailsBiomarker(
    monitoringVM: MonitoringVM
) {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
    ) {

        TextDetailsIntoBiomarkerDetails()

        SeeValueIntoDetailBiomarker(monitoringVM)
    }
}


@Composable
fun TextDetailsIntoBiomarkerDetails() {

    val spacing = LocalSpacing.current
    val width = (Constants.WIDTH_DP_DEVICE() - (spacing.md * 2)) / 2

    val inlineId = "Rango optimo"
    val inlineWidth = (inlineId.length * 0.55f + 0.8f).em

    Column (
        modifier = Modifier
            .width(width),
        verticalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
        
        Text(
            text = "Según la (OMS) estas en un Rango Optimo",
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small()
        )
        
        Text(
            text = "Último resultado registrado · 08 sep 2026",
            color = Color.Gray,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
        )
    }
}


@Composable private fun SeeValueIntoDetailBiomarker(
    monitoringVM: MonitoringVM
) {

    val spacing = LocalSpacing.current
    val shape = RoundedCornerShape(spacing.md)
    val width = (Constants.WIDTH_DP_DEVICE() - (spacing.md * 2)) / 2

    val currentBiomarker by monitoringVM.biomarkerSelected.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(width)
    ) {

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(IQuiuboColorPallete.backgroundLight, shape),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = currentBiomarker?.value?.ifIsIntToInt() ?: "",
                fontSize = ResponsiveText.displayBig(),
                lineHeight = ResponsiveLineHeight.small(),
                fontFamily = MyFont.geistMonoNerdFontRegular,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "mg/dL",
                fontSize = ResponsiveText.small(),
                lineHeight = ResponsiveLineHeight.small(),
                fontFamily = MyFont.geistMonoNerdFontRegular
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.sm),
            contentAlignment = Alignment.TopEnd
        ) {

            Icon(
                imageVector = Icons.Default.SettingsAccessibility,
                contentDescription = "ho",
                tint = Color.Black
            )
        }
    }


}


@Composable private fun BottomForExposePocentualChange() {

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        
        IconAndShowInfo()
    }
}


@Composable
fun IconAndShowInfo() {
    
    val spacing = LocalSpacing.current
    
    Row (
        horizontalArrangement = Arrangement.spacedBy(spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = "arrow",
            tint = IQuiuboColorPallete.moradoPrimario
        )
        
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = IQuiuboColorPallete.moradoPrimario,
                        fontWeight = FontWeight.Bold,
                        fontSize = ResponsiveText.caption()
                    )
                ) { append("5 mg/dL ") }
                append("menos vs. Medición anterior")
            },
            color = IQuiuboColorPallete.moradoPrimario,
            fontFamily = MyFont.workSansRegular,
            fontSize = ResponsiveText.small(),
            lineHeight = ResponsiveLineHeight.small(),
        )
    }
}
