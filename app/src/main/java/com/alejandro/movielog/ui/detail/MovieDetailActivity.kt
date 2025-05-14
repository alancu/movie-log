package com.alejandro.movielog.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.Movie
import com.alejandro.movielog.data.network.RetrofitClient
import com.alejandro.movielog.utils.ApiKeyProvider
import com.alejandro.movielog.utils.Constants
import com.alejandro.movielog.utils.loadImage
import com.alejandro.movielog.utils.navigateToUrl
import kotlinx.coroutines.launch

/**
 * Activitat que mostra els detalls d'una pel·lícula i permet veure el tràiler si està disponible.
 */
class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Recupera l'objecte Movie passat a l'Intent
        @Suppress("DEPRECATION")
        val movie: Movie? = intent.getParcelableExtra(Constants.EXTRA_MOVIE)

        val titleTextView: TextView = findViewById(R.id.tv_movie_detail_title)
        val descriptionTextView: TextView = findViewById(R.id.tv_movie_detail_description)
        val posterImageView: ImageView = findViewById(R.id.iv_movie_detail_poster)

        titleTextView.text = movie?.title
        descriptionTextView.text = movie?.overview

        // Posem la imatge del pòster
        posterImageView.loadImage("${Constants.POSTER_BASE_URL}${movie?.posterPath}")

        // Carreguem el tràiler
        movie?.id?.let { loadMovieTrailer(it) }
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
                    val youtubeUrl = "${Constants.YOUTUBE_WATCH_URL}${video.key}"

                    val trailerButton: Button = findViewById(R.id.button_watch_trailer)
                    trailerButton.visibility = View.VISIBLE
                    trailerButton.setOnClickListener {
                        navigateToUrl(youtubeUrl)
                    }
                }


            } catch (e: Exception) {
                // Maneig d'errors
                Log.e("TrailerError", "Error carregant el tràiler", e)
            }
        }
    }
}