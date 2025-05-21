package com.alejandro.movielog.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.R
import com.alejandro.movielog.data.model.WatchedMovie
import com.alejandro.movielog.repository.UserMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel per gestionar les pel·lícules marcades com a vistes per l'usuari.
 * Es comunica amb UserMovieRepository per afegir, eliminar i carregar llistats de pel·lícules vistes a Firestore.
 * Exposa LiveData per la llista de vistes i per saber si una pel·lícula concreta està marcada com a vista.
 */
@HiltViewModel
class WatchedViewModel @Inject constructor(
    private val repository: UserMovieRepository
) : ViewModel() {

    private val _isWatched = MutableLiveData<Boolean>()
    val isWatched: LiveData<Boolean> = _isWatched

    private val _watchedMovies = MutableLiveData<List<WatchedMovie>>()
    val watchedMovies: LiveData<List<WatchedMovie>> = _watchedMovies

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Marca una pel·lícula com a vista (guarda a Firestore).
     */
    fun addWatched(context: Context, movie: WatchedMovie) {
        viewModelScope.launch {
            try {
                repository.addWatched(movie)
                _isWatched.value = true
            } catch (e: Exception) {
                _errorMessage.value = context.getString(R.string.error_marking_watched) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Elimina una pel·lícula de l'historial de vistes.
     */
    fun removeWatched(context: Context, movieId: Int) {
        viewModelScope.launch {
            try {
                repository.removeWatched(movieId)
                _isWatched.value = false
            } catch (e: Exception) {
                _errorMessage.value = context.getString(R.string.error_removing_watched) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Comprova si una pel·lícula està marcada com a vista.
     */
    fun checkIfWatched(context: Context, movieId: Int) {
        viewModelScope.launch {
            try {
                _isWatched.value = repository.isWatched(movieId)
            } catch (e: Exception) {
                _errorMessage.value = context.getString(R.string.error_checking_watched) + ": ${e.localizedMessage}"
            }
        }
    }

    /**
     * Carrega les pel·lícules vistes.
     */
    fun loadWatched(context: Context) {
        viewModelScope.launch {
            try {
                val watched = repository.getWatched()
                _watchedMovies.value = watched
            } catch (e: Exception) {
                _errorMessage.value = context.getString(R.string.error_loading_history) + ": ${e.localizedMessage}"
            }
        }
    }
}
