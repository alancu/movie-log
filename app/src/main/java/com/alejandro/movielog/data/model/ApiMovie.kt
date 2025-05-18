package com.alejandro.movielog.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Classe de dades que representa una pel·lícula obtinguda de l'API de TMDb.
 * Esta classe s'utilitza per a mostrar pel·lícules en temps real, no per a emmagatzemar-les.
 */
@Parcelize
data class ApiMovie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
) : Parcelable
