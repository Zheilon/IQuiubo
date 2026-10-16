package com.zhei.iquiubo.features.home.presentation
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.iquiubo.core.domain.model.User
import com.zhei.iquiubo.core.domain.usecases.GetCurrentUserUIDUseCase
import com.zhei.iquiubo.core.domain.usecases.ObserveUserUseCase
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.features.home.domain.model.LastExam
import com.zhei.iquiubo.features.home.domain.model.RiskBuild
import com.zhei.iquiubo.features.home.domain.model.RiskGradient
import com.zhei.iquiubo.features.home.domain.usecase.CalculateRiskValuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class HomeVM @Inject constructor(
    private val observeUser: ObserveUserUseCase,
    private val userId: GetCurrentUserUIDUseCase,
    private val calculateRiskValuesUseCase: CalculateRiskValuesUseCase
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _riskBuildList = MutableStateFlow<List<RiskBuild>>(emptyList())
    val riskBuildList: StateFlow<List<RiskBuild>> = _riskBuildList.asStateFlow()

    private val _navEvents = Channel<Scenes>(Channel.BUFFERED)
    val navEvents = _navEvents.receiveAsFlow()

    val homeUiState: StateFlow<HomeUiState> = flow {
        val id = userId() ?: throw IllegalStateException("Sin sesion")
        emitAll(
            observeUser(id).map { user ->
                _user.value = user
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



    fun getRiskValues() {
        viewModelScope.launch {
            if (_user.value != null) {
                val id = _user.value!!.userId
                _riskBuildList.value = calculateRiskValuesUseCase(id)
            }
        }
    }



    fun navigateTo(scene: Scenes) {
        viewModelScope.launch {
            _navEvents.trySend(scene)
        }
    }
}