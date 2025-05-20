package com.alejandro.movielog.data.model

import com.google.firebase.Timestamp

/**
 * Classe que representa una pel·lícula guardada per l'usuari a Firestore.
 */
data class SavedMovie(
    val id: Int = 0, // ID únic de la pel·lícula dins de TMDb. Cal per identificar la pel·lícula a la BBDD
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val savedAt: Timestamp? = null // Timestamp de Firestore; pot ser null si encara no s'ha guardat a la BBDD
) {
    /**
     * Converteix la pel·lícula a un Map per guardar-la en Firestore.
     * Firestore no permet guardar objectes complexos directament (només accepta tipus bàsics),
     * així que ho "mapegem" manualment
     */
    fun toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "overview" to overview,
        "posterPath" to posterPath,
        "savedAt" to (savedAt ?: Timestamp.now()) // Si no hi ha data, s'usa la d'ara mateix
    )
}
