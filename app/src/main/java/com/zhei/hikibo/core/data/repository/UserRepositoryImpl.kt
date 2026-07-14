package com.zhei.hikibo.core.data.repository
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import com.zhei.hikibo.core.common.FirestoreCollections
import com.zhei.hikibo.core.data.mapper.toCustomMap
import com.zhei.hikibo.core.data.model.FirestoreUser
import com.zhei.hikibo.core.domain.model.ProfileUpdate
import com.zhei.hikibo.core.domain.model.User
import com.zhei.hikibo.core.domain.repository.UserRepository
import com.zhei.hikibo.core.mappers.toDataFirestoreUser
import com.zhei.hikibo.core.mappers.toDomainUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): UserRepository {

    /**
     * Note
     * ----
     * ¿Por qué lo tengo sin captura de errores?
     * Porque quiero exponer el error al usuario, lo
     * capturo en el ViewModel().
     * */
    override suspend fun saveProfile(user: User) {
        firestore
            .collection(FirestoreCollections.USER)
            .document(user.userId)
            .set(user.toDataFirestoreUser())
            .await()
    }



    /**
     *
     * */
    override suspend fun getUser(userId: String): User? {
        return try {
            val snap = firestore
                .collection(FirestoreCollections.USER)
                .document(userId)
                .get()
                .await()

            snap.toObject(FirestoreUser::class.java)?.toDomainUser()

        } catch (e: Exception) {
            Log.e("getUser", "getUser falló", e)
            null
        }
    }



    /**
     *
     **/
    override suspend fun updateProfile(userId: String, profile: ProfileUpdate) {

        val map = profile.toCustomMap()
        if (map.isEmpty()) return

        firestore
            .collection(FirestoreCollections.USER)
            .document(userId)
            .set(map, SetOptions.merge())
            .await()
    }



    /**
     *
     * */
    override fun observeUser(userId: String): Flow<User?> = callbackFlow {
        val reg = firestore
            .collection(FirestoreCollections.USER)
            .document(userId)
            .addSnapshotListener { snap, error ->
                if (error != null) { close(error); return@addSnapshotListener }

                val user = runCatching {
                    snap?.toObject(FirestoreUser::class.java)?.toDomainUser()
                }.getOrElse {
                    Log.e("Observe User", "Documento malformado", it)
                    null
                }

                trySend(user)
            }

        awaitClose { reg.remove() }
    }
}