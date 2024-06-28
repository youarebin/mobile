package ddwu.com.mobile.foodexam

import java.io.Serializable

data class FoodDto(var food: String, var country: String) : Serializable{
    override fun toString() = "$food ($country)"
}

