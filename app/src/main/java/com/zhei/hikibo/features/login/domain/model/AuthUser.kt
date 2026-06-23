package com.zhei.hikibo.features.login.domain.model

data class AuthUser(
    val uid: String,
    val email: String?,
    val emailVerified: Boolean
)
