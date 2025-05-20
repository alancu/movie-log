package com.alejandro.movielog.data.model

import com.google.firebase.Timestamp

/**
 * Classe que representa una pel·lícula marcada com a vista per l'usuari (historial de vistes).
 */
data class WatchedMovie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val watchedAt: Timestamp = Timestamp.now() // Timestamp automàtic: es posa el moment actual en crear l'objecte
) {
    /**
     * Converteix l'objecte a Map per a guardar-lo en Firestore.
     * Igual que en SavedMovie, cal fer-ho així perquè Firestore només accepta tipus bàsics.
     */
    fun toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "title" to title,
        "overview" to overview,
        "posterPath" to posterPath,
        "watchedAt" to watchedAt
    )
}
