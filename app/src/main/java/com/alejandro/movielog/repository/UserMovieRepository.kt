package com.alejandro.movielog.repository

import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.data.model.WatchedMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositori que gestiona les pel·lícules guardades per cada usuari a Firestore.
 */
@Singleton
class UserMovieRepository @Inject constructor() {

    private val uid: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("Cal iniciar sessió per guardar pel·lícules")

    // col·lecció de pel·lícules favorites
    private val favoritesCollection
        get() = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("favorites")

    // col·lecció de pel·lícules vistes
    private val watchedCollection
        get() = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("watched")

    /**
     * Guarda o actualitza una pel·lícula dins de les favorites de l'usuari.
     */
    suspend fun saveFavorite(movie: SavedMovie) {
        favoritesCollection
            .document(movie.id.toString())
            .set(movie.toMap())
            .await()
    }

    /**
     * Guarda o actualitza una pel·lícula dins de les vistes de l'usuari.
     */
    suspend fun addWatched(movie: WatchedMovie) {
        watchedCollection
            .document(movie.id.toString())
            .set(movie.toMap())
            .await()
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        val snapshot = favoritesCollection.document(movieId.toString()).get().await()
        return snapshot.exists()
    }

    // Comprova si una pel·lícula està a vistes
    suspend fun isWatched(movieId: Int): Boolean {
        val snapshot = watchedCollection.document(movieId.toString()).get().await()
        return snapshot.exists()
    }

    suspend fun deleteFavorite(movieId: Int) {
        favoritesCollection.document(movieId.toString()).delete().await()
    }
    // Elimina una pel·lícula de vistes
    suspend fun removeWatched(movieId: Int) {
        watchedCollection.document(movieId.toString()).delete().await()
    }

    suspend fun getFavorites(): List<SavedMovie> {
        val snapshot = favoritesCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(SavedMovie::class.java) }
    }

    // obtín totes les pel·lícules vistes
    suspend fun getWatched(): List<WatchedMovie> {
        val snapshot = watchedCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(WatchedMovie::class.java) }
    }
}
