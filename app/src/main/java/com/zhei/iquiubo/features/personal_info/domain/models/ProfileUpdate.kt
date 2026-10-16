package com.zhei.iquiubo.features.personal_info.domain.models

/**
 * Nota
 * ----
 * 1. Retire el parametro uid.
 * 2. Si voy a añadir o quitar tambien lo debo hacer en el archivo ubicado en
 *  iquiubo/core/data/mapper/ProfileUpdateMapper.kt. Al igual el modelo [com.zhei.iquiubo.core.domain.model.User]
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
    val sex: String? = null,
    val heightCM: Int? = null,
    val weightKG: Int? = null,
    val activityLevel: String? = null,
    val familyHistoryDiabetes: String? = null
)
