package com.alejandro.movielog.data.response

import com.alejandro.movielog.data.model.ApiMovie

/**
 * Classe de resposta per a l'endpoint de pel·lícules.
 * Conté una llista de pel·lícules que s'obté de l'API.
 */
data class MovieResponse (
    val results: List<ApiMovie>
)
