package com.alejandro.movielog.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.core.net.toUri

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

fun Context.navigateToUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    startActivity(intent)
}