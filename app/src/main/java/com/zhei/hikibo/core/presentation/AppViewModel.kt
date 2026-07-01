package com.zhei.hikibo.core.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.hikibo.features.login.domain.repository.AuthRepository
import com.zhei.hikibo.features.login.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * Viewmodel encargado de realizar operaciones de arranque
 * */
@HiltViewModel
class AppViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val loginRepo: LoginRepository
): ViewModel() {

    sealed interface StartState {
        data object Loading: StartState
        data object LoggedOut: StartState
        data object NeedsGate: StartState
        data object Ready: StartState // Listo para seguir
    }

    val startState: StateFlow<StartState> = flow {
        val current = repo.currentUser
        if (current == null) { emit(StartState.LoggedOut); return@flow }

        val profile = runCatching { loginRepo.getUser(current.uid) }.getOrNull()
        val done = profile?.policyAccepted == true
        emit(if (done) StartState.Ready else StartState.NeedsGate)

    }.catch {
        emit(StartState.LoggedOut)

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StartState.Loading
    )


    val loginFirst: StartState get() = if (repo.currentUser != null) StartState.Ready
        else StartState.LoggedOut


    val loginStateFlow: StateFlow<StartState> = repo.observeAuthState()
        .map { if (it != null) StartState.Ready else StartState.LoggedOut }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StartState.Loading)
}