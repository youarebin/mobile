package ddwu.com.mobileapp.week04.wordexam.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.week04.wordexam.data.Word
import ddwu.com.mobileapp.week04.wordexam.data.WordRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WordViewModel(var repository: WordRepository) : ViewModel() {
    var allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun addWord(word: Word) = viewModelScope.launch {
        repository.addWord(word)
    }

    fun modifyWord(word: Word) = viewModelScope.launch {
        repository.modifyWord(word)
    }

    fun removeWord(word: Word) = viewModelScope.launch {
        repository.removeWord(word)
    }

    fun getWordMeaning(word: String) : Deferred<String> {
        val deferredWord = viewModelScope.async {
            repository.getWordMeaning(word)
        }
        return deferredWord
    }
}