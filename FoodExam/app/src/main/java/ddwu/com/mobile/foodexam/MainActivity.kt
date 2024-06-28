package ddwu.com.mobile.foodexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.foodexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val ADDFOOD_ACTIVITY_CODE = 100
    private val foods = FoodDao().foods
    private val adapter = FoodAdapter(foods)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //val foods = FoodDao().foods//val

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        //val adapter = FoodAdapter(foods)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        // btnAdd를 클릭하면 AddFoodActivity 실행
        binding.btnAdd.setOnClickListener{
            val intent = Intent(this, AddFoodActivity::class.java)
            startActivityForResult(intent, ADDFOOD_ACTIVITY_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADDFOOD_ACTIVITY_CODE){
           when(resultCode){
                RESULT_OK ->{
                    val result = data?.getSerializableExtra("newFood") as FoodDto//dto객체 받아오기
                    foods.add(result)//dto객체 추가
                    adapter.notifyItemInserted(foods.size - 1)//배열 마지막에 추가
                }
//                RESULT_CANCELED->{+-
//
//                }
           }
        }
    }

}