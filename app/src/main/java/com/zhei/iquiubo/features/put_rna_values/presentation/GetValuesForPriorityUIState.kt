package com.zhei.iquiubo.features.put_rna_values.presentation

sealed interface GetValuesForPriorityUIState {

    data object Default: GetValuesForPriorityUIState

    data class FastingError(val message: String): GetValuesForPriorityUIState
    data class PostprandialError(val message: String): GetValuesForPriorityUIState
}