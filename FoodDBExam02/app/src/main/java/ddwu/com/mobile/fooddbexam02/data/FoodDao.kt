package ddwu.com.mobile.fooddbexam02.data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.BaseColumns

class FoodDao (val context: Context){

    fun deleteFood(dto: FoodDto) : Int {
        val helper = FoodDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        return db.delete(FoodDBHelper.TABLE_NAME, whereClause, whereArgs)
    }


    @SuppressLint("Range")
    fun getAllFoods() : ArrayList<FoodDto> {
        val helper = FoodDBHelper(context)
        val db = helper.readableDatabase
//        val cursor = db.rawQuery("SELECT * FROM ${FoodDBHelper.TABLE_NAME}", null)
        val cursor = db.query(FoodDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val foods = arrayListOf<FoodDto>()
        with (cursor) {
            while (moveToNext()) {
                val id = getInt( getColumnIndex(BaseColumns._ID) )
                val food = getString ( getColumnIndex(FoodDBHelper.COL_FOOD) )
                val country = getString ( getColumnIndex(FoodDBHelper.COL_COUNTRY) )
                val dto = FoodDto(id, food, country)
                foods.add(dto)
            }
        }
        return foods
    }

}