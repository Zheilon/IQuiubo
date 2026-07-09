package com.zhei.hikibo.features.login.domain.repository
import com.zhei.hikibo.core.data.model.FirestoreUser

interface LoginRepository {

    suspend fun saveUser(user: FirestoreUser)

    suspend fun getUser(userId: String): FirestoreUser?

}