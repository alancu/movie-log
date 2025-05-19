package com.alejandro.movielog.utils

import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.data.model.SavedMovie

/**
 * Funció per a convertir una ApiMovie (rebuda de l'API TMDb)
 * en una SavedMovie (per a guardar-la en Firestore).
 */
fun ApiMovie.toSavedMovie(): SavedMovie = SavedMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)

fun SavedMovie.toApiMovie(): ApiMovie = ApiMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)
