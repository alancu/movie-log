package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Un ViewModel és una classe que guarda dades i lògica de la pantalla
// separant la lògica de la UI
/**
 * ViewModel base que proporciona propietats comunes per a la resta de ViewModels.
 * La resta de ViewModels poden heretar d'aquest per reutilitzar aquests comportaments.
 */
@Suppress("PropertyName")
open class BaseViewModel : ViewModel() {
    // les variables que comencen per "_" en Android són la versió privada i modificable
    // sense "_" és la versió pública que la UI pot observar, però no pot modificar
    protected val _loading = MutableLiveData<Boolean>()
    @Suppress("unused")
    // LiveData és una classe d’Android que serveix per guardar i observar dades que poden canviar
    // Quan el valor de LiveData canvia, la UI que l’està “observant” s’actualitza automàticament
    val loading: LiveData<Boolean> = _loading

    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Funció per gestionar errors i publicar el missatge d'error a errorMessage.
     */
    protected fun handleError(e: Exception, defaultMessage: String) {
        _errorMessage.postValue("$defaultMessage: ${e.localizedMessage}")
    }
}
