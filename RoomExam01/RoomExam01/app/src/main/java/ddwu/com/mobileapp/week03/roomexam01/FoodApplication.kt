package ddwu.com.mobileapp.week03.roomexam01

import android.app.Application
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobileapp.week03.roomexam01.data.FoodRepository

class FoodApplication: Application() {
    val foodRepo by lazy {
        val database = FoodDatabase.getDatabase(this)
        FoodRepository(database.foodDao())
    }
}