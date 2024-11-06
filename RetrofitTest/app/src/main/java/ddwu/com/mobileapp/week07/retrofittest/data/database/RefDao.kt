package ddwu.com.mobileapp.week07.retrofittest.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow     // Flow 사용 시 추가할 것

@Dao
interface RefDao {
    @Query ("SELECT * FROM ref_table")
    fun getAllRefs() : Flow<List<RefEntity>>

    @Query ("SELECT title FROM ref_table WHERE _id = :id")
    fun getNameById(id: Int) : String
}