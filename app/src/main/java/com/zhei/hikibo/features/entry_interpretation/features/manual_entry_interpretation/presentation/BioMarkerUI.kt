package com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation
import com.zhei.hikibo.core.domain.model.BioMarker


data class BioMarkerUI(
    val bioMarker: BioMarker,
    val selected: Boolean = false
)
