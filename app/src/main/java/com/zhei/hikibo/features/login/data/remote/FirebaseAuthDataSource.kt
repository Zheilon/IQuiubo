package com.zhei.hikibo.features.login.data.remote
import android.accounts.AuthenticatorException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.zhei.hikibo.features.login.domain.model.AuthUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {

    val currentUser get() = auth.currentUser


    suspend fun signIn(email: String, password: String): FirebaseUser =
        auth.signInWithEmailAndPassword(email, password).await().user
            ?: throw Exception("Firebase no devolvio usuario tras iniciar sesión.")


    suspend fun signUp(email: String, password: String): FirebaseUser =
        auth.createUserWithEmailAndPassword(email, password).await().user
            ?: throw Exception("Firebase no devolvio usuario tras el registro.")



    suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential).await().user
            ?: throw Exception("Firebase no devolvio usuario tras login con Google.")
    }



    fun authState(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser) }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }



    suspend fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()?.await()
    }



    fun signOut() = auth.signOut()
}