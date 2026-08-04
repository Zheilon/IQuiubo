package com.zhei.iquiubo.core.data.model
import com.google.firebase.Timestamp

data class FirestoreMeasurement(
    val measurementId: String = "",
    val date: Timestamp = Timestamp.now(),
    val bioMarkers: Map<String, Double> = emptyMap(),
    val interpretation: String = "",
    val riskLevel: String = ""
)
