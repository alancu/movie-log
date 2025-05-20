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

open class BaseActivity : AppCompatActivity() {

    // Opcional: per personalitzar títol i si volem el botó de tornar
    fun setupToolbar(toolbar: Toolbar, title: String? = null, showBack: Boolean = false) {
        setSupportActionBar(toolbar)
        title?.let { supportActionBar?.title = it }
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Configura cerca si vols que totes tinguen la SearchView
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.queryHint = getString(R.string.search_movies)
        // Si la SearchView no cal a totes, pots fer-la opcional

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Accions comunes per al menú
        return when (item.itemId) {
            android.R.id.home -> {
                // Botó "back"
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_user -> {
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
