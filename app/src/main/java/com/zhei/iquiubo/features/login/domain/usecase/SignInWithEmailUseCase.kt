package com.zhei.iquiubo.features.login.domain.usecase
import com.zhei.iquiubo.core.domain.model.AuthUser
import com.zhei.iquiubo.core.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): AuthUser =
        repo.signInWithEmail(email, password)
}