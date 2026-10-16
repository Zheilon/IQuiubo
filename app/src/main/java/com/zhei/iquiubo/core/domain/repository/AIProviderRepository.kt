package com.zhei.iquiubo.core.domain.repository

interface AIProviderRepository {

    suspend fun interpret(content: String): String?

}