package com.zhei.iquiubo.features.show_priority_results.domain.models

data class RecomendationsAreaUIModel(
    val title: String,
    val objective: String,
    val recomendations: List<String>,
    val uiMessage: String
)
