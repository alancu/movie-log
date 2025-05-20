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

@AndroidEntryPoint
class SearchActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la toolbar amb títol i botó "enrere"
        setupToolbar(binding.toolbar, getString(R.string.search_results), showBack = true)

        // Obté la consulta de cerca
        val query = intent.getStringExtra(Constants.Extras.EXTRA_QUERY)
        if (!query.isNullOrEmpty()) {
            supportActionBar?.title = "\"$query\""
            viewModel.searchMovies(query)
        }

        // Configura el RecyclerView
        adapter = MovieAdapter()
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter

        // Observa resultats de la cerca
        viewModel.movies.observe(this) { movies ->
            adapter.updateMovies(movies)
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        setupUserMenu(menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
