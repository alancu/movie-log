package com.alejandro.movielog.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.repository.UserMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona les pel·lícules guardades per l'usuari a Firestore.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: UserMovieRepository
) : ViewModel() {

    /**
     * Guarda una pel·lícula a Firestore dins de la col·lecció de favorits de l'usuari.
     */
    fun addFavorite(movie: SavedMovie) {
        viewModelScope.launch {
            try {
                repository.saveFavorite(movie)
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error afegint a favorits", e)
                e.printStackTrace()
            }
        }
    }
}
