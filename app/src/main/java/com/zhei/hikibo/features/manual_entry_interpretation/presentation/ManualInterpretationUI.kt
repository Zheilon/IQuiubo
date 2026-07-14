package com.zhei.hikibo.features.manual_entry_interpretation.presentation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhei.hikibo.core.common.Constants
import com.zhei.hikibo.core.common.MyFont
import com.zhei.hikibo.core.common.ResponsiveLineHeight
import com.zhei.hikibo.core.common.ResponsiveText
import com.zhei.hikibo.core.common.dashedBorder
import com.zhei.hikibo.core.common.locals.LocalSpacing
import com.zhei.hikibo.ui.theme.IQuiuboColorPallete


@Composable fun ManualInterpretationUI(
    interpretationVM: InterpretationVM
) {


    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .background(IQuiuboColorPallete.backgroundLight)
    ) {

        FirstSection(interpretationVM)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(IQuiuboColorPallete.backgroundLight)
                .padding(horizontal = spacing.md)
        ) {

            item { SecondSection(interpretationVM) }

            item { Spacer(modifier = Modifier.height(spacing.lg)) }

            item { ThirdSection(interpretationVM) }


            // Este spacer queda a lo último para generar un espacio entre
            // el LazyColumn y el NavigationBar.
            item { Spacer(modifier = Modifier.height(spacing.md)) }
        }
    }
}



@Composable private fun FirstSection(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    // Esta sección será una columna la cual contega, 
    // primero el título de la pantalla y luego una descripción de esta.
    
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                IQuiuboColorPallete.moradoPrimario,
                //RoundedCornerShape(bottomStart = spacing.xxl, bottomEnd = spacing.xxl)
            )
            .padding(vertical = spacing.lg, horizontal = spacing.lg)
    ) {
        
        TitleFirstSection()

        SubtitleFirstSection()
    }
}



@Composable private fun TitleFirstSection() {
    
    Text(
        text =  "Portal de ingreso de datos", // Interpreta tus resultados
        fontSize = ResponsiveText.headline(),
        lineHeight = ResponsiveLineHeight.headline(),
        fontFamily = MyFont.spaceGroteskRegular,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}



@Composable private fun SubtitleFirstSection() {

    Text(
        text = "¡Busca el nombre de los exámenes que te tomaste!",
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.backgroundLight,
        fontFamily = MyFont.jakartaSansRegular
    )
}



@Composable private fun SecondSection(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        CustomTextfieldSearch(interpretationVM)

        // Este Spacer se traslado dentro de la función LazyColumnExamNames...
        // dentro del ?.let{}, ya que si no hay lista se va a conservar el
        // espacio que hay más arriba, en el composable principal.
        // Spacer(modifier = Modifier.height(spacing.md))

        ExamNamesSecondSection(interpretationVM)
    }
}



