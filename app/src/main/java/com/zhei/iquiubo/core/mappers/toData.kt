package com.zhei.iquiubo.core.mappers
import com.zhei.iquiubo.core.common.toTimestamp
import com.zhei.iquiubo.core.data.model.FirestoreUser
import com.zhei.iquiubo.core.domain.model.User


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