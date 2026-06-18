package com.zhei.ikiora.features.login.data.repository
import com.google.firebase.firestore.FirebaseFirestore
import com.zhei.ikiora.features.login.domain.model.UserLogIn
import com.zhei.ikiora.features.login.domain.repository.LoginRepository
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor (
    private val firestore: FirebaseFirestore
): LoginRepository {

    override suspend fun saveUser(user: UserLogIn) {
        TODO("Not yet implemented")
    }
}