package com.alejandro.movielog.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandro.movielog.R
import com.alejandro.movielog.databinding.ActivityFavoriteMoviesBinding
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.components.FavoriteAdapter
import com.alejandro.movielog.ui.detail.MovieDetailActivity
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.toApiMovie
import com.alejandro.movielog.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesActivity : BaseActivity() {

    private lateinit var binding: ActivityFavoriteMoviesBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la Toolbar amb títol i botó "enrere"
        setupToolbar(binding.toolbar, getString(R.string.favorite_movies), showBack = true)

        adapter = FavoriteAdapter { savedMovie ->
            // converteix SavedMovie a ApiMovie
            val apiMovie = savedMovie.toApiMovie()
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(Constants.Extras.EXTRA_MOVIE, apiMovie)
            startActivity(intent)
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter

        favoriteViewModel.loadFavorites()

        favoriteViewModel.favoriteMovies.observe(this) { movies ->
            adapter.submitList(movies)
        }

        favoriteViewModel.errorMessage.observe(this) { msg ->
            msg?.let {
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
