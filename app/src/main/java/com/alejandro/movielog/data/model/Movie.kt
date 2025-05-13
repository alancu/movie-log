package com.alejandro.movielog.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Classe de dades que representa una pel·lícula rebuda de l'API TMDb.
 * Es fa parcelable per poder passar-la entre activitats mitjançant intents.
 */
@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
) : Parcelable