package com.alejandro.movielog.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.ui.detail.MovieDetailActivity

/**
 * Conté funcions d'extensió per a ImageView i Context relacionades amb la UI.
 */

// Carrega una imatge en un ImageView des d'una URL (sense placeholder)
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}

// Carrega una imatge circular (amb placeholder) en un ImageView (foto de perfil, etc.)
fun ImageView.loadCircularImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_user)
        .circleCrop()
        .into(this)
}

/**
 * Extensió de Context per obrir directament l'activitat de detalls d'una pel·lícula.
 */
fun Context.openMovieDetail(apiMovie: ApiMovie) {
    val intent = Intent(this, MovieDetailActivity::class.java)
    intent.putExtra(Constants.Extras.EXTRA_MOVIE, apiMovie)
    startActivity(intent)
}
