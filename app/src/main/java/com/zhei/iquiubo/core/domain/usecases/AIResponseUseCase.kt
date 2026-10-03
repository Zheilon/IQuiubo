package com.zhei.iquiubo.core.domain.usecases
import com.zhei.iquiubo.core.domain.repository.AIProviderRepository
import javax.inject.Inject

class AIResponseUseCase @Inject constructor(
    private val repo: AIProviderRepository
) {

    /**
     * Esta función más adelante será compuesta por
     * una lista de un tipo de clase la cual me ayudará
     * para darle más forma a las respuestas
     * */
    suspend operator fun invoke(content: String): String =
        repo.interpret(content)
}