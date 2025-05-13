package com.alejandro.movielog.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object = singleton
object RetrofitClient {
    // Configura Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/") // URL base de l'API
        .addConverterFactory(GsonConverterFactory.create()) // Tradueix JSON a Kotlin
        .build()

    // Crea la implementació de l'API definida a TMDbApiService
    val apiService: TMDbApiService = retrofit.create(TMDbApiService::class.java)
}
