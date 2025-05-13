package com.alejandro.movielog.data.model

/**
 * Classe que representa el tràiler d'una pel·lícula
 */
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)
