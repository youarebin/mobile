package ddwu.com.mobile.foodexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.foodexam.databinding.ActivityAddFoodBinding

class AddFoodActivity : AppCompatActivity() {
    val TAG = "AddFoodActivity"
    val binding by lazy {
        ActivityAddFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener{//저장 -> dto객체 넘겨주기
            val resultIntent = Intent()
            resultIntent.putExtra("newFood", FoodDto(binding.etNewFood.text.toString(), binding.etCountry.text.toString()))
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.btnCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }


    }

}