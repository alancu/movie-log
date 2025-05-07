package com.alejandro.movielog.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val apiService: TMDbApiService by lazy { //by lazy inicialitza la variable només quan siga necessària
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()) //convertim les respostes de l'API en JSON a objectes Kotlin
            .build()
            .create(TMDbApiService::class.java)
    }
}
