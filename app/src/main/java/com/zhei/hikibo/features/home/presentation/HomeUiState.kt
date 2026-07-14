package com.zhei.hikibo.features.home.presentation
import com.zhei.hikibo.core.domain.model.User

sealed interface HomeUiState {

    data object Default: HomeUiState
    data object Loading: HomeUiState
    data class Error(val message: String): HomeUiState
    data class Success(val user: User): HomeUiState

}