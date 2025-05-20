package com.alejandro.movielog.repository

import android.content.Context
import com.alejandro.movielog.data.response.MovieResponse
import com.alejandro.movielog.data.network.RetrofitClient
import com.alejandro.movielog.utils.ApiKeyProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Repositori encarregat de recuperar pel·lícules des de l'API de TMDb.
 * Un repositori és una classe que s'encarrega d'accedir a les dades
 * així la resta de l'appnomés ha de demanar-li les dades al repositori, sense saber d'on venen.
 */
//S'injecta el context d'aplicació (@ApplicationContext), necessari per a obtindre la clau d'API.
class ApiMovieRepository @Inject constructor(@ApplicationContext private val context: Context) {
    // Obté la clau d'API de TMDb utilitzant un helper (ApiKeyProvider)
    private val apiKey = ApiKeyProvider.getApiKey(context)

    /**
     * Obté una llista de pel·lícules populars des de l'API.
     */
    suspend fun getPopularMovies(): MovieResponse {
        return RetrofitClient.apiService.getPopularMovies(apiKey)
    }

    /**
     * Busca pel·lícules a partir d'una consulta a l'API.
     */
    suspend fun searchMovies(query: String): MovieResponse {
        return RetrofitClient.apiService.searchMovies(apiKey, query)
    }
}
