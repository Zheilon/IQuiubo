package com.zhei.hikibo.features.login.domain.usecase
import com.zhei.hikibo.features.login.domain.model.AuthUser
import com.zhei.hikibo.features.login.domain.repository.AuthRepository
import javax.inject.Inject

class CreateEmailUserUserCase @Inject constructor(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): AuthUser =
        repo.signUpWithEmail(email, password)
}