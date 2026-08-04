package com.zhei.iquiubo.features.home.domain.model

data class LastExam(
    val exam: String,
    val value: Float,
    val unit: String,
    val threshold: RiskThreshold.Template
)
