package com.zhei.hikibo.features.entry_interpretation.features.manual_entry_interpretation.presentation
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.hikibo.core.common.normalizeForSort
import com.zhei.hikibo.core.domain.model.BioMarker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ManualInterpretationVM @Inject constructor(): ViewModel() {

    private val _examsList = MutableStateFlow<List<AlphabetBiomarker>>(emptyList())
    val examsList: StateFlow<List<AlphabetBiomarker>> = _examsList.asStateFlow()

    private val _text = MutableStateFlow("")
    val exam: StateFlow<String> = _text.asStateFlow()

    private val _selectedExams = MutableStateFlow<List<BioMarkerUI>>(emptyList())
    val selectedExams: StateFlow<List<BioMarkerUI>> = _selectedExams.asStateFlow()


    val filteredList: StateFlow<List<AlphabetBiomarker>> = combine(_examsList, _text) { list, search ->
        list.map { alfabetic ->
            AlphabetBiomarker(
                word = alfabetic.word,
                biomarkerList = alfabetic.biomarkerList.filter {
                    it.bioMarker.displayName.contains(search.trim(), ignoreCase = true)
                            || it.bioMarker.key.contains(search.trim(), ignoreCase = true)
                }
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = emptyList())



    init {
        getBiomarkers()
        Log.e("BIOMARKERRR", _examsList.value.toString())
    }



    /**
     * Se encarga de llenar la variable con los biomacadores tipo enum.
     * Esta función se llama por primera vez en el init para poder llenar esta variable
     * */
    private fun getBiomarkers() {
        _examsList.value = BioMarker.entries
            .sortedBy { it.displayName.normalizeForSort() }
            .groupBy { it.displayName.normalizeForSort().first().uppercase() }
            .map { (key, list) ->
                AlphabetBiomarker(
                    word = key,
                    biomarkerList = list.map { BioMarkerUI(it) }
                )
            }
        Log.e("BIOMARKERS LEN", "Tamaño: ${BioMarker.entries.size}")
    }



    fun updateExamField(value: String) {
        _text.value = value
    }



    fun cleanText() {
        _text.value = ""
    }



    fun selectBioMarker(bioMarker: BioMarker) {
        _examsList.update { alphabetList ->
            alphabetList.map { group ->
                group.copy(
                    biomarkerList = group.biomarkerList.map { biomarkerUI ->
                        if (biomarkerUI.bioMarker == bioMarker) {
                            biomarkerUI.copy(selected = !biomarkerUI.selected)
                        } else { biomarkerUI }
                    }
                )
            }
        }

        // Pongo esta función para que se vaya actualizando
        // tambien la lista de elementos seleccionados.
        addToBiomarkersSelected()
    }



    /**
     * Esta función es utilizada para poder obtener los examenes
     * seleccionados y almacenarlos en la variable _selectedExams.
     * Se realiza un filtrado, tomando como condición, que unicamente
     * se van a tomar los que tengan el atributo selected en (true)
     * */
    private fun addToBiomarkersSelected() {
        _selectedExams.value = _examsList.value.flatMap { it.biomarkerList }.filter { it.selected }
    }

}