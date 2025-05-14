package com.alejandro.movielog.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.alejandro.movielog.ui.fragments.MovieListFragment
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.components.UserProfileDialog
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.auth.UserAuthHelper
import com.alejandro.movielog.utils.loadCircularImage
import com.alejandro.movielog.utils.navigateTo


/**
 * Activitat principal de l'app. Mostra la llista de pel·lícules i gestiona la barra d'eines amb la cerca i el perfil d'usuari.
 */
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura la toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.popular_movies)

        // Mostra el fragment amb la llista de pel·lícules
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_movie_list, MovieListFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_movies)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    navigateTo<SearchActivity>(Constants.Extras.EXTRA_QUERY to query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        // Carrega la imatge de perfil
        val userItem = menu.findItem(R.id.action_user)
        val actionView = userItem?.actionView
        val userImage = actionView?.findViewById<ImageView>(R.id.image_user_menu)

        val account = UserAuthHelper.getGoogleAccount(this)
        account?.let {
            userImage?.loadCircularImage(it.photoUrl.toString())

        }

        // Mostra el diàleg de perfil
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
        return when (item.itemId) {
            R.id.action_user -> {
                if (UserAuthHelper.isUserLoggedIn) {
                    val account = UserAuthHelper.getGoogleAccount(this)
                    val dialog = UserProfileDialog(account) {
                        UserAuthHelper.logout(this)
                        finish()
                    }
                    dialog.show(supportFragmentManager, "UserProfileDialog")
                } else {
                    Toast.makeText(this, "No s'ha iniciat sessió", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
