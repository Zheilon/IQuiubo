package com.zhei.hikibo.features.personal_info.domain.usecases
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.features.personal_info.domain.repository.PersonalInfoRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repo: PersonalInfoRepository
) {

    suspend operator fun invoke(user: FirestoreUser) {
        repo.updateProfile(user)
    }
}