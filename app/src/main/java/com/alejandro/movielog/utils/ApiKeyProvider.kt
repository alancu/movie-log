package com.alejandro.movielog.utils

import android.content.Context
import com.alejandro.movielog.R

object ApiKeyProvider {
    fun getApiKey(context: Context): String =
        context.getString(R.string.tmdb_api_key)
}