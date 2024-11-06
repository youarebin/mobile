package ddwu.com.mobileapp.week03.roomexam01

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobileapp.week03.roomexam01.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    // view binding object
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    val foodDabase by lazy { -> FoodApplication에서 정의했기 때문에 불필요
//        FoodDatabase.getDatabase(this)
//    }
//
//    val foodDao by lazy { -> FoodApplication에서 정의했기 때문에 불필요
//        foodDabase.foodDao()
//    }

    val foodRepo by lazy {
        (application as FoodApplication).foodRepo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // init RecyclerView
        val adapter = FoodAdapter(ArrayList<Food>())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.foodRecyclerView.layoutManager = layoutManager
        binding.foodRecyclerView.adapter = adapter


        // get all foods
//        Thread {
//            val foods = foodDao.getAllFoods()
//            for (food in foods) {
//                Log.d(TAG, food.toString())
//            }
//        }.start()
        val foodFlow : Flow<List<Food>> = foodRepo.allFoods
        CoroutineScope(Dispatchers.Main).launch {
            foodFlow.collect{ foods ->
//                for (food in foods) {
//                    Log.d(TAG, food.toString())
//                }
                adapter.foods.clear() //기존 데이터 삭제
                adapter.foods.addAll(foods) // 새로운 foods데이터 추가
                adapter.notifyDataSetChanged() // adapter 변경했으므로 갱신
            }
        }


        // food by country
        binding.btnShow.setOnClickListener {
            val countryName = binding.etCountry.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                val foods = foodRepo.showFoodByCountry(countryName)
                for (food in foods){
                    Log.d(TAG, food.toString())
                }
            }

//            adapter.foods.clear()
//            adapter.foods.addAll(foods)
//            adapter.notifyDataSetChanged()

        }


        // insert new food
        binding.btnInsert.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val food = Food(0, foodName, countryName)   // new food

            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.addFood(food)
            }
//            Thread {
//                foodDao.insertFood(food)
//            }.start()
        }

        // update food id 2
        binding.btnUpdate.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val targetFood = Food(2, foodName, countryName)

            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.modifyFood(targetFood)
            }
        }

        // update food id 3
        binding.btnDelete.setOnClickListener {
            val targetFood = Food(3, "", "")    // delete food _id 3

            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.removeFood(targetFood)
            }
        }


    }
}