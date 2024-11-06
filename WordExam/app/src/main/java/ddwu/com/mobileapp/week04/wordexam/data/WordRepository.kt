package ddwu.com.mobileapp.week04.wordexam.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords : Flow<List<Word>> = wordDao.showAllWords()

    suspend fun getWordMeaning(word: String) : String {
        val wordMeaning = wordDao.getWordMeaning(word)
        return wordMeaning
    }

    suspend fun addWord(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun modifyWord(word: Word) {
        wordDao.updateWord(word)
    }

    suspend fun removeWord(word: Word) {
        wordDao.deleteWord(word)
    }
}