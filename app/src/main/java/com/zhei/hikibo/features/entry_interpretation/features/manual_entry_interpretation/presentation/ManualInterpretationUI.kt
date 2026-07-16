package com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay


@Composable fun ManualInterpretationUI(
    manualInterpretationVM: ManualInterpretationVM,
) {


    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        FirstSection(manualInterpretationVM)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(IQuiuboColorPallete.backgroundLight)
        ) {

            SecondSection(manualInterpretationVM)
        }
    }
}



@Composable private fun FirstSection(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current

    // Esta sección será una columna la cual contega, 
    // primero el título de la pantalla y luego una descripción de esta.
    
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(
                IQuiuboColorPallete.accentMiddle,
                //RoundedCornerShape(bottomStart = spacing.xxl, bottomEnd = spacing.xxl)
            )
            .padding(spacing.lg)
            .statusBarsPadding()
    ) {
        
        TitleFirstSection()

        SubtitleFirstSection()

        //ThirdSection(manualInterpretationVM)

        ButtonToNavigate(manualInterpretationVM)
        
        //CustomTextfieldSearch(manualInterpretationVM)
    }
}



@Composable private fun TitleFirstSection() {
    
    Text(
        text =  "Selecciona tus exámenes", // Interpreta tus resultados
        fontSize = ResponsiveText.headline(),
        lineHeight = ResponsiveLineHeight.headline(),
        fontFamily = MyFont.spaceGroteskRegular,
        fontWeight = FontWeight.Bold,
        color = IQuiuboColorPallete.primaryText
    )
}



@Composable private fun SubtitleFirstSection() {

    Text(
        text = "¡Busca el nombre de los exámenes que te tomaste!",
        fontSize = ResponsiveText.caption(),
        lineHeight = ResponsiveLineHeight.caption(),
        color = IQuiuboColorPallete.secondaryText,
        fontFamily = MyFont.jakartaSansRegular
    )
}



@Composable private fun ButtonToNavigate(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current
    val selectedList by manualInterpretationVM.selectedExams.collectAsStateWithLifecycle()

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación del botón
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.90f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "press_scale"
    )

    if (selectedList.isNotEmpty())
        Spacer(modifier = Modifier.height(spacing.md))

    AnimatedVisibility(
        visible = selectedList.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(350)) +
            slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = spring(
                    dampingRatio = 0.75f,
                    stiffness = Spring.StiffnessMediumLow
                )
            ) +
            expandVertically(
                expandFrom = Alignment.Bottom,
                animationSpec = spring(
                    dampingRatio = 0.85f,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
        exit = fadeOut(animationSpec = tween(180)) +
            shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(180)
            )
    ) {

        Button(
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            onClick = {}
        ) {

            Text(
                text = "Continuar (${selectedList.size})",
                fontSize = ResponsiveText.button(),
                lineHeight = ResponsiveLineHeight.button(),
                fontFamily = MyFont.jakartaSansRegular,
                color = IQuiuboColorPallete.primaryText
            )
        }
    }
}



@Composable private fun SecondSection(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        // Este Spacer se traslado dentro de la función LazyColumnExamNames...
        // dentro del ?.let{}, ya que si no hay lista se va a conservar el
        // espacio que hay más arriba, en el composable principal.
        // Spacer(modifier = Modifier.height(spacing.md))

        //CustomTextfieldSearch(manualInterpretationVM)

        ExamNamesSecondSection(manualInterpretationVM)
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable private fun CustomTextfieldSearch(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current

    val text by manualInterpretationVM.exam.collectAsStateWithLifecycle()

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
            isFocused -> IQuiuboColorPallete.LOW_PINK
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

    // Se activará el teclado automáticamente al entrar a la pantalla
    val focusRequester = remember { FocusRequester() }
    val keyBoardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        awaitFrame()
        focusRequester.requestFocus()
        keyBoardController?.show()
    }

    BasicTextField(
        modifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .focusRequester(focusRequester),
        interactionSource = interactionSource,
        value = text,
        onValueChange = { manualInterpretationVM.updateExamField(it) },
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
                    //.border(animateWidthBorder, focusedColor, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(spacing.md)
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
                            .clickable { manualInterpretationVM.cleanText() }
                    )
                }
            }
        }
    )
}



@OptIn(ExperimentalFoundationApi::class)
@Composable private fun ExamNamesSecondSection(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current
    val filteredList by manualInterpretationVM.filteredList.collectAsStateWithLifecycle()
    val text by manualInterpretationVM.exam.collectAsStateWithLifecycle()

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
    ) {

        filteredList.forEachIndexed { indexAlpha, alphabetBiomarker ->

            stickyHeader {
                AlphabeticHeader(alphabetBiomarker.word)
            }

            item { Spacer(modifier = Modifier.height(spacing.md)) }

            itemsIndexed(alphabetBiomarker.biomarkerList) { index, biomarker ->

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = spacing.md)
                ) {
                    ItemBiomarkerLazyColumn(
                        index = index,
                        lastIndex = alphabetBiomarker.biomarkerList.size - 1,
                        onlyOne = alphabetBiomarker.biomarkerList.size == 1,
                        bioMarker = biomarker,
                        manualInterpretationVM = manualInterpretationVM,
                        indexAlphabetic = indexAlpha,
                        list = filteredList
                    )
                }

                // AlfabeticLazyItem(manualInterpretationVM, alphabeticItem)
            }
        }
    }
}



