package com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.features.fillingInToInterpretation.presentation
import androidx.compose.ui.graphics.vector.ImageVector

data class FillingDataUI(
    val examName: String,
    val unit: String,
    val icon: ImageVector,
    val value: String = ""
)
