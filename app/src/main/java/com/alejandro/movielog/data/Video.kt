package com.alejandro.movielog.data

// per a l'API TMDb els vídeos van "a part" de les pel·lícules i per això cal una classe pròpia
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)
