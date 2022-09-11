package com.blank.protobufsample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PrefViewModel(
    private val preferencesRepository: PrefRepository) : ViewModel()  {

    init {
        getDataPref()
    }

    private val _namePref = MutableLiveData<String>()
    val namePref :LiveData<String> get() = _namePref

     fun saveName(name:String){
        viewModelScope.launch {
            preferencesRepository.updatePref(name)
        }
     }

    fun getDataPref() = viewModelScope.launch {
        preferencesRepository.preferencesFlow.collect {
            _namePref.value = it.name
        }
    }
}

class PrefViewModelFactory(
    private val  preferencesRepository: PrefRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrefViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrefViewModel(preferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}