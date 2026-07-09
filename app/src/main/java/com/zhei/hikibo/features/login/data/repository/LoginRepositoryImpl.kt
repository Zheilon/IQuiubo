package com.zhei.hikibo.features.login.data.repository
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.zhei.hikibo.core.common.FirestoreCollections
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.features.login.domain.repository.LoginRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor (
    private val firestore: FirebaseFirestore
): LoginRepository {


    /**
     * ¿Por qué lo tengo sin captura de errores?
     * Porque quiero exponer el error al usuario, lo
     * capturo en el ViewModel().
     * */
    override suspend fun saveUser(user: FirestoreUser) {
        firestore
            .collection(FirestoreCollections.USER)
            .document(user.userId)
            .set(user, SetOptions.merge())
            .await()
    }



    override suspend fun getUser(userId: String): FirestoreUser? {
        return try {
            val snap = firestore
                .collection(FirestoreCollections.USER)
                .document(userId)
                .get()
                .await()

            Log.e(
                "getUser",
                "exists=${snap.exists()}" +
                        " pending=${snap.metadata.hasPendingWrites()}" +
                        " cache=${snap.metadata.isFromCache}"
            )

            if (!snap.exists()) null else snap.toObject(FirestoreUser::class.java)

        } catch (e: Exception) {
            Log.e("getUser", "getUser falló", e)
            null
        }
    }
}