package ddwu.com.mobile.finalreport_01_20220740.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "MovieDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_MOVIE = "movie"
        const val COL_DIRECTOR = "director"
        const val COL_DATE = "date"
        const val COL_RATE = "rate"
        const val COL_SUMMARY = "summary"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_MOVIE TEXT, $COL_DIRECTOR TEXT, $COL_DATE TEXT, $COL_RATE TEXT, $COL_SUMMARY TEXT )"
        Log.d(TAG, CREATE_TABLE)    // SQL 문장이 이상 없는지 Log에서 확인 필요
        db?.execSQL(CREATE_TABLE)

        /*샘플 데이터 - 필요할 경우 실행*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '파묘', '장재현', '2024.02.22', '8.19', '...')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '인사이드아웃2', '켈시 만', '2024', '5', '....')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '범죄도시4', '허명행', '2024.04.24','7.55', '...')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '토토로', '미야자키 하야오', '1988.4.16', '9.53', '...')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '겨울왕국2', '크리스틴 벅', '2019.11.21', '8.94', '...')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}