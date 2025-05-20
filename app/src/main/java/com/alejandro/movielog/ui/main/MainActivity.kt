package com.alejandro.movielog.ui.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.fragments.MovieListFragment
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.navigateTo
import dagger.hilt.android.AndroidEntryPoint
import android.view.Menu

/**
 * Activitat principal que mostra el catàleg de pel·lícules populars.
 * Carrega el fragment MovieListFragment on es mostren les pel·lícules.
 * Només ací s'activa la cerca (SearchView) en la Toolbar
 */
// @AndroidEntryPoint indica a Hilt que esta classe necessita rebre dependències injectades automàticament
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        // Toolbar amb títol i sense botó "enrere"
        setupToolbar(toolbar, getString(R.string.popular_movies), showBack = false)

        // Carrega el fragment amb la llista de pel·lícules populars
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_movie_list, MovieListFragment())
                .commit()
        }
    }

    /**
     * "Unfla" el menú principal i configura la SearchView
     * Quan es busca, obri SearchActivity amb la consulta.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // SearchView només a MainActivity
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = getString(R.string.search_movies)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // Navega a SearchActivity passant la consulta de cerca
                    navigateTo<SearchActivity>(Constants.Extras.EXTRA_QUERY to query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?) = false
        })

        // Afig el menú d'usuari (icona, foto, accions)
        setupUserMenu(menu)

        return true
    }
}
