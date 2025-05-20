package com.alejandro.movielog.data.response

import com.alejandro.movielog.data.model.ApiVideo

/**
 * Classe de dades que representa la resposta de l'API quan demanem els vídeos d'una pel·lícula.
 * El camp "results" conté una llista de vídeos (trailers, teasers, etc.).
 */
data class VideoResponse (
    val id: Int, // ID de la pel·lícula a la qual corresponen els vídeos (pot no utilitzar-se, però l'API el retorna)
    val results: List<ApiVideo> // Llista de vídeos associats a aquesta pel·lícula
)
