package com.alejandro.movielog.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.core.net.toUri

// Aquest fitxer conté funcions d'extensió útils per a Context/Activity.
// Són funcions que  afegeixes a una classe sense haver de modificar-la

/**
 * Funció d'extensió per navegar fàcilment a una altra Activity.
 */
inline fun <reified T : Activity> Context.navigateTo(vararg extras: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    extras.forEach {
        when (val value = it.second) {
            is Int -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
        }
    }
    startActivity(intent)
}

/**
 * Funció d'extensió per obrir un enllaç extern (URL) amb el navegador
 */
fun Context.navigateToUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}
