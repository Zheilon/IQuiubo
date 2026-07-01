package com.zhei.hikibo.features.login.data.repository
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.zhei.hikibo.features.login.domain.model.UserLogIn
import com.zhei.hikibo.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor (
    private val firestore: FirebaseFirestore
): LoginRepository {


    /**
     *
     * */
    override suspend fun saveUser(user: UserLogIn) {
        firestore
            .collection("users")
            .document(user.userId)
            .set(user, SetOptions.merge())
            .await()
    }



    override suspend fun getUser(userId: String): UserLogIn? {
        return try {
            val snap = firestore.collection("users").document(userId).get().await()
            if (snap.metadata.hasPendingWrites()) null
            else snap.toObject(UserLogIn::class.java)

        } catch (e: FirebaseFirestoreException) {
            // Offline sin caché, sin permiso, etc. → no hay perfil utilizable.
            null
        }
    }
}