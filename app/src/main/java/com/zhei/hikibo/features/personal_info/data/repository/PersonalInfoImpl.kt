package com.zhei.hikibo.features.personal_info.data.repository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.zhei.hikibo.core.common.FirestoreCollections
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.features.personal_info.domain.repository.PersonalInfoRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class PersonalInfoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): PersonalInfoRepository {


    override suspend fun updateProfile(user: FirestoreUser) {

        val map = mapOf(
            "firstName" to user.firstName,
            "firstLastName" to user.firstLastName,
            "birthDay" to user.birthDay,
            "sex" to user.sex
        )

        firestore
            .collection(FirestoreCollections.USER)
            .document(user.userId)
            .set(map, SetOptions.merge())
            .await()
    }
}