@Composable private fun CustomTextfieldSearch(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    val text by interpretationVM.exam.collectAsStateWithLifecycle()

    // Esta segunda sección será un textfield super personalizado
    // con el cual se pueda buscar el nombre del examen
    //
    // 1). Si hay texto al final del textfield tendra que aparecer
    // una ( x ), para borrar lo que está escrito.

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Animación del borde respecto a el foco.

    val focusedColor by animateColorAsState(
        targetValue = when {
            isFocused -> IQuiuboColorPallete.moradoPrimario
            else -> IQuiuboColorPallete.secondaryText
        },
        animationSpec = tween(250),
        label = "animate_color"
    )

    // Grosor del borde respecto a el foco.

    val animateWidthBorder by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 1.dp,
        label = "dp_width"
    )

    // Animo el icono de la lupa que se encuentra en el
    // TextField, respecto a cuando esta focus el textfield

    val animateIcon by animateFloatAsState(
        targetValue = if (isFocused) 360f else 0f,
        animationSpec = tween(1000),
        label = "anime_icon"
    )

    BasicTextField(
        interactionSource = interactionSource,
        value = text,
        onValueChange = { interpretationVM.updateExamField(it) },
        singleLine = true,
        textStyle = TextStyle(
            color = IQuiuboColorPallete.primaryText,
            fontFamily = MyFont.jakartaSansRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body()
        ),
        cursorBrush = SolidColor(IQuiuboColorPallete.moradoPrimario),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        decorationBox = { innerTextField ->

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val strokeWidth = animateWidthBorder.toPx()
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            color = focusedColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = strokeWidth
                        )
                    }
                    //.border(animateWidthBorder, focusedColor, RoundedCornerShape(spacing.md))
                    //.background(Color.White, RoundedCornerShape(spacing.md))
                    .padding(vertical = spacing.md)
            ) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search_icon",
                    tint = focusedColor,
                    modifier = Modifier
                        .size(spacing.xl)
                        .graphicsLayer { rotationX = animateIcon }
                )

                Spacer(modifier = Modifier.width(spacing.sm))

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    if (text.isEmpty()) {
                        Text(
                            text = "Buscar...",
                            color = IQuiuboColorPallete.secondaryText,
                            fontFamily = MyFont.spaceGroteskRegular,
                            fontSize = ResponsiveText.body(),
                            lineHeight = ResponsiveLineHeight.body()
                        )
                    }

                    innerTextField()
                }

                if (text.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "search_icon",
                        tint = IQuiuboColorPallete.secondaryText.copy(alpha = 0.7f),
                        modifier = Modifier
                            .size(spacing.xl)
                            .clip(CircleShape)
                            .clickable { interpretationVM.cleanText() }
                    )
                }
            }
        }
    )
}



@Composable private fun ExamNamesSecondSection(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current
    val filteredList by interpretationVM.filteredList.collectAsStateWithLifecycle()
    val text by interpretationVM.exam.collectAsStateWithLifecycle()


    AnimatedVisibility(
        visible = filteredList.isNotEmpty(),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {

        Column {

            Spacer(modifier = Modifier.height(spacing.md))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                filteredList.forEachIndexed { index, bioMarker ->

                    ItemBiomarkerLazyColumn(
                        index = index,
                        lastIndex = filteredList.size - 1,
                        onlyOne = filteredList.size == 1,
                        bioMarker = bioMarker,
                        interpretationVM = interpretationVM
                    )
                }
            }
        }
    }
}



@Composable
fun ItemBiomarkerLazyColumn(
    index: Int,
    lastIndex: Int,
    onlyOne: Boolean,
    bioMarker: BioMarkerUI,
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    var customBorders = when(index) {
        0 -> RoundedCornerShape(topStart = spacing.md, topEnd = spacing.md)
        lastIndex -> RoundedCornerShape(bottomStart = spacing.md, bottomEnd = spacing.md)
        else -> RoundedCornerShape(0.dp)
    }

    customBorders = if (onlyOne) RoundedCornerShape(spacing.md)
    else customBorders


    var customBorderButton = when (index) {
        0 -> RoundedCornerShape(topEnd = spacing.md)
        lastIndex -> RoundedCornerShape(bottomEnd = spacing.md)
        else -> RoundedCornerShape(0.dp)
    }

    customBorderButton = if (onlyOne) RoundedCornerShape(topEnd = spacing.md, bottomEnd = spacing.md)
    else customBorderButton

    val customColorButton = if (index % 2 != 0) IQuiuboColorPallete.LOW_PINK
    else IQuiuboColorPallete.LOW_PINK.copy(0.6f)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White, customBorders)
            .drawBehind {
                if (index != lastIndex) {
                    val strokeWidth = (1.dp).toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = IQuiuboColorPallete.backgroundLight,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        val widthColumn = (Constants.WIDTH_DP_DEVICE() - (spacing.md * 2) - spacing.xl)

        Column(
            modifier = Modifier
                .width(widthColumn)
                .padding(horizontal = spacing.md, vertical = spacing.sm)
        ) {
            
            Text(
                text = bioMarker.bioMarker.displayName,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.caption(),
                lineHeight = ResponsiveLineHeight.caption(),
                fontWeight = FontWeight.Bold,
                color = IQuiuboColorPallete.primaryText
            )

            Text(
                text = bioMarker.bioMarker.unit,
                fontFamily = MyFont.workSansRegular,
                fontSize = ResponsiveText.caption() ,
                lineHeight = ResponsiveLineHeight.caption(),
                color = IQuiuboColorPallete.secondaryText
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(spacing.xl),
                //.background(customColorButton, customBorderButton),
            contentPadding = PaddingValues(0.dp),
            shape = customBorderButton,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!bioMarker.selected) customColorButton else Color(0xFFD5FFD4),
                //disabledContainerColor = Color(0xFFD5FFD4)
            ),
            //enabled = !bioMarker.selected,
            onClick = {
                interpretationVM.selectBioMarker(bioMarker.bioMarker)
                // Una vez el user selecciona un biomarcador
                // se limpia el texto, para que vuelva a entrar uno.
                // interpretationVM.cleanText()
            }
        ) {

            Icon(
                imageVector = if (!bioMarker.selected) Icons.Default.Add else Icons.Default.Check,
                contentDescription = "add_ic",
                tint = if (!bioMarker.selected) IQuiuboColorPallete.moradoPrimario else Color(0xFF089400),
                modifier = Modifier.size(spacing.lg)
            )
        }
    }
}



