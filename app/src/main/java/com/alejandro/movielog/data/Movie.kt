package com.alejandro.movielog.data

import com.google.gson.annotations.SerializedName

data class Movie (
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("poster_path")
    val posterPath: String?
)