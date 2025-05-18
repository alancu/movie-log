package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona la lògica de les pel·lícules i emet dades a la UI.
 */
@HiltViewModel
class MovieViewModel  @Inject constructor(private val repository: MovieRepository)
    : BaseViewModel() {

    private val _movies = MutableLiveData<List<ApiMovie>>()
    val movies: LiveData<List<ApiMovie>> = _movies

    fun loadPopularMovies() {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val response = repository.getPopularMovies()
                _movies.postValue(response.results)
            } catch (e: Exception) {
                handleError(e, "Error carregant pel·lícules populars")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val response = repository.searchMovies(query)
                _movies.postValue(response.results)
            } catch (e: Exception) {
                handleError(e, "Error buscant pel·lícules")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
