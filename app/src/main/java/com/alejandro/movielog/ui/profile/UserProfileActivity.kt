package com.alejandro.movielog.ui.profile

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.favorites.FavoriteMoviesActivity
import com.alejandro.movielog.ui.history.WatchedMoviesActivity
import com.alejandro.movielog.utils.auth.UserAuthHelper
import com.alejandro.movielog.utils.loadCircularImage
import com.alejandro.movielog.utils.navigateTo

/**
 * Activitat de perfil d'usuari.
 * Mostra la informació del compte de Google iniciat
 * Permet veure llistats de favorites i vistes, i fer logout.
 * No utilitza ViewModel perquè només mostra dades ja autenticades.
 */
class UserProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Configura la toolbar amb títol i botó "enrere"
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, getString(R.string.profile), showBack = true)

        // Elements de la UI per a mostrar dades d'usuari i accions
        val imageUserPhoto = findViewById<ImageView>(R.id.image_user_photo)
        val textUserName = findViewById<TextView>(R.id.text_user_name)
        val textUserEmail = findViewById<TextView>(R.id.text_user_email)
        val buttonFavorites = findViewById<Button>(R.id.button_favorites)
        val buttonLogout = findViewById<Button>(R.id.button_logout)
        val buttonWatched = findViewById<Button>(R.id.button_watched)

        // Obté les dades del compte de Google iniciat
        val account = UserAuthHelper.getGoogleAccount(this)

        account?.let {
            textUserName.text = it.displayName ?: ""
            textUserEmail.text = it.email ?: ""
            // Carrega la foto amb forma circular (usant Glide)
            imageUserPhoto.loadCircularImage(it.photoUrl?.toString() ?: "")
        }

        // Botó per veure les pel·lícules favorites
        buttonFavorites.setOnClickListener {
            navigateTo<FavoriteMoviesActivity>()
        }

        // Botó per tancar la sessió
        buttonLogout.setOnClickListener {
            UserAuthHelper.logout(this)
            finish() // Torna a la pantalla de login després de logout
        }

        // Botó per veure llistat de pel·lícules vistes
        buttonWatched.setOnClickListener {
            navigateTo<WatchedMoviesActivity>()
        }
    }

    /**
     * Unfla el menú d'usuari sense SearchView.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        setupUserMenu(menu)
        return true
    }

    /**
     * Permet tornar arrere amb el botó de la Toolbar.
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
