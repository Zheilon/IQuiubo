package com.zhei.iquiubo.features.monitoring.presentation
import androidx.lifecycle.ViewModel
import com.zhei.iquiubo.core.common.normalizeForSort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MonitoringVM @Inject constructor(): ViewModel() {

    private val bdList = listOf("Glucosa en ayunas", "Glucosa Postprandial", "Hemoglobina glicosilada (HbA1c)")

    /**
     * Data class encargada de gestionar el biomarcador seleccionado.
     * */
    data class BiomarkerSelection(
        val biomarker: String,
        val value: Double,
        val selected: Boolean = false
    )

    private val _activeBiomarkers = MutableStateFlow<List<BiomarkerSelection>>(emptyList())
    val activeBiomarkers: StateFlow<List<BiomarkerSelection>> = _activeBiomarkers.asStateFlow()

    private val _biomarkerSelected = MutableStateFlow<BiomarkerSelection?>(null)
    val biomarkerSelected: StateFlow<BiomarkerSelection?> = _biomarkerSelected

    init {
        prepareData()
    }



    /**
     * Organiza alfabéticamente la lista entrante de biomarcadores, de la base de
     * datos de FireStore.
     * */
    private fun prepareData() {

        // Esta parte se traen los biomarcadores disponibles de
        // la base de datos para luego mappearlos a la clase BiomarkerSelection.
        val tempList = bdList.map { BiomarkerSelection(it, 97.0) }

        // Ordenamiento alfabético descendente.
        _activeBiomarkers.value = tempList.sortedByDescending {
            it.biomarker.first().toString().normalizeForSort()
        }
    }



    /**
     * Se encarga de realizar la siguientes acciones:
     *
     * 1. Determina que elemento de seleccionarse, siendo la
     * (key) de selección el nombre del biomarcador.
     *
     * 2. Particiona la lista en dos, la primera es la lista que
     * contiene el elemento seleccionado y la segunda lista contiene
     * los elementos cuyo atributo (selected) es igual a (false).
     *
     * 3. Posteriormente se realiza una union de estas listas poniendo
     * de primeras la lista con el elemento seleccionado.
     * */
    fun selectDataAndRelocate(nameBiomarker: String) {

        _activeBiomarkers.update { biolist ->

            // Para determinar que biomarcador dejar como selected = true o false
            // se realiza una comparación directamente en el atributo selected, comparando
            // el biomarker entrante con el dado por el (.map {}).
            val updatedList = biolist.map {
                it.copy(selected = nameBiomarker == it.biomarker)
            }

            val selected = updatedList.find { it.selected }
            _biomarkerSelected.value = selected

            // Selecciono todos los que no son (selected = true)     y aplico otro
            // ordenamiento alfabético descendente.
            val others = updatedList.filter { !it.selected }.sortedByDescending {
                it.biomarker.first().toString().normalizeForSort()
            }

            if (selected != null) listOf(selected) + others else biolist
        }
    }
}