package com.alejandro.movielog.data.response

import com.alejandro.movielog.data.model.Movie

// classe per a guardar resposta de l'API
// "data class" s'utilitza per a representar dades i crea algunes funcions per defecte
data class MovieResponse (
    val results: List<Movie>
)
