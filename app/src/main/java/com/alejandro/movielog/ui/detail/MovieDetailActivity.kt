package com.alejandro.movielog.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.data.network.RetrofitClient
import com.alejandro.movielog.utils.ApiKeyProvider
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import com.alejandro.movielog.utils.navigateToUrl
import com.alejandro.movielog.utils.toSavedMovie
import com.alejandro.movielog.viewmodel.FavoriteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activitat que mostra els detalls d'una pel·lícula i permet veure el tràiler si està disponible.
 */
@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var apiMovie: ApiMovie? = null // si ho has passat per intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Recupera l'objecte Movie passat a l'Intent
        @Suppress("DEPRECATION")
        apiMovie = intent.getParcelableExtra(Constants.Extras.EXTRA_MOVIE)

        val titleTextView: TextView = findViewById(R.id.tv_movie_detail_title)
        val descriptionTextView: TextView = findViewById(R.id.tv_movie_detail_description)
        val posterImageView: ImageView = findViewById(R.id.iv_movie_detail_poster)

        titleTextView.text = apiMovie?.title
        descriptionTextView.text = apiMovie?.overview

        // Posem la imatge del pòster
        posterImageView.loadImage("${Constants.Api.POSTER_BASE_URL}${apiMovie?.posterPath}")

        // Carreguem el tràiler
        apiMovie?.id?.let { loadMovieTrailer(it) }

        // Botó de guardar
        val fabFavorite = findViewById<FloatingActionButton>(R.id.fab_favorite)
        fabFavorite.setOnClickListener {
            apiMovie?.let {
                val saved = it.toSavedMovie()
                favoriteViewModel.addFavorite(saved)
                Toast.makeText(this, "Afegida a favorits", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Fa una crida a l'API per obtenir el tràiler de la pel·lícula i obrir-lo en YouTube.
     */
    private fun loadMovieTrailer(movieId: Int) {
        lifecycleScope.launch {
            try {
                // fer una crida a l'API per obtenir els vídeos
                val response = RetrofitClient.apiService
                    .getMovieVideos(movieId, ApiKeyProvider.getApiKey(this@MovieDetailActivity))


                // Buscar el tràiler en YouTube
                val youtubeTrailer = response.results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }

                youtubeTrailer?.let { video ->
                    val youtubeUrl = "${Constants.Api.YOUTUBE_WATCH_URL}${video.key}"

                    val trailerButton: Button = findViewById(R.id.button_watch_trailer)
                    trailerButton.visibility = View.VISIBLE
                    trailerButton.setOnClickListener {
                        navigateToUrl(youtubeUrl)
                    }
                }


            } catch (e: Exception) {
                // Maneig d'errors
                Log.e("TrailerError", getString(R.string.error_loading_trailer), e)
            }
        }
    }
}