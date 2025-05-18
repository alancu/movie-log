package com.alejandro.movielog.repository

import com.alejandro.movielog.data.model.SavedMovie
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

    private val favoritesCollection
        get() = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("favorites")

    /**
     * Guarda o actualitza una pel·lícula dins dels favorits de l'usuari.
     */
    suspend fun saveFavorite(movie: SavedMovie) {
        favoritesCollection
            .document(movie.id.toString())
            .set(movie.toMap())
            .await()
    }

    //TODO: llegir, eliminar, etc...
}
