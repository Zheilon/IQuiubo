package com.zhei.hikibo.features.login.presentation
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.zhei.hikibo.core.framework.NetworkChecker
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.features.login.domain.usecase.AddFirebaseUserUseCase
import com.zhei.hikibo.features.login.domain.usecase.CreateEmailUserUserCase
import com.zhei.hikibo.features.login.domain.usecase.SignInWithEmailUseCase
import com.zhei.hikibo.features.login.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class LoginVM @Inject constructor(
    private val signInWithGoogle: SignInWithGoogleUseCase,
    private val signInWithEmail: SignInWithEmailUseCase,
    private val createUserWithEmail: CreateEmailUserUserCase,
    private val addUserToFirestore: AddFirebaseUserUseCase,
    private val network: NetworkChecker
) : ViewModel() {

    private val _isLoggedWithGoogle = mutableStateOf(false)
    val isLoggedWithGoogle: State<Boolean> = _isLoggedWithGoogle

    private val _isLoggedWithEmail = mutableStateOf(false)
    val isLoggedWithEmail: State<Boolean> = _isLoggedWithEmail

    private val _acceptPolicy = mutableStateOf<Boolean?>(null)
    val acceptPolicy: State<Boolean?> = _acceptPolicy

    // Variable encargada de cuando mostrar la politica de privacidad.
    private val _showPolicity = mutableStateOf(false)
    val showPolicity: State<Boolean> = _showPolicity

    // Esta variable almacenará la frase de valor que va a tener efecto
    // de typing.
    private val _valuePhrase = mutableStateOf("")
    val valuePhrase: State<String> = _valuePhrase

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    private val _googleUiState = MutableStateFlow<GoogleUiState>(GoogleUiState.Idle)
    val googleUiState: StateFlow<GoogleUiState> = _googleUiState.asStateFlow()

    private val _emailUiState = MutableStateFlow<EmailUiState>(EmailUiState.Idle)
    val emailUiState: StateFlow<EmailUiState> = _emailUiState.asStateFlow()

    private val _firestoreUiState = MutableStateFlow<FirestoreUiState>(FirestoreUiState.Idle)
    val firestoreUiState: StateFlow<FirestoreUiState> = _firestoreUiState.asStateFlow()

    private val _emailText = MutableStateFlow("")
    val emailText: StateFlow<String> = _emailText.asStateFlow()

    private val _passwordText = MutableStateFlow("")
    val passwordText: StateFlow<String> = _passwordText.asStateFlow()


    // Creación de un channel para emitir un evento desde
    // el viewmodel, el cual este se ejecute más adelante en UI.
    sealed interface LoginNavEvent { data object ToNext: LoginNavEvent }

    private val _navEvents = Channel<LoginNavEvent>(Channel.BUFFERED)
    val navEvents = _navEvents.receiveAsFlow()

    private val _userLogin = MutableStateFlow(FirestoreUser())


    fun lazyInitialization() {
        startTypingEffect()
    }



    /**
     * Cuando oprima el (Check Box) va a cambiar de false a true
     * Y de manera inversa cuando vuelva a oprimir una segunda vez.
     * */
    fun setCheckBox() {

        // En el primer llamado de la función, se le asignará un (true) si
        // la variable es (null), ¡Y se corta la función!.
        if (_acceptPolicy.value == null) {
            _acceptPolicy.value = true
            _userLogin.update { it.copy(policyAccepted = true) }
            return
        }

        // Para cuando la variable sea (true) entonces a volver a (null)
        // con lo cual no se va a mostrar marcación interna en el
        // checkbox.
        _acceptPolicy.value = null
        _userLogin.update { it.copy(policyAccepted = false) }
    }


    /**
     * Esta función se va a utilizar cuando el area de mostrar la
     * política de privacidad, se de aceptar pues aquí directamente se pondra en (true)
     * la variable
     * */
    fun setTrueInCheckBox() {
        _acceptPolicy.value = true
        _userLogin.update { it.copy(policyAccepted = true) }
    }


    /**
     * Se encarga de cambiar el valor booleano de _showPolicity. Cuando
     * esta sea (true) mostrara las políticas de privacidad en un menú
     * emergente.
     * */
    fun setShowPolicity() {
        _showPolicity.value = !_showPolicity.value
    }



    /**
     * Se usa exclusivamente para ocultar la política de tratamiento de
     * datos cuando el user realiza el gesto de volver a la pantalla anterior.
     * */
    fun hidePolicy() {
        _showPolicity.value = false
    }



     /**
      * Cambia el valor dentro de la variable _emailText
      * */
    fun updateEmailText(value: String) {
        _emailText.value = value.trim().lowercase()
    }



    /**
     * Cambia el valor dentro de la variable _passwordText
     * */
    fun updatePasswordText(value: String) {
        _passwordText.value = value.trim()
    }



    /**
     * Actualiza el tipo de estado ubicado dentro
     * de la variable _googleUiState
     * */
    fun updateGoogleUiState(state: GoogleUiState) {
        _googleUiState.value = state
    }


    /**
     * Actualiza el tipo de estado ubicado dentro
     * de la variable _emailUiState
     * */
    fun updateEmailUiState(state: EmailUiState) {
        _emailUiState.value = state
    }



    /**
     * Actualiza el tipo de estado ubicado dentro
     * de la variable _firestoreUiState
     * */
    fun updateFirestoreUiState(state: FirestoreUiState) {
        _firestoreUiState.value = state
    }



    /**
     * Limpia simultaneamente las variables _emailText, _passwordText.
     * Se ejecutará esta acción cuando se cierre el ModalBottomSheet
     * */
    fun clearEmailVars() {
        _emailText.value = ""
        _passwordText.value = ""
    }


    /**
     * Esta función se encarga de validar que todas las condiciones
     * para continuar el flujo de la aplicación sean cumplidas
     *
     * Condiciones:
     * 1. Debe estar loggeado con Google (o) con algún Email.
     * 2. Debe aceptar las políticas de tratamiento de datos.
     * */
    fun allConditionsAreTrue() =
        (_acceptPolicy.value ?: false) && (_isLoggedWithGoogle.value || _isLoggedWithEmail.value)



    /** Punto de entrada único: recibe el resultado
     *  del helper y decide qué hacer.
     *  */
    fun onGoogleButton(result: GoogleSignInResult) {

        viewModelScope.launch {

            when (result) {

                is GoogleSignInResult.Canceled -> {
                    updateGoogleUiState(GoogleUiState.Idle)
                }

                is GoogleSignInResult.Error -> {
                    Log.e("LOGIN VM", "NameError de credencial", result.cause)
                    updateGoogleUiState(GoogleUiState.Error(result.userMessage))
                }

                is GoogleSignInResult.Success -> {
                    exchangeGoogleWithFirebase(result.idToken)
                }
            }
        }
    }



    private fun exchangeGoogleWithFirebase(idToken: String) {

        viewModelScope.launch {
            updateGoogleUiState(GoogleUiState.Loading)

            runCatching {
                signInWithGoogle(idToken)

            }.onSuccess {
                _userLogin.update { userLogIn ->
                    userLogIn.copy(
                        userId = it.uid,
                        displayName = "",
                        email = it.email ?: "",
                        emailVerified = it.emailVerified
                    )
                }

                _isLoggedWithGoogle.value = true
                updateGoogleUiState(GoogleUiState.Success)

            }.onFailure {
                Log.e("LoginVM", "Login con Google (Firebase) falló", it)

                val msg = when (it) {
                    is FirebaseNetworkException ->
                        "Sin conexión. Revisa tu internet e intenta de nuevo."

                    is FirebaseAuthInvalidCredentialsException ->
                        "Credenciales inválidas. Vuelve a intentarlo."

                    is FirebaseAuthUserCollisionException ->
                        "Ya existe una cuenta con ese correo."

                    else ->
                        "No se pudo iniciar sesión. Intenta más tarde."
                }

                updateGoogleUiState(GoogleUiState.Error(msg))
            }
        }
    }



    fun onEmailSignInButton() {

        viewModelScope.launch {
            updateEmailUiState(EmailUiState.Loading)

            runCatching {
                signInWithEmail(_emailText.value, _passwordText.value)

            }.onSuccess {
                _userLogin.update { userLogIn ->
                    userLogIn.copy(
                        userId = it.uid,
                        displayName = "",
                        email = it.email ?: "",
                        emailVerified = it.emailVerified
                    )
                }
                _isLoggedWithEmail.value = true
                updateEmailUiState(EmailUiState.Success)

            }.onFailure {
                val msg = when(it) {
                    is FirebaseNetworkException ->
                        "Sin conexión. Revisa tu internet e intenta de nuevo."

                    is FirebaseAuthInvalidCredentialsException ->
                        "Credenciales inválidas. Vuelve a intentarlo."

                    is FirebaseAuthUserCollisionException ->
                        "Ya existe una cuenta con ese correo."

                    else ->
                        "No se pudo iniciar sesión. Intenta más tarde."
                }

                updateEmailUiState(EmailUiState.Error(msg))
            }
        }
    }



    fun onEmailSignUpButton() {

        // Se realiza una validación previa respecto a la estructura de
        // dominio del correo electrónico que el user va a usar.

        if (!Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()) {
            updateEmailUiState(
                EmailUiState.Error(
                    "Correo inválido."
                )
            )
            return
        }

        // Realizar validaciones de las condiciones
        // mínimas que debe tener una contraseña para
        // que pueda ser usada.

        if (_passwordText.value.length < 7) {
            updateEmailUiState(
                EmailUiState.PasswordError(
                    "La contraseña debe tener al menos 7 caracteres."
                )
            )
            return
        }

        viewModelScope.launch {
            updateEmailUiState(EmailUiState.Loading)

            runCatching {
                createUserWithEmail(_emailText.value, _passwordText.value)

            }.onSuccess {
                _userLogin.update { userLogIn ->
                    userLogIn.copy(
                        userId = it.uid,
                        displayName = "",
                        email = it.email ?: "",
                        emailVerified = it.emailVerified
                    )
                }
                _isLoggedWithEmail.value = true
                updateEmailUiState(EmailUiState.Success)

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

                updateEmailUiState(EmailUiState.Error(msg))
            }
        }
    }



    fun onNavigateNext() {

        if (!allConditionsAreTrue())
            return

        if (!network.isOnline()) {
            updateFirestoreUiState(
                FirestoreUiState.Error(
                    "Sin conexión. Revisa tu internet e intenta de nuevo."
                )
            )
            return
        }

        viewModelScope.launch {
            updateFirestoreUiState(FirestoreUiState.Loading)

            runCatching {
                withTimeout(8_000) {
                    addUserToFirestore(_userLogin.value)
                }

            }.onSuccess {
                updateFirestoreUiState(FirestoreUiState.Success)
                _navEvents.trySend(LoginNavEvent.ToNext)

            }.onFailure {
                // Rethrow de cancelación real (no la del timeout) — ver nota al final
                if (it is CancellationException && it !is TimeoutCancellationException)
                    throw it

                val msg = when (it) {
                    is TimeoutCancellationException ->
                        "No pudimos confirmar tu consentimiento. Revisa tu conexión e intenta de nuevo."

                    is FirebaseFirestoreException -> when (it.code) {
                        FirebaseFirestoreException.Code.PERMISSION_DENIED -> "No tienes permiso para esta acción."
                        FirebaseFirestoreException.Code.UNAVAILABLE        -> "Servicio no disponible. Intenta más tarde."
                        else -> "No se pudo guardar. Intenta más tarde."
                    }

                    else -> "No se pudo guardar. Intenta más tarde."
                }
                updateFirestoreUiState(FirestoreUiState.Error(msg))
            }
        }
    }



    private fun startTypingEffect() {
        viewModelScope.launch {
            delay(500)
            _isTyping.value = true

            // Escribe bien hasta aquí
            type("Tu camino hacia un")

            type(" co")
            type("mtrsjl")
            delay(350)
            deleteChars(6)
            delay(120)
            // Corrige
            type("ntrol ")

            type("meta")

            // Se equivoca (quería 'b', le dio a la 'v')
            type("v")
            delay(350)

            // Borra el error
            deleteChars(1)
            delay(120)

            // Corrige y termina
            type("bólico inteligente")

            // Un emoji final
            delay(300)
            _valuePhrase.value += " \uD83D\uDE0A"

            // El cursor ya en la parte final desaparece.
            delay(500)
            _isTyping.value = false
        }
    }

    /**
     * Escribe un texto carácter por carácter, con ritmo humano.
     * */
    private suspend fun type(text: String) {
        text.forEach { char ->
            _valuePhrase.value += char
            delay(humanDelay(char))
        }
    }

    /**
     * Borra [count] caracteres, uno por uno (borrar es más rápido que escribir).
     * */
    private suspend fun deleteChars(count: Int) {
        repeat(count) {
            _valuePhrase.value = _valuePhrase.value.dropLast(1)   // backspace
            delay((30L..70L).random())
        }
    }

    /**
     * Pausas largas en puntuación + ligero azar = se siente humano.
     * */
    private fun humanDelay(char: Char): Long = when (char) {
        ',', ';'      -> 300L
        '.', '!', '?' -> 500L
        else          -> (40L..110L).random()
    }
}


