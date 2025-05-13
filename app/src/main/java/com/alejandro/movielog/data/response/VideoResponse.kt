package com.alejandro.movielog.data.response

import com.alejandro.movielog.data.model.Video

/**
 * Classe de resposta per a l'endpoint de vídeos d'una pel·lícula.
 * Conté una llista de vídeos associats.
 */
data class VideoResponse (
    val id: Int,
    val results: List<Video>
)