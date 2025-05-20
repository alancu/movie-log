package com.alejandro.movielog.data.response

import com.alejandro.movielog.data.model.ApiMovie

/**
 * Classe de dades que representa la resposta de l'API quan demanem una llista de pel·lícules.
 * Només té un camp "results" perquè l'API TMDb sempre torna una llista de pel·lícules dins d'aquest array.
 */
data class MovieResponse (
    val results: List<ApiMovie> // Llista de pel·lícules que ha tornat l'API en aquesta petició
)
