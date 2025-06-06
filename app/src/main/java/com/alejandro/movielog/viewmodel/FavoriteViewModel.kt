package com.alejandro.movielog.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.SavedMovie
import com.alejandro.movielog.repository.UserMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel per gestionar les pel·lícules guardades en favorits per l'usuari.
 * Es comunica amb UserMovieRepository per guardar, eliminar i comprovar favorits a Firestore.
 * Exposa LiveData per saber si una pel·lícula està en favorits i per mostrar la llista de favorits.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: UserMovieRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _favoriteMovies = MutableLiveData<List<SavedMovie>>()
    val favoriteMovies: LiveData<List<SavedMovie>> = _favoriteMovies

    /**
     * Afig una pel·lícula a favorits (guarda a Firestore).
     */
    fun addFavorite(context: Context, movie: SavedMovie) {
        viewModelScope.launch {
            try {
                repository.saveFavorite(movie)
                _isFavorite.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = context.getString(R.string.error_adding_favorite) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Comprova si una pel·lícula està en favorits.
     */
    fun checkIfFavorite(context: Context, movieId: Int) {
        viewModelScope.launch {
            try {
                _isFavorite.value = repository.isFavorite(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = context.getString(R.string.error_checking_favorite) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Elimina una pel·lícula de favorits.
     */
    fun removeFavorite(context: Context, movieId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteFavorite(movieId)
                _isFavorite.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = context.getString(R.string.error_removing_favorite) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Carrega totes les pel·lícules guardades en favorits.
     */
    fun loadFavorites(context: Context) {
        viewModelScope.launch {
            try {
                _favoriteMovies.value = repository.getFavorites()
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = context.getString(R.string.error_loading_favorites) + ": ${e.localizedMessage}"
            }
        }
    }
}
