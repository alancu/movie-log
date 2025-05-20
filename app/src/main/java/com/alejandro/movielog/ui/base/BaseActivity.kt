package com.alejandro.movielog.ui.base

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.components.UserProfileDialog
import com.alejandro.movielog.utils.auth.UserAuthHelper
import com.alejandro.movielog.utils.loadCircularImage

/**
 * Classe base per a totes les activitats de l'app, fa que totes les activitats tinguen una
 * configuració comuna.
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Configura la Toolbar.
     * toolbar és la vista de la Toolbar que a utilitzar.
     * si showBack és true, mostrarem el botó d'anar enrere.
     */
    fun setupToolbar(toolbar: Toolbar, title: String? = null, showBack: Boolean = false) {
        setSupportActionBar(toolbar)
        title?.let { supportActionBar?.title = it }
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
    }

    /**
     * Configura el menú d'usuari (icona i accions).
     * Mostra la foto de perfil (si està loguejat) i obri el diàleg de perfil en fer clic.
     * "menu" és el menú que es mostra a la toolbar (hi ha dos: tipus amb i sense búsqueda)
     */
    protected fun setupUserMenu(menu: Menu) {
        val userItem = menu.findItem(R.id.action_user)
        val actionView = userItem?.actionView
        val userImage = actionView?.findViewById<ImageView>(R.id.image_user_menu)
        val account = UserAuthHelper.getGoogleAccount(this)
        account?.let {
            // Carrega la foto de perfil en la icona del menú
            userImage?.loadCircularImage(it.photoUrl.toString())
        }
        userImage?.setOnClickListener {
            // Obri el diàleg amb info d'usuari i opció de logout
            val dialog = UserProfileDialog(account) {
                UserAuthHelper.logout(this)
                finish() // Tanca l'activitat després de logout
            }
            dialog.show(supportFragmentManager, "user_profile")
        }
    }

    /**
     * Crea el menú d'opcions (Toolbar). Es pot sobreescriure en cada activitat.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)

        // la SearchView només la mostrem a MainActivity, està desactivada a la resta
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.queryHint = getString(R.string.search_movies)

        // Configura el botó de perfil
        val userItem = menu.findItem(R.id.action_user)
        val actionView = userItem?.actionView
        val userImage = actionView?.findViewById<ImageView>(R.id.image_user_menu)
        val account = UserAuthHelper.getGoogleAccount(this)
        account?.let {
            userImage?.loadCircularImage(it.photoUrl.toString())
        }
        userImage?.setOnClickListener {
            val dialog = UserProfileDialog(account) {
                UserAuthHelper.logout(this)
                finish()
            }
            dialog.show(supportFragmentManager, "user_profile")
        }
        return true
    }

    /**
     * Gestiona la selecció dels ítems del menú (com el botó "enrere" o logout).
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Acció del botó "enrere"
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_user -> {
                // Obri el diàleg de perfil d'usuari (si està loguejat)
                val account = UserAuthHelper.getGoogleAccount(this)
                if (account != null) {
                    val dialog = UserProfileDialog(account) {
                        UserAuthHelper.logout(this)
                        finish()
                    }
                    dialog.show(supportFragmentManager, "user_profile")
                } else {
                    Toast.makeText(this, getString(R.string.login_not_completed), Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
