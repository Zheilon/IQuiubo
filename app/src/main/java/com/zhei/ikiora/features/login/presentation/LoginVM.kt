package com.zhei.ikiora.features.login.presentation
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginVM @Inject constructor() : ViewModel() {

    private val _isLogged = mutableStateOf(false)
    val isLogged: State<Boolean> = _isLogged

    private val _acceptPolicy = mutableStateOf<Boolean?>(null)
    val acceptPolicy: State<Boolean?> = _acceptPolicy

    // Variable encargada de cuando mostrar la politica de privacidad.
    private val _showPolicity = mutableStateOf(false)
    val showPolicity: State<Boolean> = _showPolicity


    /**
     * Cuando oprima el (Check Box) va a cambiar de false a true
     * Y de manera inversa cuando vuelva a oprimir una segunda vez.
     * */
    fun setCheckBox() {

        // En el primer llamado de la función, se le asignará un (true) si
        // la variable es (null), ¡Y se corta la función!.
        if (_acceptPolicy.value == null) {
            _acceptPolicy.value = true
            return
        }

        // Para cuando la variable sea (true) entonces a volver a (null)
        // con lo cual no se va a mostrar marcación interna en el
        // checkbox.
        _acceptPolicy.value = null
    }


    /**
     * Esta función se va a utilizar cuando el area de mostrar la
     * política de privacidad, se de aceptar pues aquí directamente se pondra en (true)
     * la variable
     * */
    fun setTrueInCheckBox() { _acceptPolicy.value = true }


    /**
     * Se encarga de cambiar el valor booleano de _showPolicity. Cuando
     * esta sea (true) mostrara las políticas de privacidad en un menú
     * emergente.
     * */
    fun setShowPolicity() { _showPolicity.value = !_showPolicity.value }


    /**
     * Se usa exclusivamente para ocultar la política de tratamiento de
     * datos cuando el user realiza el gesto de volver a la pantalla anterior.
     * */
    fun hidePolicy() { _showPolicity.value = false }


    /**
     * Esta función se encarga de validar que todas las condiciones
     * para continuar el flujo de la aplicación sean cumplidas
     * */
    fun allConditionsAreTrue() =
        (_acceptPolicy.value ?: false) && _isLogged.value
}