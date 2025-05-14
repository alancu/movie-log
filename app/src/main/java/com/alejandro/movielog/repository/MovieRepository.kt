package com.alejandro.movielog.repository

import android.content.Context
import com.alejandro.movielog.data.response.MovieResponse
import com.alejandro.movielog.data.network.RetrofitClient
import com.alejandro.movielog.utils.ApiKeyProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Repositori que centralitza les operacions de xarxa relacionades amb les pel·lícules.
 */
class MovieRepository @Inject constructor(@ApplicationContext private val context: Context){
    private val apiKey = ApiKeyProvider.getApiKey(context)
    /**
     * Obté una llista de pel·lícules populars des de l'API.
     */
    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }

    /**
     * Busca pel·lícules a partir d'un text de consulta.
     */
    suspend fun searchMovies(query: String): MovieResponse {
        return RetrofitClient.apiService.searchMovies(apiKey, query)
    }
}

