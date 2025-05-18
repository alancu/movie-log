package com.alejandro.movielog.data.model

/**
 * Classe de dades que representa un tràiler obtingut de l'API de TMDb.
 */
data class ApiVideo(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)
