package com.alejandro.movielog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.search_view)
        searchView.queryHint = getString(R.string.query_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Envia la cerca al fragment
                query?.let {
                    val fragment = supportFragmentManager.findFragmentById(R.id.fragment_movie_list)
                    if (fragment is MovieListFragment) {
                        fragment.searchMovies(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false // No fem res mentre s'escriu
            }
        })

        // afegir el fragment dinàmicament
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_movie_list, MovieListFragment())  // fragment_container és el container a activity_main.xml
                .commit()
        }
    }
}
