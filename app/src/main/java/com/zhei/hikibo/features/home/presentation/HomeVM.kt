package com.zhei.hikibo.features.home.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.hikibo.core.domain.model.User
import com.zhei.hikibo.core.domain.usecases.GetCurrentUserUIDUseCase
import com.zhei.hikibo.core.domain.usecases.GetRemoteUserUseCase
import com.zhei.hikibo.core.domain.usecases.ObserveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(
    private val observeUser: ObserveUserUseCase,
    private val userId: GetCurrentUserUIDUseCase
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    val homeUiState: StateFlow<HomeUiState> = flow {
        val id = userId() ?: throw IllegalStateException("Sin sesion")
        emitAll(
            observeUser(id).map { user ->
                if (user == null) HomeUiState.Loading
                else HomeUiState.Success(user)
            }
        )

    }.catch {
        emit(HomeUiState.Error("No pudimos cargar tu perfil"))

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeUiState.Loading
    )



    fun getGreeting(hour: Int = LocalTime.now().hour): String = when (hour) {
        in 5 .. 11 -> "¡Buenos días!"
        in 12..17 -> "¡Buenas tardes!"
        else -> "¡Buenas noches!"
    }

}