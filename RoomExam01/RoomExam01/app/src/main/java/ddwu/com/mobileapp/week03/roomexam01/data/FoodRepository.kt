package ddwu.com.mobileapp.week03.roomexam01.data

import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDao
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {
    val allFoods : Flow<List<Food>> = foodDao.getAllFoods()

    suspend fun addFood(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun showFoodByCountry(country: String):List<Food> {
        return foodDao.getFoodsByCountry(country)
    }

    suspend fun modifyFood(food:Food) {
        foodDao.updateFood(food)
    }

    suspend fun removeFood(food: Food) {
        foodDao.deleteFood(food)
    }

}