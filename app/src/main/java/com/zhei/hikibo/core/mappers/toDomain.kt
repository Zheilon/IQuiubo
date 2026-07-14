package com.zhei.hikibo.core.mappers
import com.google.firebase.auth.FirebaseUser
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.core.domain.model.AuthUser
import com.zhei.hikibo.core.domain.model.User


fun FirebaseUser.toDomainAuthUser(): AuthUser =
    AuthUser(uid, email, isEmailVerified)


fun FirestoreUser.toDomainUser(): User =
    User(
        userId = this.userId,
        displayName = this.displayName,
        firstName = this.firstName,
        firstLastName = this.firstLastName,
        email = this.email,
        emailVerified = this.emailVerified,
        policyAccepted = this.policyAccepted,
        birthDay = this.birthDay,
        sex = this.sex,
        createdIn = this.createdIn.toInstant()
    )