package com.zhei.iquiubo.core.mappers
import com.google.firebase.auth.FirebaseUser
import com.zhei.iquiubo.core.data.model.FirestoreMeasurement
import com.zhei.iquiubo.core.data.model.FirestoreUser
import com.zhei.iquiubo.core.domain.model.AuthUser
import com.zhei.iquiubo.core.domain.model.Measurement
import com.zhei.iquiubo.core.domain.model.User
import kotlin.Int


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
        createdIn = this.createdIn.toInstant(),
        heightCM = this.heightCM,
        weightKG = this.weightKG,
        activityLevel = this.activityLevel,
        familyHistoryDiabetes = this.familyHistoryDiabetes
    )


fun FirestoreMeasurement.toDomain(): Measurement =
    Measurement(
        measurementId = this.measurementId,
        date = this.date.toInstant(),
        bioMarkers = this.bioMarkers,
        interpretation = this.interpretation,
        riskLevel = this.riskLevel
    )