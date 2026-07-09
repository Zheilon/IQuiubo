package com.zhei.hikibo.core.domain.usecases

import com.zhei.hikibo.core.domain.repository.AuthRepository
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    operator fun invoke(): String? =
        repo.currentUser?.uid
}