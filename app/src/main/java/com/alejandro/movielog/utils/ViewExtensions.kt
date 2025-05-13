package com.alejandro.movielog.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.alejandro.movielog.R

/**
 * Extensió per carregar una imatge en un ImageView des d'una URL.
 * Utilitza Glide amb placeholder i forma circular.
 *
 * @param url La URL de la imatge a carregar.
 */
fun ImageView.loadCircularImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_user)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}


