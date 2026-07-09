package com.zhei.hikibo.core.domain.model

import com.google.firebase.Timestamp

data class Measurement(
    val dateId: Timestamp = Timestamp.now(),
    val bioMarkers: Map<String, Double> = emptyMap(),
    val interpretation: String = ""
)
