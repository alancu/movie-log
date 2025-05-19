package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.data.model.WatchedMovie
import com.alejandro.movielog.repository.UserMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona les pel·lícules marcades com a vistes per l'usuari.
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

    fun addWatched(movie: WatchedMovie) {
        viewModelScope.launch {
            try {
                repository.addWatched(movie)
                _isWatched.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Error marcant com a vista: ${e.localizedMessage}"
            }
        }
    }

    fun removeWatched(movieId: Int) {
        viewModelScope.launch {
            try {
                repository.removeWatched(movieId)
                _isWatched.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Error eliminant de vistes: ${e.localizedMessage}"
            }
        }
    }

    fun checkIfWatched(movieId: Int) {
        viewModelScope.launch {
            try {
                _isWatched.value = repository.isWatched(movieId)
            } catch (e: Exception) {
                _errorMessage.value = "Error comprovant si és vista: ${e.localizedMessage}"
            }
        }
    }

    fun loadWatched() {
        viewModelScope.launch {
            try {
                val watched = repository.getWatched()
                // Ordenem per data, la més recent primer
                _watchedMovies.value = watched.sortedByDescending { it.watchedAt }
            } catch (e: Exception) {
                _errorMessage.value = "Error carregant historial: ${e.localizedMessage}"
            }
        }
    }
}
