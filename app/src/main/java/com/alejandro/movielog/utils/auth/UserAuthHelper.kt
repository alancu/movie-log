package com.alejandro.movielog.utils.auth

import android.content.Context
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.login.LoginActivity
import com.alejandro.movielog.utils.navigateTo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Utilitat per a gestionar la sessió d'usuari amb Google i Firebase.
 */
@Suppress("Deprecation")
object UserAuthHelper {

    // Instància única de FirebaseAuth per gestionar login/logout
    private val auth = FirebaseAuth.getInstance()

    /**
     * Comprova si l'usuari està autenticat.
     */
    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    /**
     * Obté el compte de Google actual, o null si no hi ha cap iniciat.
     */
    fun getGoogleAccount(context: Context): com.google.android.gms.auth.api.signin.GoogleSignInAccount? =
        com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(context)

    /**
     * Crea i retorna el client de Google Sign-In amb la configuració de l'app.
     * S'usa per iniciar el procés de login amb Google.
     */
    fun getGoogleClient(context: Context): com.google.android.gms.auth.api.signin.GoogleSignInClient {
        val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
            com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Web client ID configurat a Google Cloud/Firebase
            .requestEmail()
            .build()

        return com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(context, gso)
    }

    /**
     * Autentica l'usuari amb Firebase utilitzant el token de Google obtingut després del login.
     * Quan finalitza, executa el callback onResult amb true/false depenent de si ha anat bé o no.
     */
    fun authenticate(idToken: String, onResult: (Boolean, Exception?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception)
                }
            }
    }

    /**
     * Tanca la sessió de l'usuari (logout) tant en Firebase com en Google.
     * Després redirigeix a la pantalla de login.
     */
    fun logout(context: Context) {
        auth.signOut()
        getGoogleClient(context).signOut()
        context.navigateTo<LoginActivity>()
    }
}
