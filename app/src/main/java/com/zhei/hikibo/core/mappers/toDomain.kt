package com.zhei.hikibo.core.mappers
import com.google.firebase.auth.FirebaseUser
import com.zhei.hikibo.core.domain.model.AuthUser


fun FirebaseUser.toDomain(): AuthUser =
    AuthUser(uid, email, isEmailVerified)