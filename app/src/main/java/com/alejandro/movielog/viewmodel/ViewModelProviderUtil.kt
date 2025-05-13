package com.alejandro.movielog.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.alejandro.movielog.R
import com.alejandro.movielog.repository.MovieRepository

/**
 * Utilitat per crear instàncies de ViewModel amb dependències injectades.
 * Aquesta classe centralitza la creació de MovieViewModel, evitant duplicació
 * de codi a les activitats i fragments que el necessiten.
 */
object ViewModelProviderUtil {

    /**
     * Retorna una instància de MovieViewModel amb el MovieRepository configurat.
     */
    fun provideMovieViewModel(owner: ViewModelStoreOwner, context: Context): MovieViewModel {
        // Obté la clau d’API des del fitxer de recursos
        val apiKey = context.getString(R.string.tmdb_api_key)

        // Crea el repositori amb la clau d’API
        val repository = MovieRepository(apiKey)

        // Crea la fàbrica del ViewModel amb el repositori
        val factory = MovieViewModelFactory(repository)

        // Retorna una instància de MovieViewModel
        return ViewModelProvider(owner, factory)[MovieViewModel::class.java]
    }
}
