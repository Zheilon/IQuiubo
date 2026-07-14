package com.zhei.hikibo.core.domain.usecases

import com.zhei.hikibo.core.domain.repository.AuthRepository
import javax.inject.Inject


class GetCurrentUserUIDUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    operator fun invoke(): String? =
        repo.currentUser?.uid
}