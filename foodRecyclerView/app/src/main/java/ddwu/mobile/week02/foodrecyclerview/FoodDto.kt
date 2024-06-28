package ddwu.mobile.week02.foodrecyclerview

data class FoodDto(val photo: Int, val food: String, val count: Int) {
//    override fun toString(): String {
//        return "$food ($count)"
//    }
    override fun toString() = "$food ($count)"
}