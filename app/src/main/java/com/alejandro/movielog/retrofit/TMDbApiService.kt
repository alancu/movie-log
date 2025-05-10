package com.alejandro.movielog.retrofit

import com.alejandro.movielog.data.MovieResponse
import com.alejandro.movielog.data.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// interfície que defineix les peticions a l'API de TMDb
interface TMDbApiService {
    // petició GET a l'endpoint 'movie/popular' de l'API per a obtindre pel·lícules populars
    @GET("movie/popular")
    // 'suspend' indica que és una funció que es pot executar de manera asíncrona
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        // substituïm {movie_id} a l'URL pel valor de movieId
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): VideoResponse
}