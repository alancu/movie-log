package com.alejandro.movielog.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.main.MainActivity
import com.alejandro.movielog.utils.auth.UserAuthHelper
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

/**
 * Activitat per a iniciar sessió amb Google. Si l'usuari ja ha iniciat sessió, redirigeix a MainActivity.
 */
@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var googleSignInClient: com.google.android.gms.auth.api.signin.GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Si l'usuari ja està iniciat, anem a la pantalla principal
        if (UserAuthHelper.isUserLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        // configura el client de Google
        googleSignInClient = UserAuthHelper.getGoogleClient(this)

        findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }
    }

    // inicia el procés de login amb Google.
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken ?: throw Exception("No s'ha obtingut el token")
                UserAuthHelper.authenticate(idToken) { success, exception ->
                    if (success) {
                        startActivity(Intent(this, MainActivity::class.java))
                        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
                        finish()
                    } else {
                        Log.e("LoginActivity", getString(R.string.google_signin_failed), exception)
                        Toast.makeText(this, getString(R.string.google_signin_failed), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Log.e("LoginActivity", getString(R.string.google_signin_failed), e)
                Toast.makeText(this, getString(R.string.google_signin_failed), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
