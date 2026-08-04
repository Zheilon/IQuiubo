package com.zhei.iquiubo.core.data.repository
import com.zhei.iquiubo.core.mappers.toDomainAuthUser
import com.zhei.iquiubo.core.data.remote.FirebaseAuthDataSource
import com.zhei.iquiubo.core.domain.model.AuthUser
import com.zhei.iquiubo.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {


    override val currentUser get(): AuthUser? =
        dataSource.currentUser?.toDomainAuthUser()



    override fun observeAuthState(): Flow<AuthUser?> =
        dataSource.authState().map { it?.toDomainAuthUser() }



    override suspend fun signInWithEmail(email: String, password: String): AuthUser =
        dataSource.signIn(email, password).toDomainAuthUser()



    override suspend fun signUpWithEmail(email: String, password: String): AuthUser =
        dataSource.signUp(email, password).toDomainAuthUser()



    override suspend fun signInWithGoogle(idToken: String): AuthUser =
        dataSource.signInWithGoogle(idToken).toDomainAuthUser()



    override suspend fun sendEmailVerification() {
        dataSource.sendEmailVerification()
    }



    override fun signOut() = dataSource.signOut()
}