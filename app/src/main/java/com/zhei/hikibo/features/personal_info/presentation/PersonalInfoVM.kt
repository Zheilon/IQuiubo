package com.zhei.hikibo.features.personal_info.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.core.domain.usecases.GetCurrentUserUseCase
import com.zhei.hikibo.core.framework.NetworkChecker
import com.zhei.hikibo.features.personal_info.domain.usecases.UpdateProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject


@HiltViewModel
class PersonalInfoVM @Inject constructor(
    private val updateUserProfile: UpdateProfileUseCase,
    private val getCurrentUid: GetCurrentUserUseCase,
    private val network: NetworkChecker
): ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _firstLastName = MutableStateFlow("")
    val firstLastName: StateFlow<String> = _firstLastName.asStateFlow()

    private val _day = MutableStateFlow("")
    val day: StateFlow<String> = _day.asStateFlow()

    private val _month = MutableStateFlow("")
    val month: StateFlow<String> = _month.asStateFlow()

    private val _year = MutableStateFlow("")
    val year: StateFlow<String> = _year.asStateFlow()

    private val _selectedSex = MutableStateFlow<String?>(null)
    val selectedSex: StateFlow<String?> = _selectedSex.asStateFlow()

    private val _personalInfoUiState = MutableStateFlow<PersonalInfoUiState>(PersonalInfoUiState.Idle)
    val personalInfoUiState: StateFlow<PersonalInfoUiState> = _personalInfoUiState.asStateFlow()

    sealed interface NavEvent { data object ToNext: NavEvent }
    private val _navEvents = Channel<NavEvent>(Channel.BUFFERED)
    val navEvents = _navEvents.receiveAsFlow()


    /**
     * Actualiza la variable _name556
     * */
    fun updateFirstName(value: String) {
        _firstName.value = value.trim()

        // Esta sentencia la pongo de esta manera para que
        // cuando halla un error visible, el usuario al momento de
        // escribir, ese error se limpie.

        if (_personalInfoUiState.value is PersonalInfoUiState.NameError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    /**
     * Actualiza la variable _firstLastName
     * */
    fun updateFirstLastName(value: String) {
        _firstLastName.value = value.trim()

        if (_personalInfoUiState.value is PersonalInfoUiState.FirstLastNameError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    /**
     * Actualiza la variable _day
     * */
    fun updateDay(value: String) {
        if (value.length < 3) {
            _day.value = value
        }

        if (_personalInfoUiState.value is PersonalInfoUiState.DateError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    /**
     * Actualiza la variable _month
     * */
    fun updateMonth(value: String) {
        if (value.length < 3) {
            _month.value = value
        }

        if (_personalInfoUiState.value is PersonalInfoUiState.DateError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    /**
     * Actualiza la variable _year
     * */
    fun updateYear(value: String) {
        if (value.length < 5) {
            _year.value = value
        }

        if (_personalInfoUiState.value is PersonalInfoUiState.DateError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    fun setSelectedSex(sex: String) {
        _selectedSex.value = sex

        if (_personalInfoUiState.value is PersonalInfoUiState.SexError)
            updatePersonalInfoUiState(PersonalInfoUiState.Idle)
    }



    /**
     * Actualiza la variable _personalInfoUiState a
     * el estado que venga del parámetro.
     * */
    private fun updatePersonalInfoUiState(state: PersonalInfoUiState) {
        _personalInfoUiState.value = state
    }



    /**
     * Valida que todas las condiciones se cumplan:
     * 1). Que el apartado del nombre este lleno.
     * 2). Que la edad este llena y sea mayor a cero
     * */
    private fun validateAndSetErrors(): Boolean {

        // Validación de condición del nombre

        if (_firstName.value.isEmpty()) {
            updatePersonalInfoUiState(
                PersonalInfoUiState.NameError(
                    "El campo del nombre no debe estar vacio."
                )
            )
            return false
        }

        // Validación del primer apellido

        if (_firstLastName.value.isEmpty()) {
            updatePersonalInfoUiState(
                PersonalInfoUiState.FirstLastNameError(
                    "Debes llenar el campo del primer apellido."
                )
            )
            return false
        }

        // Area de validación de que la fecha este en condiciones
        // de ser usable.

        val d = _day.value.toIntOrNull()
        val m = _month.value.toIntOrNull()
        val y = _year.value.toIntOrNull()

        val birthDate = try {
            if (d == null || m == null || y == null) null
            else LocalDate.of(y, m, d) // lanza si es 31/02, mes 13, etc.
        } catch (e: java.time.DateTimeException) { null }

        if (birthDate == null) {
            updatePersonalInfoUiState(
                PersonalInfoUiState.DateError(
                    "Fecha inválida."
                )
            )
            return false
        }

        if (birthDate.isAfter(LocalDate.now())) { // no futura
            updatePersonalInfoUiState(
                PersonalInfoUiState.DateError(
                    "La fecha no puede ser futura."
                )
            )
            return false
        }

        val age = Period.between(birthDate, LocalDate.now()).years
        if (age < 1 || age > 120) { // rango sano
            updatePersonalInfoUiState(
                PersonalInfoUiState.DateError(
                    "Edad fuera de rango."
                )
            )
            return false
        }


        // Validar si el el user ya marcó la casilla de sexo
        if (_selectedSex.value == null) {
            updatePersonalInfoUiState(
                PersonalInfoUiState.SexError(
                    "Falta que selecciones tu sexo"
                )
            )
            return false
        }

        // Si no hay internet, no envio formulario de información personal.
        if (!network.isOnline()) {
            updatePersonalInfoUiState(
                PersonalInfoUiState.OnButtonSendError(
                    "Sin conexión. Revisa tu internet e intenta de nuevo."
                )
            )
            return false
        }

        // Retorna (true) si todas las condiciones son verdaderas
        return true
    }



    fun readyToNav() {

        if (!validateAndSetErrors())
            return

        viewModelScope.launch {

            // Comienzo de estado de carga.
            updatePersonalInfoUiState(PersonalInfoUiState.Loading)

            // Hago un casteo directo con .toInt() pues en la función
            // validateAndSetErrors() ya se descarta que las fechas esten mal.

            val d = _day.value.toInt()
            val m = _month.value.toInt()
            val y = _year.value.toInt()

            val user = FirestoreUser(
                userId = getCurrentUid() ?: "",
                firstName = _firstName.value,
                firstLastName = _firstLastName.value,
                birthDay = LocalDate.of(y, m, d).toString(),
                sex = _selectedSex.value ?: "Unspecified"
            )

            runCatching {
                updateUserProfile(user)

            }.onSuccess {
                updatePersonalInfoUiState(PersonalInfoUiState.OnButtonSendSuccess)
                _navEvents.trySend(NavEvent.ToNext)

            }.onFailure {
                val msg = when(it) {
                    is FirebaseNetworkException ->
                        "Sin conexión. Revisa tu internet e intenta de nuevo."

                    is FirebaseAuthUserCollisionException ->
                        "Ya existe una cuenta con ese correo."

                    is FirebaseAuthInvalidCredentialsException ->
                        "Credenciales inválidas. Vuelve a intentarlo."
                    else -> "No se pudo iniciar sesión. Intenta más tarde."
                }

                updatePersonalInfoUiState(PersonalInfoUiState.OnButtonSendError(msg))
            }
        }


    }
}