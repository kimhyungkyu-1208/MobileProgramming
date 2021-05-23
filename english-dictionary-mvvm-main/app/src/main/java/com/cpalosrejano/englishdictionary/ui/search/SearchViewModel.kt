package com.cpalosrejano.englishdictionary.ui.search

import androidx.lifecycle.*
import com.cpalosrejano.englishdictionary.model.Word
import com.cpalosrejano.englishdictionary.repository.word.WordRepository
import kotlinx.coroutines.launch

class SearchViewModel(
        private val repository: WordRepository
) : ViewModel() {

    val definitions = MutableLiveData<Word>()

    fun fetchDefinitionsFor(word: String) {
        viewModelScope.launch {
            val result = repository.getWord(word)
            result?.let {
                definitions.postValue(it)
            }
        }
    }

    // FactoryClass to create new instance of this ViewModel
    class Factory(private val repository: WordRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(repository) as T
        }
    }

}