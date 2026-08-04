package com.zhei.iquiubo.core.domain.model
import com.google.firebase.Timestamp
import java.time.Instant

data class Measurement(
    val measurementId: String,
    val date: Instant,
    val bioMarkers: Map<String, Double>,
    val interpretation: String,
    val riskLevel: String
)
