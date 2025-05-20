package com.alejandro.movielog.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.alejandro.movielog.R
import com.alejandro.movielog.databinding.ActivityWatchedMoviesBinding
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.ui.components.WatchedAdapter
import com.alejandro.movielog.ui.detail.MovieDetailActivity
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.toApiMovie
import com.alejandro.movielog.viewmodel.WatchedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchedMoviesActivity : BaseActivity() {

    private lateinit var binding: ActivityWatchedMoviesBinding
    private val watchedViewModel: WatchedViewModel by viewModels()
    private lateinit var adapter: WatchedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchedMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la toolbar amb títol i botó "enrere"
        setupToolbar(binding.toolbar, getString(R.string.watched_movies), showBack = true)

        adapter = WatchedAdapter { watchedMovie ->
            val apiMovie = watchedMovie.toApiMovie()
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(Constants.Extras.EXTRA_MOVIE, apiMovie)
            startActivity(intent)
        }

        binding.rvWatched.layoutManager = LinearLayoutManager(this)
        binding.rvWatched.adapter = adapter

        watchedViewModel.loadWatched()

        watchedViewModel.watchedMovies.observe(this) { movies ->
            adapter.submitList(movies)
        }

        watchedViewModel.errorMessage.observe(this) { msg ->
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
