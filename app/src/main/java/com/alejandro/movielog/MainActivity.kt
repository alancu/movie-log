package com.alejandro.movielog

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.alejandro.movielog.ui.UserProfileDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.popular_movies)

        // afegim els fragments de búsqueda i llistat de pel·lícules
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
                if (query != null) {
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_movie_list)
                    if (fragment is MovieListFragment) {
                        fragment.searchMovies(query)
                    }
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_user -> {
                val account =com.google.android.gms.auth.api.signin.GoogleSignIn
                    .getLastSignedInAccount(this)
                if (account != null) {
                    val dialog = UserProfileDialog(account) {
                        // Passem la funció signOut com a paràmetre al diàleg (s'executarà en fer logout)
                        signOut()
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

    private fun signOut() {
        // tanquem la sessió de Firebase
        FirebaseAuth.getInstance().signOut()
        // redirigir a l'activitat de login
        startActivity(Intent(this, LoginActivity::class.java))
        @Suppress("DEPRECATION")
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
        // tanquem esta activitat
        finish()
    }
}
