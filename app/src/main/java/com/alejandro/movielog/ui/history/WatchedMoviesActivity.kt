package com.alejandro.movielog.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
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
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activitat que mostra la llista de pel·lícules vistes per l’usuari (historial)
 * Utilitza un RecyclerView amb WatchedAdapter
 * La llista es carrega des de Firestore mitjançant el ViewModel.
 */
@AndroidEntryPoint
class WatchedMoviesActivity : BaseActivity() {

    private lateinit var binding: ActivityWatchedMoviesBinding
    private val watchedViewModel: WatchedViewModel by viewModels()
    private lateinit var adapter: WatchedAdapter
    private lateinit var shimmerViewContainer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchedMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimmerViewContainer = findViewById(R.id.shimmer_view_container)

        // Configura la Toolbar amb títol i botó "enrere"
        setupToolbar(binding.toolbar, getString(R.string.watched_movies), showBack = true)

        // Inicialitza l’adaptador per a les pel·lícules vistes
        adapter = WatchedAdapter { watchedMovie ->
            val apiMovie = watchedMovie.toApiMovie()
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(Constants.Extras.EXTRA_MOVIE, apiMovie)
            startActivity(intent)
        }
        binding.rvWatched.layoutManager = LinearLayoutManager(this)
        binding.rvWatched.adapter = adapter

        // Mostra shimmer en iniciar càrrega
        shimmerViewContainer.visibility = View.VISIBLE
        shimmerViewContainer.startShimmer()
        binding.rvWatched.visibility = View.GONE

        watchedViewModel.loadWatched(this)

        watchedViewModel.watchedMovies.observe(this) { movies ->
            adapter.submitList(movies)
            // Quan tens dades, amaga shimmer i mostra la llista
            shimmerViewContainer.stopShimmer()
            shimmerViewContainer.visibility = View.GONE
            binding.rvWatched.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()
        // tornem a carregar el llistat quan tornem d'una fitxa
        watchedViewModel.loadWatched(this)
    }
}
