package com.zhei.hikibo.features.personal_info.domain.usecases
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.core.domain.model.ProfileUpdate
import com.zhei.hikibo.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repo: UserRepository
) {

    suspend operator fun invoke(userId: String, profile: ProfileUpdate) {
        repo.updateProfile(userId, profile)
    }
}