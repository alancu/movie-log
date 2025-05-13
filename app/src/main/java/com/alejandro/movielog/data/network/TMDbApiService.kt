package com.alejandro.movielog.data.network

import com.alejandro.movielog.data.response.MovieResponse
import com.alejandro.movielog.data.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfície que defineix les crides HTTP a l'API de TMDb mitjançant Retrofit.
 */
interface TMDbApiService {

    /**
     * Retorna una llista de pel·lícules populars.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    /**
     * Busca pel·lícules a partir d'un text de consulta.
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieResponse

    /**
     * Obté els vídeos d'una pel·lícula donat el seu ID.
     */
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): VideoResponse
}
