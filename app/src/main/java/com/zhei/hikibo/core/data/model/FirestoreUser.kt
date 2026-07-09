package com.zhei.hikibo.core.data.model
import com.google.firebase.Timestamp
import java.time.Instant

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
