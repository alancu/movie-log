package com.alejandro.movielog.ui.history

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alejandro.movielog.databinding.ActivityWatchedMoviesBinding
import com.alejandro.movielog.R
import com.alejandro.movielog.ui.components.WatchedAdapter
import com.alejandro.movielog.ui.detail.MovieDetailActivity
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.toApiMovie
import com.alejandro.movielog.viewmodel.WatchedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchedMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchedMoviesBinding
    private val watchedViewModel: WatchedViewModel by viewModels()
    private lateinit var adapter: WatchedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchedMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.watched_movies)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
