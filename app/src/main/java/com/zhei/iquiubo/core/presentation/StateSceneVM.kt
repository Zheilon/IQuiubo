package com.zhei.iquiubo.core.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.iquiubo.core.data.local.dao.StateSceneDao
import com.zhei.iquiubo.core.data.local.entity.StateSceneEntity
import com.zhei.iquiubo.core.navigation.DataBaseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StateSceneVM @Inject constructor(
    private val stateDao: StateSceneDao
) : ViewModel() {



    private val _personalInfoScene = MutableStateFlow(true)
    val personalInfoScene: StateFlow<Boolean> = _personalInfoScene.asStateFlow()


    // Este init!!!! es super importante bro, ya que me carga los estados
    // de la db, y los pone en estas variables.
    //
    // Ojo, como estas variables interactuan con la db lo correcto es
    // realizar esta operación de carga de datos en el propio VM, con es
    // recomendado hacerlo en MainActity, estos valores interactuan se
    // encargan de almacenar los valores de la bd
    //
    // Y me super sirve porque la instancia de StateSceneVM, la inicializo en la
    // clase MainActivity, desde esta inicialización desde este alcanze tan
    // superior permite a este sobrevivir a las recomposiciones.
    init { loadRegisters() }


    /**
     * Carga registros de la tabla state_scenes, utilizando
     * la enum class [DataBaseStates], el async {} se utiliza con
     * funciones suspend.
     * */
    private fun loadRegisters() {
        viewModelScope.launch {
            DataBaseStates.entries.map { async { getStateByName(it.scene) } }.awaitAll()
        }
    }



    /**
     * Cambia los estados de los roles dependiendo
     * el state name ingresado.
     * */
    fun updateState (stateName: String, bool: Boolean) {
        when (stateName) {
            DataBaseStates.PERSONAL_INFO.scene -> { _personalInfoScene.value = bool }
        }
    }



    /**
     * Función encargada de recolectar la respuesta del
     * dao, para traer el estado de la escena con el
     * nombre, el parámetro [stateName] vendrá del enum class [DataBaseStates].
     * */
    private suspend fun getStateByName (stateName: String) {
        val entity = stateDao.getStateByName(stateName)
        val state = if (entity == null) {
            stateDao.insertState(StateSceneEntity(name = stateName))
            true
        } else { entity.state }

        updateState(stateName, state)
    }



    /**
     * Actualiza el parametro [state: Boolean] de la entidad
     * scene_state, según el nombre del rol, encontrado
     * en el enum class [DataBaseStates].
     *
     * @param stateName aquí estará el nombre del estado el
     * cual provendrá de la clase enum (DataBaseStates)
     * */
    fun updateStateByNameInRoom (stateName: String, newState: Boolean) {
        viewModelScope.launch {
            stateDao.updateStateByName(stateName, newState)
        }
    }
}