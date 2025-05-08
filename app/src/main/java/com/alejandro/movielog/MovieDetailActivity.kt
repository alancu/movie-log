package com.alejandro.movielog

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.movielog.data.Movie
import com.bumptech.glide.Glide

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

        // posem la imatge
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
            .into(posterImageView)
    }
}
