package com.zhei.iquiubo.features.personal_info.domain.usecases
import com.zhei.iquiubo.features.personal_info.domain.models.ProfileUpdate
import com.zhei.iquiubo.core.domain.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repo: UserRepository
) {

    suspend operator fun invoke(userId: String, profile: ProfileUpdate) {
        repo.updateProfile(userId, profile)
    }
}