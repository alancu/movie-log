package com.alejandro.movielog.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    /**
     * Guarda una pel·lícula a Firestore dins de la col·lecció de favorits de l'usuari.
     */
    fun addFavorite(movie: SavedMovie) {
        viewModelScope.launch {
            try {
                repository.saveFavorite(movie)
                _isFavorite.value = true
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error afegint a favorits", e)
                e.printStackTrace()
            }
        }
    }

    fun checkIfFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                _isFavorite.value = repository.isFavorite(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFavorite(movieId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteFavorite(movieId)
                _isFavorite.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
