package com.alejandro.movielog.data

// per a l'API TMDb els vídeos van "a part" de les pel·lícules i per això cal una classe pròpia
data class VideoResponse (
    val id: Int,
    val results: List<Video>
)
