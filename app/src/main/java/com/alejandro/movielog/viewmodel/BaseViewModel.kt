package com.alejandro.movielog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Suppress("PropertyName")
open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    @Suppress("unused")
    val loading: LiveData<Boolean> = _loading

    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    protected fun handleError(e: Exception, defaultMessage: String) {
        _errorMessage.postValue("$defaultMessage: ${e.localizedMessage}")
    }
}
