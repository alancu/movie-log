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

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, getString(R.string.popular_movies), showBack = false)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_movie_list, MovieListFragment())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Configura la SearchView només a MainActivity
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.queryHint = getString(R.string.search_movies)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    navigateTo<SearchActivity>(Constants.Extras.EXTRA_QUERY to query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?) = false
        })

        // Crida la funció de baseActivity per a configurar el menú d'usuari
        setupUserMenu(menu)

        return true
    }
}
