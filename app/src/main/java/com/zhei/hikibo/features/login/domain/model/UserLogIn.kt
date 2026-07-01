package com.zhei.hikibo.features.login.domain.model
import com.google.firebase.Timestamp

data class UserLogIn(
    val userId: String = "",
    val displayName: String = "",
    val email: String = "",
    val isEmailVerified: Boolean = false,
    val policyAccepted: Boolean = false,
    val createdIn: Timestamp = Timestamp.now(),
)
