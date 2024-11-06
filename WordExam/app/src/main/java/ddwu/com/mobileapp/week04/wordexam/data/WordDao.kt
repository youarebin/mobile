package ddwu.com.mobileapp.week04.wordexam.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Update
    suspend fun updateWord(word: Word)

    @Query("SELECT * FROM word_table")
    // 조건 없이 전체 단어를 검색하여 Word 엔티티 반환
    fun showAllWords() : Flow<List<Word>>

    @Query("SELECT meaning FROM word_table WHERE word = :word")
    // 단어(word)를 입력하여 의미(meaning) 반환
    suspend fun getWordMeaning(word: String) : String
}