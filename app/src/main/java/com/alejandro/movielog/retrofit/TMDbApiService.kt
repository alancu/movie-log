package com.alejandro.movielog.retrofit

import com.alejandro.movielog.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse
}