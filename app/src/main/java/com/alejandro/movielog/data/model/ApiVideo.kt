package com.alejandro.movielog.data.model

/**
 * Classe de dades que representa un vídeo associat a una pel·lícula en TMDb.
 * No es pot obtindre directament el vídeo des de ApiMovie, perquè cada pel·lícula pot tindre zero, un o més vídeos (tràilers, teasers, clips...).
 * Els vídeos s'obtenen amb una crida API separada
 */
data class ApiVideo(
    val id: String,    // ID intern de TMDb per al vídeo
    val key: String,   // Clau de vídeo en el lloc (YouTube, etc). S'usa per fer l'enllaç al vídeo
    val name: String,
    val site: String,  // Lloc on està allotjat el vídeo
    val type: String   // Tipus de vídeo: "Trailer", "Teaser", etc.
)
