package com.alejandro.movielog.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Classe de dades que representa una pel·lícula obtinguda de l'API de TMDb.
 * Esta classe s'utilitza per a mostrar pel·lícules en temps real, no per a emmagatzemar-les.
 */
@Parcelize // Permet passar l'objecte entre activitats/fragments via intents (implementa Parcelable automàticament)
data class ApiMovie(
    val id: Int, // ID únic de la pel·lícula (proporcionat per TMDb)
    val title: String,
    val overview: String,
    @SerializedName("poster_path") // Necessari perquè el JSON de l'API usa "poster_path", però ací la variable es diu "posterPath"
    val posterPath: String
) : Parcelable // Necessari per passar objectes entre activitats amb intents
