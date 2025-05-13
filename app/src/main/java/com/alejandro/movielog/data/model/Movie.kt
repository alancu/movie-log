package com.alejandro.movielog.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// classe que representa una pel·lícula rebuda de l'API
// "data class" s'utilitza per a representar dades i crea algunes funcions per defecte
// ho fem "parcelable" per poder enviar-ho entre activitats
@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    // @SerializedName perquè en el JSON aquest camp es diu 'poster_path', però ací l'anomenem posterPath
    @SerializedName("poster_path")
    val posterPath: String
) : Parcelable