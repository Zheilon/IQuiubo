package com.zhei.hikibo.core.domain.model
import java.time.Instant

/**
 * Nota
 * ----
 * 1. Retire el parametro uid.
 * */
data class ProfileUpdate(
    val userId: String? = null,
    val displayName: String? = null,
    val firstName: String? = null,
    val firstLastName: String? = null,
    val email: String? = null,
    val emailVerified: Boolean? = null,
    val policyAccepted: Boolean? = null,
    val birthDay: String? = null,
    val sex: String? = null
)
