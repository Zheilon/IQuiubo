package com.zhei.iquiubo.core.domain.model

data class AuthUser(
    val uid: String,
    val email: String?,
    val emailVerified: Boolean
)