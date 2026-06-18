package com.zhei.ikiora.features.login.domain.repository
import com.zhei.ikiora.features.login.domain.model.UserLogIn

interface LoginRepository {

    suspend fun saveUser(user: UserLogIn)

}