package com.zhei.iquiubo.features.entry_interpretation.domain.usecases
import com.zhei.iquiubo.features.entry_interpretation.domain.repository.IQuiuboNeuralNetworkRepository
import javax.inject.Inject

class GenerateNeuralNetInferenceUseCase @Inject constructor(
    private val repo: IQuiuboNeuralNetworkRepository
) {

    operator fun invoke(map: Map<String, Double>): FloatArray =
        repo.predict(map)
}