package com.zhei.hikibo.core.domain.repository
import com.zhei.hikibo.core.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

    val currentUser: AuthUser?

    fun observeAuthState(): Flow<AuthUser?>

    suspend fun signInWithEmail(email: String, password: String): AuthUser

    suspend fun signUpWithEmail(email: String, password: String): AuthUser

    suspend fun signInWithGoogle(idToken: String): AuthUser

    suspend fun sendEmailVerification()

    fun signOut()

}