@Composable private fun AlphabeticHeader(
    word: String
) {

    val spacing = LocalSpacing.current

    Column(
        Modifier
            .fillMaxWidth()
            .background(IQuiuboColorPallete.backgroundLight.copy(0.6f))
            .padding(horizontal = spacing.md)
            .padding(top = spacing.md),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = word,
            fontSize = ResponsiveText.headline(),
            lineHeight = ResponsiveLineHeight.headline(),
            color = Color.Black,
            fontFamily = MyFont.spaceGroteskRegular,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .background(IQuiuboColorPallete.accentMiddle.copy(0.5f), CircleShape)
                .padding(horizontal = spacing.md)
            )

        // HorizontalDivider(color = IQuiuboColorPallete.LOW_PINK)

        //Spacer(modifier = Modifier.height(10.dp))
    }
}



//@Composable private fun AlfabeticLazyItem(
//    manualInterpretationVM: ManualInterpretationVM,
//    list: AlphabetBiomarker,
//) {
//
//    Column (
//        horizontalAlignment = Alignment.Start
//    ) {
//
//        list.biomarkerList.forEachIndexed { index, bioMarker ->
//            ItemBiomarkerLazyColumn(
//                index = index,
//                lastIndex = list.biomarkerList.size - 1,
//                onlyOne = list.biomarkerList.size == 1,
//                bioMarker = bioMarker,
//                manualInterpretationVM = manualInterpretationVM,
//                list = list
//            )
//        }
//    }
//}



@Composable private fun ItemBiomarkerLazyColumn(
    index: Int,
    lastIndex: Int,
    onlyOne: Boolean,
    bioMarker: BioMarkerUI,
    manualInterpretationVM: ManualInterpretationVM,
    indexAlphabetic: Int,
    list: List<AlphabetBiomarker>
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

    val customColorButton = if (index % 2 != 0) IQuiuboColorPallete.accentSmooth
    else IQuiuboColorPallete.accentSmooth.copy(0.6f)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(
                if (!bioMarker.selected) Color.White else Color(0xFFD5FFD4).copy(alpha = 0.3f),
                customBorders
            )
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
                fontSize = ResponsiveText.caption(),
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
                manualInterpretationVM.selectBioMarker(bioMarker.bioMarker)
                // Una vez el user selecciona un biomarcador
                // se limpia el texto, para que vuelva a entrar uno.
                // manualInterpretationVM.cleanText()
            }
        ) {

            Icon(
                imageVector = if (!bioMarker.selected) Icons.Default.Add else Icons.Default.Check,
                contentDescription = "add_ic",
                tint = if (!bioMarker.selected) Color(0xFFD4A500).copy(green = 0.5f) else Color(
                    0xFF089400
                ),
                modifier = Modifier.size(spacing.lg)
            )
        }
    }

    // Se utiliza para evitar que se ponga otro spacer, cuando la
    // lista alphabeticList, ya esta en su última posición.
    if (lastIndex == index && indexAlphabetic < list.size - 1)
        Spacer(modifier = Modifier.height(spacing.md))
}



@Composable private fun ThirdSection(
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        SubtitleAndIconNumber(manualInterpretationVM)

        Spacer(modifier = Modifier.height(spacing.md))

        FlowRowSelectedItemsThirdSection(manualInterpretationVM)
    }
}



@Composable private fun SubtitleAndIconNumber(
    manualInterpretationVM: ManualInterpretationVM
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Bottom
    ) {

        SubtitleThirdSection(manualInterpretationVM)
        
        Spacer(modifier = Modifier.weight(1f))

        IconNumberThirdSection(manualInterpretationVM)
    }
}



@Composable private fun SubtitleThirdSection(
    manualInterpretationVM: ManualInterpretationVM
) {

    val selectedExamList by manualInterpretationVM.selectedExams.collectAsStateWithLifecycle()
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
    manualInterpretationVM: ManualInterpretationVM
) {

    val selectedExamList by manualInterpretationVM.selectedExams.collectAsStateWithLifecycle()
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
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current
    val selectedList by manualInterpretationVM.selectedExams.collectAsStateWithLifecycle()

    FlowRow (
        verticalArrangement = Arrangement.spacedBy(spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(spacing.sm)
    ) {
    
        selectedList.forEach {
            ItemFlowRowThirdSection(it, manualInterpretationVM)
        }
    }

    if (selectedList.isNotEmpty())
        Spacer(Modifier.height(spacing.md))
}



@Composable private fun ItemFlowRowThirdSection(
    bioMarker: BioMarkerUI,
    manualInterpretationVM: ManualInterpretationVM
) {

    val spacing = LocalSpacing.current

    Row (
        modifier = Modifier
            .dashedBorder(1.dp, IQuiuboColorPallete.accentMiddle)
            .background(IQuiuboColorPallete.accentSmooth, RoundedCornerShape(10.dp))
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
                .clickable { manualInterpretationVM.selectBioMarker(bioMarker.bioMarker) },
            tint = Color.Black
        )
    }
}
