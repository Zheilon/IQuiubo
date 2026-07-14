package com.zhei.hikibo.core.mappers
import com.zhei.hikibo.core.common.toTimestamp
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.core.domain.model.User


fun User.toDataFirestoreUser(): FirestoreUser =
    FirestoreUser(
        userId = this.userId,
        displayName = this.displayName,
        firstName = this.firstName,
        firstLastName = this.firstLastName,
        email = this.email,
        emailVerified = this.emailVerified,
        policyAccepted = this.policyAccepted,
        birthDay = this.birthDay,
        sex = this.sex,
        createdIn = this.createdIn.toTimestamp()
    )