package com.alejandro.movielog.data.model

import com.google.firebase.Timestamp

/**
 * Classe que representa una pel·lícula marcada com a vista per un usuari a Firestore
 */
data class WatchedMovie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val watchedAt: Timestamp = Timestamp.now()
) {
    fun toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "overview" to overview,
        "posterPath" to posterPath,
        "watchedAt" to watchedAt
    )
}
