package com.alejandro.movielog.data

import com.google.gson.annotations.SerializedName

// 'data class' és una classe de Kotlin que per guardar dades
// crea automàticament funcions útils
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    // @SerializedName per què en el JSON, este camp es diu 'poster_path', però ací posterPath
    @SerializedName("poster_path")
    val posterPath: String?
)