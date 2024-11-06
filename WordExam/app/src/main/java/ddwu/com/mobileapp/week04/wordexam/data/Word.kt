package ddwu.com.mobileapp.week04.wordexam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey val word: String,
    var meaning: String
) {
    override fun toString(): String {
        return word
    }
}
