package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.data.model.Movie
import com.alejandro.movielog.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies()
                _movies.postValue(response.results)
            } catch (e: Exception) {
                _errorMessage.postValue("Error carregant pel·lícules populars: $e")
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(query)
                _movies.postValue(response.results)
            } catch (e: Exception) {
                _errorMessage.postValue("Error buscant pel·lícules: $e")
            }
        }
    }
}
