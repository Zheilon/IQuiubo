package com.zhei.hikibo.features.login.domain.usecase
import com.zhei.hikibo.core.domain.model.AuthUser
import com.zhei.hikibo.core.domain.repository.AuthRepository
import javax.inject.Inject


class SignInWithGoogleUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(tokenId: String): AuthUser =
        repo.signInWithGoogle(tokenId)
}