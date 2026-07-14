package com.zhei.hikibo.features.login.domain.usecase
import com.zhei.hikibo.core.domain.model.User
import com.zhei.hikibo.core.domain.repository.UserRepository
import javax.inject.Inject

class AddFirebaseUserUseCase @Inject constructor(
    private val repo: UserRepository
)  {

    suspend operator fun invoke(user: User) {
        repo.saveProfile(user)
    }
}