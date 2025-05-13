package com.alejandro.movielog.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton que configura Retrofit per fer peticions a l'API de TMDb.
 */
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: TMDbApiService = retrofit.create(TMDbApiService::class.java)
}
