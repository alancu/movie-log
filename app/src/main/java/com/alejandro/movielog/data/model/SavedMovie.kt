package com.alejandro.movielog.data.model

import com.google.firebase.Timestamp

/**
 * Classe que representa una pel·lícula guardada per un usuari a Firestore
 */
data class SavedMovie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val savedAt: Timestamp? = null
) {
    fun toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "overview" to overview,
        "posterPath" to posterPath,
        "savedAt" to (savedAt ?: Timestamp.now())
    )

}
