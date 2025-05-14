package com.alejandro.movielog.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.Movie
import com.alejandro.movielog.ui.detail.MovieDetailActivity

/**
 * Extensió per carregar una imatge en un ImageView des d'una URL.
 */
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

/**
 * Extensió per carregar una imatge en un ImageView des d'una URL.
 * Utilitza Glide amb placeholder i forma circular.
 */
fun ImageView.loadCircularImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_user)
        .circleCrop()
        .into(this)
}


/**
 * Extensió de Context per obrir l'activitat de detalls d'una pel·lícula.
 */
fun Context.openMovieDetail(movie: Movie) {
    val intent = Intent(this, MovieDetailActivity::class.java)
    intent.putExtra(Constants.Extras.EXTRA_MOVIE, movie)
    startActivity(intent)
}


