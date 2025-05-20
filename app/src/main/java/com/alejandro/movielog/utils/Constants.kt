package com.alejandro.movielog.utils

/**
 * Fitxer amb constants globals de l'aplicació.
 * S'usen per centralitzar els valors reutilitzables.
 */
object Constants {
    object Api {
        // URL base per a l'API de TMDb
        const val BASE_URL = "https://api.themoviedb.org/3/"
        // Base per carregar pòsters
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
        // Prefix per a vídeos de YouTube
        const val YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v="
    }
    // Claus identificadores per passar dades entre pantalles via intents (extras d'Intent)
    object Extras {
        // Clau per passar pel·lícules entre activitats
        const val EXTRA_MOVIE = "movie"
        // Clau per passar la consulta de cerca
        const val EXTRA_QUERY = "query"
    }
}
