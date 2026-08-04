package com.zhei.iquiubo.core.data.model
import com.google.firebase.Timestamp


/**
 * Si se va a cambiar algún parametro estos son lugares donde
 * se debe cambiar el nombre:
 *
 * 1. data class ProfileUpdate()
 * 2. data/mapper/ProfileUpdateToMap.kt
 * */
data class FirestoreUser(
    val userId: String = "",
    val displayName: String = "",
    val firstName: String = "",
    val firstLastName: String = "",
    val email: String = "",
    val emailVerified: Boolean = false,
    val policyAccepted: Boolean = false,
    val birthDay: String = "",
    val sex: String = "",
    val createdIn: Timestamp = Timestamp.now()
)
