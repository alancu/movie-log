package com.alejandro.movielog.utils

import android.content.Context
import com.alejandro.movielog.R

/**
 * Classe d'utilitat per obtindre la clau d'API de TMDb des dels recursos de l'app (strings.xml)
 * Això permet guardar la clau fora del codi, i no hardcodejar-la, millorant la seguretat.
 */
// no la posem en Constants.kt per a no exposar-la directament al codi
object ApiKeyProvider {
    fun getApiKey(context: Context): String =
        context.getString(R.string.tmdb_api_key)
}
