package com.alejandro.movielog.data.network

import com.alejandro.movielog.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton que configura Retrofit per fer peticions a l'API de TMDb.
 * Un singleton és un objecte que només té una instància durant tota l'execució de l'app.
 */
object RetrofitClient {
    // Instància única de Retrofit configurada per a TMDb
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Indiquem que el JSON es convertirà a objectes de dades amb Gson
        .build()

    // Instància de la interfície que defineix les crides a l'API (TMDbApiService)
    val apiService: TMDbApiService = retrofit.create(TMDbApiService::class.java)
}
