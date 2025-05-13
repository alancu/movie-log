package com.alejandro.movielog.repository

import com.alejandro.movielog.data.response.MovieResponse
import com.alejandro.movielog.data.network.RetrofitClient

/**
 * Repositori que centralitza les operacions de xarxa relacionades amb les pel·lícules.
 */
class MovieRepository(private val apiKey: String) {

    /**
     * Obté una llista de pel·lícules populars des de l'API.
     */
    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }

    /**
     * Busca pel·lícules a partir d'un text de consulta.
     */
    suspend fun searchMovies(query: String): MovieResponse {
        return RetrofitClient.apiService.searchMovies(apiKey, query)
    }
}
