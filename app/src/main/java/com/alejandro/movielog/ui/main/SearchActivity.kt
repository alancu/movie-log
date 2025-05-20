package com.alejandro.movielog.ui.main

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandro.movielog.R
import com.alejandro.movielog.databinding.ActivitySearchBinding
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.components.MovieAdapter
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activitat que mostra els resultats d'una cerca de pel·lícules.
 * Rep la consulta des de MainActivity, busca pel·lícules via ViewModel i les mostra amb MovieAdapter.
 * També permet obrir el detall de cada pel·lícula fent clic.
 */
@AndroidEntryPoint
class SearchActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la toolbar amb títol i botó d'anar enrere
        setupToolbar(binding.toolbar, getString(R.string.search_results), showBack = true)

        // Rep la consulta de cerca i inicia la búsqueda
        val query = intent.getStringExtra(Constants.Extras.EXTRA_QUERY)
        if (!query.isNullOrEmpty()) {
            supportActionBar?.title = "\"$query\""
            viewModel.searchMovies(query)
        }

        // Configura el RecyclerView amb MovieAdapter
        adapter = MovieAdapter()
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter

        // Observa els resultats de la cerca i els mostra
        viewModel.movies.observe(this) { movies ->
            adapter.updateMovies(movies)
        }

        // Mostra un missatge d'error si la cerca falla
        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Unfla el menú d'usuari
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        setupUserMenu(menu)
        return true
    }

    // Acció per al botó "enrere" de la Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
