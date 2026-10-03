package com.zhei.iquiubo.core.data.repository
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.zhei.iquiubo.core.domain.repository.AIProviderRepository
import com.zhei.iquiubo.core.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeminiProviderRepositoryImpl @Inject constructor(
    private val functions: FirebaseFunctions
): AIProviderRepository {


    override suspend fun interpret(content: String): String {
        return withContext(Dispatchers.IO) {
            runCatching {
                val response = functions
                    .getHttpsCallable("askGemini")
                    .call(
                        mapOf(
                            "model" to "gemini-2.0-flash",
                            "prompt" to content
                        )
                    ).await()

                response.getData().toString()

            }.onFailure {
                Log.e("AUTH_TESTING", "Error en askGemini", it)

            }.getOrNull() ?: "No hubo respuesta"
        }
    }
}