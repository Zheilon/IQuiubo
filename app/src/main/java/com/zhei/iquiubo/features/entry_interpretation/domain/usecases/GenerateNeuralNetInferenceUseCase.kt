package com.zhei.iquiubo.features.entry_interpretation.domain.usecases
import com.zhei.iquiubo.features.entry_interpretation.domain.repository.IQuiuboNeuralNetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenerateNeuralNetInferenceUseCase @Inject constructor(
    private val repo: IQuiuboNeuralNetworkRepository
) {

    /**
     * withContext(Default): la inferencia es cómputo pesado y no debe correr en el
     * hilo principal, así la UI no se congela y quien llame no tiene que acordarse.
     * */
    suspend operator fun invoke(map: Map<String, Double>): FloatArray =
        withContext(Dispatchers.Default) { repo.predict(map) }
}