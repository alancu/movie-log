package com.alejandro.movielog.repository

import com.alejandro.movielog.data.response.MovieResponse
import com.alejandro.movielog.data.network.RetrofitClient

class MovieRepository(private val apiKey: String) {

    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }

    suspend fun searchMovies(query: String): MovieResponse {
        return RetrofitClient.apiService.searchMovies(apiKey, query)
    }
}