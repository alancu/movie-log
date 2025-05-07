package com.alejandro.movielog.retrofit

import com.alejandro.movielog.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("4a5cbff143eda90e596622878aaa6354") apiKey: String): MovieResponse
}