package com.zhei.iquiubo.core.domain.usecases

import com.zhei.iquiubo.core.domain.model.User
import com.zhei.iquiubo.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val repo: UserRepository
) {

    operator fun invoke(userId: String): Flow<User?> =
        repo.observeUser(userId)
}