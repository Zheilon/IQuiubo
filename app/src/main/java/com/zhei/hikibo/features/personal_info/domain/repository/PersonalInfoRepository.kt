package com.zhei.hikibo.features.personal_info.domain.repository
import com.zhei.hikibo.core.data.model.FirestoreUser

interface PersonalInfoRepository {

    suspend fun updateProfile(user: FirestoreUser)

}