package com.zhei.hikibo.features.login.data.repository
import com.zhei.hikibo.core.mappers.toDomain
import com.zhei.hikibo.features.login.data.remote.FirebaseAuthDataSource
import com.zhei.hikibo.features.login.domain.model.AuthUser
import com.zhei.hikibo.features.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {


    override val currentUser get(): AuthUser? =
        dataSource.currentUser?.toDomain()



    override fun observeAuthState(): Flow<AuthUser?> =
        dataSource.authState().map { it?.toDomain() }



    override suspend fun signInWithEmail(email: String, password: String): AuthUser =
        dataSource.signIn(email, password).toDomain()



    override suspend fun signUpWithEmail(email: String, password: String): AuthUser =
        dataSource.signUp(email, password).toDomain()



    override suspend fun signInWithGoogle(idToken: String): AuthUser =
        dataSource.signInWithGoogle(idToken).toDomain()



    override suspend fun sendEmailVerification() {
        dataSource.sendEmailVerification()
    }



    override fun signOut() = dataSource.signOut()
}