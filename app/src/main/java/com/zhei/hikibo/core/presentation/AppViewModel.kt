package com.zhei.hikibo.core.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.hikibo.core.domain.repository.AuthRepository
import com.zhei.hikibo.core.domain.repository.UserRepository
import com.zhei.hikibo.core.domain.usecases.GetCurrentUserUIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * Viewmodel encargado de realizar operaciones de arranque
 * */
@HiltViewModel
class AppViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUIDUseCase,
    private val userRepo: UserRepository,
    private val authRepo: AuthRepository,
): ViewModel() {


    @OptIn(ExperimentalCoroutinesApi::class)
    val reactiveLogin: StateFlow<Boolean?> =

        // fuente viva: reacciona a login/logout
        authRepo.observeAuthState()
            .flatMapLatest { user ->
                if (user == null) {
                    // sin sesión → false
                    flowOf(false)

                } else {
                    // Fuente viva reacciona al doc de firestore.
                    userRepo.observeUser(user.uid).map { it?.policyAccepted == true } // true solo si aceptó política
                }
            }
            .catch { emit(false) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)


}