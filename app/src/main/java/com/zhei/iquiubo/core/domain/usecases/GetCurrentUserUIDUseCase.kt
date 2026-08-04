package com.zhei.iquiubo.core.domain.usecases

import com.zhei.iquiubo.core.domain.repository.AuthRepository
import javax.inject.Inject


class GetCurrentUserUIDUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    operator fun invoke(): String? =
        repo.currentUser?.uid
}