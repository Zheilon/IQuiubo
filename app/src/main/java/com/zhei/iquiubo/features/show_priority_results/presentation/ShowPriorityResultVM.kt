package com.zhei.iquiubo.features.show_priority_results.presentation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhei.iquiubo.core.domain.model.NeuralNetConfig
import com.zhei.iquiubo.features.entry_interpretation.domain.usecases.GenerateNeuralNetInferenceUseCase
import com.zhei.iquiubo.features.put_rna_values.domain.model.PriorityInputModel
import com.zhei.iquiubo.features.put_rna_values.domain.shared.SharePriorityData
import com.zhei.iquiubo.features.show_priority_results.domain.models.RecomendationsAreaUIModel
import com.zhei.iquiubo.features.show_priority_results.domain.usecases.GetRecomendationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowPriorityResultVM @Inject constructor(
    sharePriorityData: SharePriorityData,
    private val generateNeuralNetInferenceUseCase: GenerateNeuralNetInferenceUseCase,
    private val getRecomendationsUseCase: GetRecomendationsUseCase
): ViewModel() {

    // Por recomendación, se lee directamente en la instacia del MutableStateFlow
    private val _priorityInput = MutableStateFlow(sharePriorityData.take())
    val priorityInput: StateFlow<PriorityInputModel?> = _priorityInput.asStateFlow()

    private val _listPriorityOutput = MutableStateFlow<List<PriorityResultModel>>(emptyList())
    val listPriorityOutput: StateFlow<List<PriorityResultModel>> = _listPriorityOutput.asStateFlow()

    private val _recomendationUi = MutableStateFlow<RecomendationsAreaUIModel?>(null)
    val recomendationUi: StateFlow<RecomendationsAreaUIModel?> = _recomendationUi.asStateFlow()

    private val _uiState = MutableStateFlow<ShowPriorityUiState>(ShowPriorityUiState.Idle)
    val uiState: StateFlow<ShowPriorityUiState> = _uiState.asStateFlow()



    fun lazyInit() {
        generatePrediction()
    }



    private fun updateUiState(state: ShowPriorityUiState) {
        _uiState.value = state
    }



    fun generatePrediction() {
        viewModelScope.launch {

            updateUiState(ShowPriorityUiState.Loading)

            _priorityInput.value?.let {

                val map = mapOf(
                    "activity_level" to formatActivtyLevel(it.activityLevel),
                    "age" to it.age,
                    "bmi" to it.bmi,
                    "family_history_diabetes" to formatFamilyHistoryDiabetes(it.familyHistoryDiabetes),
                    "glucose_fasting" to it.glucoseFasting,
                    "glucose_postprandial" to it.glucosePostprandial
                )

                val arr = generateNeuralNetInferenceUseCase(map)

                if (arr.isNotEmpty()) {

                    // Poner el displayName así como prioridad I y demás es
                    // muy importante ese formato, así que cuidado al cambiarlo.
                    _listPriorityOutput.value = listOf(
                        PriorityResultModel(
                            displayName = NeuralNetConfig.PriorityNetNames.BAJA.priority,
                            subtitle = "Prioridad más baja",
                            probability = arr[0]
                        ),
                        PriorityResultModel(
                            displayName = NeuralNetConfig.PriorityNetNames.MEDIA.priority,
                            subtitle = "Prioridad media",
                            probability = arr[1]
                        ),
                        PriorityResultModel(
                            displayName = NeuralNetConfig.PriorityNetNames.ALTA.priority,
                            subtitle = "Prioridad más alta",
                            probability = arr[2]
                        )
                    )

                    // En esta parte es donde obtengo la recomendación.
                    val maxPriority = _listPriorityOutput.value.maxByOrNull { item -> item.probability }
                    _recomendationUi.value = getRecomendationsUseCase(maxPriority?.displayName)

                    updateUiState(ShowPriorityUiState.Success)

                }
            }
        }
    }



    private fun formatActivtyLevel(level: String): Double = when(level) {
        NeuralNetConfig.ActivityValues.Baja.value -> 0.0
        NeuralNetConfig.ActivityValues.Media.value -> 1.0
        NeuralNetConfig.ActivityValues.Alta.value -> 2.0
        else -> error("Nivel de actividad no reconocido: $level")
    }



    private fun formatFamilyHistoryDiabetes(value: String): Double = when(value) {
        NeuralNetConfig.FamilyHistoryValues.No.value -> 0.0
        NeuralNetConfig.FamilyHistoryValues.Yes.value -> 1.0
        else -> error("Antecedente familiar no reconocido: $value")
    }
}