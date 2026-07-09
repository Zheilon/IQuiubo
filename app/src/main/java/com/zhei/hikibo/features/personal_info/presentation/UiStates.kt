package com.zhei.hikibo.features.personal_info.presentation


sealed interface PersonalInfoUiState {

    data object Idle: PersonalInfoUiState
    data object Loading: PersonalInfoUiState

    data object NameSuccess: PersonalInfoUiState
    data class NameError(val message: String): PersonalInfoUiState

    data object FirstLastNameSuccess: PersonalInfoUiState
    data class FirstLastNameError(val message: String): PersonalInfoUiState

    data object DateSuccess: PersonalInfoUiState
    data class DateError(val message: String): PersonalInfoUiState

    data object SexSuccess: PersonalInfoUiState
    data class SexError(val message: String): PersonalInfoUiState

    data object OnButtonSendSuccess: PersonalInfoUiState
    data class OnButtonSendError(val message: String): PersonalInfoUiState

}