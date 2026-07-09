package com.zhei.hikibo.features.login.presentation
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.zhei.hikibo.R


/**
 * Se encarga de manejara los distintos resultados
 * que pueda generar el Helper de google.
 * */
sealed interface GoogleSignInResult {

    data class Success(val idToken: String): GoogleSignInResult
    data object Canceled: GoogleSignInResult
    data class Error(val cause: Throwable, val userMessage: String): GoogleSignInResult

}


/**
 * Representa los distintos estados que pueden
 * ser expuestos a la UI. Su implementación se da
 * directamente en el ViewModel: [LoginVM]
 * */
sealed interface GoogleUiState {

    data object Idle: GoogleUiState
    data object Loading : GoogleUiState
    data object Success : GoogleUiState
    data class Error(val message: String): GoogleUiState

}


/**
 * Representa el error acurrido cuando se intenta
 * iniciar sesión con correo
 * */
sealed interface EmailUiState {
    data object Idle: EmailUiState
    data object Loading: EmailUiState
    data object Success: EmailUiState
    data class Error(val message: String): EmailUiState
    data class PasswordError(val message: String): EmailUiState
}


/**
 * Representan los errores cuando se guardan valores
 * en firestore o se leen
 * */
sealed interface FirestoreUiState {
    data object Idle: FirestoreUiState
    data object Loading: FirestoreUiState
    data object Success: FirestoreUiState
    data class Error(val message: String): FirestoreUiState
}


suspend fun getGoogleIdToken(context: Context): GoogleSignInResult {

    return try {

        val webClientId = context.getString(R.string.default_web_client_id)

        val option = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        val result = CredentialManager.create(context).getCredential(context, request)
        val token = GoogleIdTokenCredential.createFrom(result.credential.data).idToken

        GoogleSignInResult.Success(token)


    } catch (e: GetCredentialCancellationException) {
        Log.e("GoogleSignIn (funct)", "Usuario canceló el sheet")   // normal, silencio
        GoogleSignInResult.Canceled


    } catch (e: NoCredentialException) {
        Log.w("GoogleSignIn (funct)", "No hay cuentas de Google en el device", e)
        GoogleSignInResult.Error(e, "¡No hay cuentas de Google en este dispositivo!")


    } catch (e: GetCredentialException) {
        Log.e("GoogleSignIn (funct)", "NameError obteniendo credential", e)   // error real
        GoogleSignInResult.Error(e, "¡NameError Obteniendo credencial!")
    }

}