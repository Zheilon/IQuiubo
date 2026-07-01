package com.zhei.hikibo.features.login.domain.repository
import com.zhei.hikibo.features.login.domain.model.UserLogIn

interface LoginRepository {

    suspend fun saveUser(user: UserLogIn)

    suspend fun getUser(userId: String): UserLogIn?

}