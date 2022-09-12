package com.blank.protobufsample

import androidx.lifecycle.*
import com.blank.protobufsample.grpc.Book
import kotlinx.coroutines.launch

class PrefViewModel(
    private val preferencesRepository: PrefRepository) : ViewModel()  {

    init {
        getDataPref()
    }

    private val _namePref = MutableLiveData<String>()
    val namePref :LiveData<String> get() = _namePref

    private val _listBook = MutableLiveData<List<Book>>()
    val listBook :LiveData<List<Book>> get() = _listBook

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
    fun getBook() = viewModelScope.launch {
        preferencesRepository.getBook().collect{
         _listBook.value = it.booksList
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