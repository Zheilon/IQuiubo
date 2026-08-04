package com.zhei.iquiubo.core.domain.usecases

import com.zhei.iquiubo.core.domain.model.User
import com.zhei.iquiubo.core.domain.repository.UserRepository
import javax.inject.Inject

class GetRemoteUserUseCase @Inject constructor(
    private val repo: UserRepository
) {

    suspend operator fun invoke(userId: String): User? =
        repo.getUser(userId)
}