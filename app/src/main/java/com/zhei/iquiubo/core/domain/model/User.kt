package com.zhei.iquiubo.core.domain.model
import java.time.Instant


/**
 * Si cambio algo, cambio en [com.zhei.iquiubo.core.data.model.FirestoreUser] y en
 * ProfileUserUpdate y en los mappers
 * */
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
    val createdIn: Instant,
    val heightCM: Int,
    val weightKG: Int,
    val activityLevel: String,
    val familyHistoryDiabetes: String
)
