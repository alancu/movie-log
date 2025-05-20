package com.alejandro.movielog.repository

import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.data.model.WatchedMovie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositori que gestiona les pel·lícules guardades per cada usuari a Firestore.
 * @Singleton fa que només hi haja una instància d'aquest repositori per a tota l'app.
 * S'injecta amb Hilt automàticament (@Inject constructor)
 */
@Singleton
class UserMovieRepository @Inject constructor() {

    // Obté l'ID de l'usuari actual (si no hi ha usuari autenticat, llança error)
    private val uid: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("Cal iniciar sessió per guardar pel·lícules")

    //  col·lecció de pel·lícules favorites de l'usuari en Firestore
    private val favoritesCollection
        get() = Firebase.firestore
            .collection("users")
            .document(uid)
            .collection("favorites")

    // col·lecció de pel·lícules vistes de l'usuari en Firestore
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
            .await() //await fa que la funció siga asíncrona i puga ser usada amb corrutines (suspend)
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

    // Comprova si una pel·lícula està guardada com a favorita.
    suspend fun isFavorite(movieId: Int): Boolean {
        val snapshot = favoritesCollection.document(movieId.toString()).get().await()
        return snapshot.exists()
    }

    // Comprova si una pel·lícula està marcada com a vista
    suspend fun isWatched(movieId: Int): Boolean {
        val snapshot = watchedCollection.document(movieId.toString()).get().await()
        return snapshot.exists()
    }

    // Elimina una pel·lícula del llistat de favorites
    suspend fun deleteFavorite(movieId: Int) {
        favoritesCollection.document(movieId.toString()).delete().await()
    }

    // Elimina una pel·lícula del llistat de vistes
    suspend fun removeWatched(movieId: Int) {
        watchedCollection.document(movieId.toString()).delete().await()
    }

    // Obté la llista de favorites guardades per l'usuari
    suspend fun getFavorites(): List<SavedMovie> {
        val snapshot = favoritesCollection.get().await()
        // ací transformem cada document de Firestore en SavedMovie
        return snapshot.documents.mapNotNull { it.toObject(SavedMovie::class.java) }
    }

    // Obté totes les pel·lícules vistes, ordenades de més recent a més antiga
    suspend fun getWatched(): List<WatchedMovie> {
        // .orderBy ordena per la data de visualització
        val snapshot = watchedCollection
            .orderBy("watchedAt", Query.Direction.DESCENDING)
            .get().await()
        return snapshot.documents.mapNotNull { it.toObject(WatchedMovie::class.java) }
    }
}
