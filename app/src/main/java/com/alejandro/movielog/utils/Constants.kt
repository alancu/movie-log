package com.alejandro.movielog.utils

object Constants {
    object Api {
        // URL base per a Retrofit
        const val BASE_URL = "https://api.themoviedb.org/3/"

        // Ruta base per a carregar imatges
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"

        // Ruta base de YouTube
        const val YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v="
    }
    object Extras {
        // Clau que s’utilitza per passar una pel·lícula entre activitats
        const val EXTRA_MOVIE = "movie"

        // Clau que s’utilitza per passar la consulta de cerca
        const val EXTRA_QUERY = "query"
    }
}