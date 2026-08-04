package com.zhei.iquiubo.features.login.domain.usecase
import com.zhei.iquiubo.core.domain.model.AuthUser
import com.zhei.iquiubo.core.domain.repository.AuthRepository
import javax.inject.Inject


class SignInWithGoogleUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(tokenId: String): AuthUser =
        repo.signInWithGoogle(tokenId)
}