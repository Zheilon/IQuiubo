package com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.features.fillingInToInterpretation.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.features.entry_interpretation.features.manual_entry_interpretation.presentation.BioMarkerUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class FillingInToInterpVM @Inject constructor(): ViewModel() {


    private val _info = MutableStateFlow<List<FillingDataUI>>(mutableListOf())
    val info: StateFlow<List<FillingDataUI>> = _info.asStateFlow()

    private val _navigation = Channel<Scenes>(Channel.BUFFERED)
    val navigation = _navigation.receiveAsFlow()


    // Se flow se encarga de retornar un
    val missingTextfields: StateFlow<Pair<Int, Float>> = _info.map {
        if (it.isEmpty()) return@map Pair(0, 0.0f)
        val completed = it.count { data ->
            val number = data.value.toDoubleOrNull()
            data.value.isNotEmpty() && number != null && number > 0
        }
        Pair(completed, completed.toFloat() / it.size)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Pair(0, 0.0f)
    )


    // StateFlow utilizo para determinar si se cumplen todas
    // las condiciones para realizar la inferencia.
    val canContinue: StateFlow<Boolean> = _info.map { list ->
        list.isNotEmpty() && list.all {
            val number = it.value.toDoubleOrNull()
            it.value.isNotEmpty() && number != null && number > 0
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )


    fun doFill(names: List<BioMarkerUI>) {

        val incoming = names.associateBy { it.bioMarker.key }

        _info.update { current ->
            val currentMap = current.associateBy { it.examName }

            incoming.map { (_, biomarkerUI) ->
                val bio = biomarkerUI.bioMarker
                currentMap[bio.displayName] ?: FillingDataUI(
                    examName = bio.displayName,
                    unit = bio.unit,
                    icon = bio.icon
                )
            }
        }
    }



    fun updateInfo(info: FillingDataUI, value: String) {

        if (!value.matches(Regex("""^\d*\.?\d*$""")))
            return

        _info.update { list ->
            list.map { data ->
                if (data.examName == info.examName) data.copy(value = value) else data
            }
        }
    }



    fun onButtonToNav(destiny: Scenes) {
        _navigation.trySend(destiny)
    }
}