package com.zhei.hikibo.core.domain.model
import java.time.Instant

data class User(
    val userId: String,
    val displayName: String,
    val firstName: String,
    val firstLastName: String,
    val email: String,
    val emailVerified: Boolean,
    val policyAccepted: Boolean,
    val birthDay: String,
    val sex: String,
    val createdIn: Instant
)
