package com.alejandro.movielog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    // en "companion object" posem coses que seran compartides per totes les instàncies
    companion object {
        // codi per identificar el resultat de l'inici de sessió amb Google
        private const val RC_SIGN_IN = 9001
    }

    // client per iniciar sessió amb Google
    private lateinit var googleSignInClient: com.google.android.gms.auth.api.signin.GoogleSignInClient
    // objecte per gestionar l'autenticació amb Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // si l'usuari ja ha iniciat sessió, el redirigim a MainActivity
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        // configurem les opcions de Google Sign-In
        val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
            com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        // creem l'intent per a iniciar sessió amb Google
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // comprovem si el resultat és del Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // intentem obtindre el compte de Google
                val account = task.getResult(ApiException::class.java)!!
                // iniciem sessió amb Firebase
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("LoginActivity", getString(R.string.google_signin_failed), e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // convertim el token de Google a una credencial de Firebase
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // inici de sessió correcte -> anem a la pantalla principal
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
                    finish()
                } else {
                    // inici de sessió fallat -> mostrem l'error pel log
                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}
