package ddwu.com.mobile.finalreport_01_20220740.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.BaseColumns

class MovieDao(val context: Context) {

    fun deleteMovie(dto: MovieDto) : Int {
        val helper = MovieDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        return db.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs)
    }


    @SuppressLint("Range")
    fun getAllMovies() : ArrayList<MovieDto> {
        val helper = MovieDBHelper(context)
        val db = helper.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)
        val cursor = db.query(MovieDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val movies = arrayListOf<MovieDto>()
        with (cursor) {
            while (moveToNext()) {
                val id = getInt( getColumnIndex(BaseColumns._ID) )
                val movie = getString ( getColumnIndex(MovieDBHelper.COL_MOVIE) )
                val director = getString ( getColumnIndex(MovieDBHelper.COL_DIRECTOR) )
                val date = getString ( getColumnIndex(MovieDBHelper.COL_DATE))
                val rate = getInt ( getColumnIndex(MovieDBHelper.COL_RATE))
                val summary = getString ( getColumnIndex(MovieDBHelper.COL_SUMMARY))
                val dto = MovieDto(id, movie, director, date, rate, summary)
                movies.add(dto)
            }
        }
        return movies
    }
}