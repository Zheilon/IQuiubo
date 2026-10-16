package com.zhei.iquiubo.features.show_priority_results.presentation

import com.zhei.iquiubo.features.put_rna_values.domain.model.PriorityInputModel

sealed interface ShowPriorityUiState {

    data object Idle: ShowPriorityUiState

    data object Loading: ShowPriorityUiState

    data object Success: ShowPriorityUiState

    data object Error: ShowPriorityUiState
}