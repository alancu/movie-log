package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alejandro.movielog.data.model.ApiMovie
import com.alejandro.movielog.repository.ApiMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que gestiona la lògica per a carregar i buscar pel·lícules des de l'API.
 * Emmagatzema la llista de pel·lícules i l'estat de càrrega.
 * Exposa les dades amb LiveData perquè la UI s'actualitze automàticament.
 */
@HiltViewModel // Permet que Hilt injecte ApiMovieRepository automàticament
class MovieViewModel @Inject constructor(private val repository: ApiMovieRepository)
    : BaseViewModel() {

    // LiveData amb la llista de pel·lícules (es mostra a la UI)
    private val _movies = MutableLiveData<List<ApiMovie>>()
    val movies: LiveData<List<ApiMovie>> = _movies

    /**
     * Carrega pel·lícules populars des de l'API (pantalla principal).
     */
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

    /**
     * Busca pel·lícules segons una consulta de text (pantalla de búsqueda).
     */
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
