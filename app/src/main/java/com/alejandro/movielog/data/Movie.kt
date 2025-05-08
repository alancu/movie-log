package com.alejandro.movielog.data

import com.google.gson.annotations.SerializedName

// classe que representa una pel·lícula rebuda de l'API
// "data class" s'utilitza per a representar dades i crea algunes funcions per defecte
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    // @SerializedName perquè en el JSON aquest camp es diu 'poster_path', però ací l'anomenem posterPath
    @SerializedName("poster_path")
    val posterPath: String
)