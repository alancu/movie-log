package com.alejandro.movielog.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.Movie
import com.alejandro.movielog.data.network.RetrofitClient
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Recupera l'objecte Movie passat a l'Intent
        @Suppress("DEPRECATION")
        val movie: Movie? = intent.getParcelableExtra("movie")

        val titleTextView: TextView = findViewById(R.id.tv_movie_detail_title)
        val descriptionTextView: TextView = findViewById(R.id.tv_movie_detail_description)
        val posterImageView: ImageView = findViewById(R.id.iv_movie_detail_poster)

        titleTextView.text = movie?.title
        descriptionTextView.text = movie?.overview

        // Posem la imatge del pòster
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(posterImageView)

        // Carreguem el tràiler
        movie?.id?.let { loadMovieTrailer(it) }
    }

    // Funció per carregar el tràiler de la pel·lícula
    private fun loadMovieTrailer(movieId: Int) {
        lifecycleScope.launch {
            try {
                // fer una crida a l'API per obtenir els vídeos
                val response = RetrofitClient.apiService.getMovieVideos(movieId, getString(R.string.tmdb_api_key))

                // Buscar el tràiler en YouTube
                val youtubeTrailer = response.results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }

                youtubeTrailer?.let {
                    val youtubeUrl = "https://www.youtube.com/watch?v=${it.key}"
                    val intent = Intent(Intent.ACTION_VIEW, youtubeUrl.toUri())

                    // Mostrar el botó de veure tràiler
                    val trailerButton: Button = findViewById(R.id.button_watch_trailer)
                    trailerButton.visibility = View.VISIBLE
                    trailerButton.setOnClickListener {
                        startActivity(intent)
                    }
                }

            } catch (e: Exception) {
                // Maneig d'errors
                Log.e("TrailerError", "Error carregant el tràiler", e)
            }
        }
    }
}