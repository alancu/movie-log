package com.alejandro.movielog.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandro.movielog.databinding.ActivitySearchBinding
import com.alejandro.movielog.ui.components.MovieAdapter
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configurem la toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // obtenim la consulta des de l'Intent
        val query = intent.getStringExtra(Constants.Extras.EXTRA_QUERY)
        if (!query.isNullOrEmpty()) {
            supportActionBar?.title = "\"$query\""
            viewModel.searchMovies(query)
        }

        // Configura el RecyclerView
        adapter = MovieAdapter()
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter

        // Observem els resultats de la cerca
        viewModel.movies.observe(this) { movies ->
            adapter.updateMovies(movies)
        }

        viewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        if (!query.isNullOrEmpty()) {
            viewModel.searchMovies(query)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