@Composable private fun ThirdSection(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        SubtitleAndIconNumber(interpretationVM)

        Spacer(modifier = Modifier.height(spacing.md))

        FlowRowSelectedItemsThirdSection(interpretationVM)
    }
}



@Composable private fun SubtitleAndIconNumber(
    interpretationVM: InterpretationVM
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Bottom
    ) {

        SubtitleThirdSection(interpretationVM)
        
        Spacer(modifier = Modifier.weight(1f))

        IconNumberThirdSection(interpretationVM)
    }
}



@Composable private fun SubtitleThirdSection(
    interpretationVM: InterpretationVM
) {

    val selectedExamList by interpretationVM.selectedExams.collectAsStateWithLifecycle()
    val size = selectedExamList.size

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = if (size > 1) "Exámenes seleccinados" else "Exámen seleccionado",
            fontFamily = MyFont.spaceGroteskRegular,
            fontSize = ResponsiveText.body(),
            lineHeight = ResponsiveLineHeight.body(),
            color = IQuiuboColorPallete.primaryText,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable private fun IconNumberThirdSection(
    interpretationVM: InterpretationVM
) {

    val selectedExamList by interpretationVM.selectedExams.collectAsStateWithLifecycle()
    val size = selectedExamList.size
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFE5CDFF), RoundedCornerShape(spacing.sm))
            .padding(horizontal = spacing.sm)
    ) {

        Text(
            text = "$size",
            fontFamily = MyFont.geistMonoNerdFontRegular,
            fontSize = ResponsiveText.caption(),
            fontWeight = FontWeight.Bold,
            color = IQuiuboColorPallete.primaryText
        )
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable private fun FlowRowSelectedItemsThirdSection(
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current
    val selectedList by interpretationVM.selectedExams.collectAsStateWithLifecycle()

    FlowRow (
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
    
        selectedList.forEach {
            ItemFlowRowThirdSection(it, interpretationVM)
        }
    }
}



@Composable private fun ItemFlowRowThirdSection(
    bioMarker: BioMarkerUI,
    interpretationVM: InterpretationVM
) {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .dashedBorder(1.dp, IQuiuboColorPallete.LOW_PINK)
            .background(IQuiuboColorPallete.LOW_PINK.copy(0.3f), RoundedCornerShape(10.dp))
            .padding(horizontal = spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = bioMarker.bioMarker.compactName,
            color = IQuiuboColorPallete.primaryText,
            fontFamily = MyFont.jakartaSansRegular,
            fontSize = ResponsiveText.caption()
        )

        Spacer(modifier = Modifier.width(spacing.md))

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .size(spacing.md)
                .clip(CircleShape)
                .clickable { interpretationVM.selectBioMarker(bioMarker.bioMarker) },
            tint = Color.Black
        )
    }
}
