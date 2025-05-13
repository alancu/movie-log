package com.alejandro.movielog.repository

import com.alejandro.movielog.data.MovieResponse
import com.alejandro.movielog.retrofit.RetrofitClient

class MovieRepository(private val apiKey: String) {

    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }

    suspend fun searchMovies(query: String): MovieResponse {
        return RetrofitClient.apiService.searchMovies(apiKey, query)
    }
}