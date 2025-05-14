package com.alejandro.movielog.data.network

import com.alejandro.movielog.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton que configura Retrofit per fer peticions a l'API de TMDb.
 */
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: TMDbApiService = retrofit.create(TMDbApiService::class.java)
}
