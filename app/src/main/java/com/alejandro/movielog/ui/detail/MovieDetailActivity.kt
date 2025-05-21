package com.alejandro.movielog.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.data.network.RetrofitClient
import com.alejandro.movielog.ui.base.BaseActivity
import com.alejandro.movielog.utils.ApiKeyProvider
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import com.alejandro.movielog.utils.navigateToUrl
import com.alejandro.movielog.utils.toSavedMovie
import com.alejandro.movielog.utils.toWatchedMovie
import com.alejandro.movielog.viewmodel.FavoriteViewModel
import com.alejandro.movielog.viewmodel.WatchedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.appcompat.widget.Toolbar

/**
 * Activitat que mostra els detalls d'una pel·lícula.
 * Esta classe hereta de BaseActivity per a compartir la Toolbar i el menú d'usuari.
 */
@AndroidEntryPoint
class MovieDetailActivity : BaseActivity() {

    // ViewModels per gestionar favorits i historial de vistes
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val watchedViewModel: WatchedViewModel by viewModels()
    private var apiMovie: ApiMovie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Obté la pel·lícula passada per intent
        @Suppress("DEPRECATION")
        apiMovie = intent.getParcelableExtra(Constants.Extras.EXTRA_MOVIE)

        val movieTitle = apiMovie?.title ?: getString(R.string.movie_detail)

        // Configura la Toolbar amb el títol de la pel·lícula
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setupToolbar(toolbar, movieTitle, showBack = true)

        val titleTextView: TextView = findViewById(R.id.tv_movie_detail_title)
        val descriptionTextView: TextView = findViewById(R.id.tv_movie_detail_description)
        val posterImageView: ImageView = findViewById(R.id.iv_movie_detail_poster)
        val fabFavorite: FloatingActionButton = findViewById(R.id.fab_favorite)
        val fabWatched: FloatingActionButton = findViewById(R.id.fab_watched)

        // Mostra dades de la pel·lícula
        titleTextView.text = apiMovie?.title
        descriptionTextView.text = apiMovie?.overview
        posterImageView.loadImage("${Constants.Api.POSTER_BASE_URL}${apiMovie?.posterPath}")

        // Carrega el tràiler (si existeix)
        apiMovie?.id?.let { loadMovieTrailer(it) }

        // Observa si la pel·lícula està a favorits o vistes, i actualitza els botons
        favoriteViewModel.isFavorite.observe(this) { isFav ->
            fabFavorite.setImageResource(
                if (isFav) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
            )
        }
        watchedViewModel.isWatched.observe(this) { isWatched ->
            fabWatched.setImageResource(
                if (isWatched) R.drawable.ic_eye_filled else R.drawable.ic_eye
            )
        }

        // Comprova si la pel·lícula està guardada
        apiMovie?.id?.let { movieId ->
            favoriteViewModel.checkIfFavorite(this, movieId)
            watchedViewModel.checkIfWatched(movieId)
        }

        // Accions dels botons flotants
        fabFavorite.setOnClickListener {
            apiMovie?.let { movie ->
                if (favoriteViewModel.isFavorite.value == true) {
                    favoriteViewModel.removeFavorite(this, movie.id)
                    Toast.makeText(this, getString(R.string.removed_from_favorites), Toast.LENGTH_SHORT).show()
                } else {
                    favoriteViewModel.addFavorite(this, movie.toSavedMovie())
                    Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
                }
            }
        }
        fabWatched.setOnClickListener {
            apiMovie?.let { movie ->
                if (watchedViewModel.isWatched.value == true) {
                    watchedViewModel.removeWatched(movie.id)
                    Toast.makeText(this, getString(R.string.removed_from_watched), Toast.LENGTH_SHORT).show()
                } else {
                    watchedViewModel.addWatched(movie.toWatchedMovie())
                    Toast.makeText(this, getString(R.string.marked_as_watched), Toast.LENGTH_SHORT).show()
                }
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

    /**
     * Carrega el tràiler de la pel·lícula si està disponible a TMDb.
     * Mostra el botó per veure'l a YouTube si existeix.
     */
    private fun loadMovieTrailer(movieId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService
                    .getMovieVideos(movieId, ApiKeyProvider.getApiKey(this@MovieDetailActivity))

                // Busca el primer vídeo de tipus "Trailer" i site "YouTube"
                val youtubeTrailer = response.results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }

                youtubeTrailer?.let { video ->
                    val trailerButton: Button = findViewById(R.id.button_watch_trailer)
                    trailerButton.visibility = View.VISIBLE
                    trailerButton.setOnClickListener {
                        navigateToUrl("${Constants.Api.YOUTUBE_WATCH_URL}${video.key}")
                    }
                }
            } catch (e: Exception) {
                Log.e("TrailerError", getString(R.string.error_loading_trailer), e)
            }
        }
    }
}
