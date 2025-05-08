package com.alejandro.movielog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // afegim els fragments de búsqueda i llistat de pel·lícules
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_search, SearchFragment())
                .replace(R.id.fragment_movie_list, MovieListFragment())
                .commit()
        }
    }
}
