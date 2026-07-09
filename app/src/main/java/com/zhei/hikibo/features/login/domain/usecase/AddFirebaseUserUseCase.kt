package com.zhei.hikibo.features.login.domain.usecase
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.features.login.domain.repository.LoginRepository
import javax.inject.Inject

class AddFirebaseUserUseCase @Inject constructor(
    private val repo: LoginRepository
)  {

    suspend operator fun invoke(user: FirestoreUser) {
        repo.saveUser(user)
    }
}