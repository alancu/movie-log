package com.alejandro.movielog.data

import com.alejandro.movielog.retrofit.RetrofitClient

// Classe que actua com a font de dades per a les pel·lícules
class MovieRepository {
    private val apiKey = "4a5cbff143eda90e596622878aaa6354"
    // 'suspend' indica que aquesta funció s'executa asíncronament
    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }
}
