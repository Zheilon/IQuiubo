package com.zhei.iquiubo.core.data.mapper
import com.zhei.iquiubo.core.domain.model.ProfileUpdate


/**
 * Función extend utilizada para cuando se desee actualizar un parametro
 * o varios en el documento del user.
 * */
fun ProfileUpdate.toCustomMap(): Map<String, Any> = buildMap {
    userId?.let { put("userId", it) }
    displayName?.let { put("displayName", it) }
    firstName?.let { put("firstName", it) }
    firstLastName?.let { put("firstLastName", it) }
    email?.let { put("email", it) }
    emailVerified?.let { put("emailVerified", it) }
    policyAccepted?.let { put("policyAccepted", it) }
    birthDay?.let { put("birthDay", it) }
    sex?.let { put("sex", it) }
}