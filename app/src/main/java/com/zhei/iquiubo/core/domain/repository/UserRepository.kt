package com.zhei.iquiubo.core.domain.repository
import com.zhei.iquiubo.core.domain.model.ProfileUpdate
import com.zhei.iquiubo.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveProfile(user: User)

    suspend fun updateProfile(userId: String, profile: ProfileUpdate)

    suspend fun getUser(userId: String): User?

    fun observeUser(userId: String): Flow<User?>
}