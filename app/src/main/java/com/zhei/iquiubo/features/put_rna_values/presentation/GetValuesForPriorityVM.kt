package com.zhei.iquiubo.features.put_rna_values.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.iquiubo.core.common.calculateAge
import com.zhei.iquiubo.core.domain.model.BioMarker
import com.zhei.iquiubo.core.domain.usecases.GetCurrentUserUIDUseCase
import com.zhei.iquiubo.core.domain.usecases.GetRemoteUserUseCase
import com.zhei.iquiubo.core.navigation.Scenes
import com.zhei.iquiubo.features.put_rna_values.domain.shared.SharePriorityData
import com.zhei.iquiubo.features.put_rna_values.domain.model.PriorityInputModel
import com.zhei.iquiubo.features.put_rna_values.domain.usecase.GetBmiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class GetValuesForPriorityVM @Inject constructor(
    private val sharePriorityData: SharePriorityData,
    private val getRemoteUserUseCase: GetRemoteUserUseCase,
    private val getCurrentUserUIDUseCase: GetCurrentUserUIDUseCase,
    private val getBmiUseCase: GetBmiUseCase
): ViewModel() {


    data class ClinicalInfo(
        val exam: String,
        val value: String,
        val unit: String = "mg/dL"
    )

    private val _examList = MutableStateFlow(
        listOf(
            ClinicalInfo(BioMarker.GLUCOSE_FASTING.displayName, ""),
            ClinicalInfo(BioMarker.GLUCOSE_POSTPRANDIAL.displayName, "")
        )
    )
    val examList: StateFlow<List<ClinicalInfo>> = _examList.asStateFlow()

    private val _navEvent = Channel<Scenes>(Channel.BUFFERED)
    val navEvent = _navEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow<GetValuesForPriorityUIState>(GetValuesForPriorityUIState.Default)
    val uiState: StateFlow<GetValuesForPriorityUIState> = _uiState.asStateFlow()



    /**
     * Actualiza el textfield asociado al nombre del examen.
     * */
    fun updateBiomaker(biomarkerName: String, value: String) {

        if (!value.matches(Regex("""^\d*\.?\d*$""")))
            return

        val cond = _uiState.value is GetValuesForPriorityUIState.FastingError ||
                _uiState.value is GetValuesForPriorityUIState.PostprandialError

        if (cond)
            updateState(GetValuesForPriorityUIState.Default)

        _examList.update { list ->
            list.map { if (biomarkerName == it.exam) it.copy(value = value) else it }
        }
    }



    fun updateState(state: GetValuesForPriorityUIState) {
        _uiState.value = state
    }



    /**
     * Función encargada de validar que todos los campos cumplan
     * con las condiciones
     * */
    private fun areOktoSend(): Boolean {

        val fastingState = _examList.value.firstOrNull {
            it.exam == BioMarker.GLUCOSE_FASTING.displayName
        }?.value.orEmpty()

        val postprandialState = _examList.value.firstOrNull {
            it.exam == BioMarker.GLUCOSE_POSTPRANDIAL.displayName
        }?.value.orEmpty()

        if (fastingState.isEmpty()) {
            updateState(
                GetValuesForPriorityUIState.FastingError(
                    "Ingresa el valor en el campo de [ Glucosa en ayunas ]"
                )
            )
            return false
        }

        if (postprandialState.isEmpty()) {
            updateState(
                GetValuesForPriorityUIState.PostprandialError(
                    "Ingresa el valor en el campo de [ Glucosa postprandial ]"
                )
            )
            return false
        }

        return true
    }



    /**
     * Función encargada de navegar entre pantallas
     * */
    fun navigateTo(scene: Scenes) {
        _navEvent.trySend(scene)
    }



    /**
     * Función encargada de migrar a la pantalla de obtención de prioridad
     * */
    fun nextInterface(scene: Scenes) {

        viewModelScope.launch {

            if (!areOktoSend())
                return@launch

            // Aquí antes habia el metodo run {}, al final de cada (?:)
            val uid = getCurrentUserUIDUseCase() ?: return@launch
            val user = getRemoteUserUseCase(uid) ?: return@launch

            val model = PriorityInputModel(
                activityLevel = user.activityLevel,
                age = LocalDate.parse(user.birthDay).calculateAge().toDouble(),
                bmi = getBmiUseCase(user) ?: 0.0,
                familyHistoryDiabetes = user.familyHistoryDiabetes,
                glucoseFasting = _examList.value.firstOrNull { it.exam == BioMarker.GLUCOSE_FASTING.displayName }?.value?.toDoubleOrNull() ?: 0.0,
                glucosePostprandial = _examList.value.firstOrNull { it.exam == BioMarker.GLUCOSE_POSTPRANDIAL.displayName }?.value?.toDoubleOrNull() ?: 0.0
            )

            // En el singleton se establece el modelo que se va a compartir con el otro (VM)
            sharePriorityData.set(model)

            // Por ultimo se realiza la navegación con navigateTo().
            navigateTo(scene)
        }
    }
}