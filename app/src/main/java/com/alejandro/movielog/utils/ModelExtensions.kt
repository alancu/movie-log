package com.alejandro.movielog.utils

import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.data.model.WatchedMovie
import com.google.firebase.Timestamp

/**
 * Conté funcions d'extensió per convertir entre models (ApiMovie, SavedMovie, WatchedMovie).
 * Facilita passar dades entre l'API, la base de dades i la UI, sense repetir codi de conversió.
 */

// Converteix una ApiMovie (de l'API) a SavedMovie (per a guardar-la a Firestore)
fun ApiMovie.toSavedMovie(): SavedMovie = SavedMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)

// Converteix SavedMovie a ApiMovie (per a mostrar a la UI)
fun SavedMovie.toApiMovie(): ApiMovie = ApiMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)

// Converteix ApiMovie a WatchedMovie (afegint data actual)
fun ApiMovie.toWatchedMovie(date: Timestamp = Timestamp.now()): WatchedMovie = WatchedMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath,
    watchedAt = date
)

// Converteix WatchedMovie a ApiMovie
fun WatchedMovie.toApiMovie(): ApiMovie = ApiMovie(
    id = this.id,
    title = this.title,
    overview = this.overview,
    posterPath = this.posterPath
)
