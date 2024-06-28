package ddwu.com.mobile.fooddbexam

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.fooddbexam.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }

    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)

        helper = FoodDBHelper(this)

        addBinding.btnAddFood.setOnClickListener{
            addFood(addBinding.etAddFood.text.toString(),
                addBinding.etAddCountry.text.toString())
        }


    }

    fun addFood(food: String, country: String) {
        val db = helper.writableDatabase
        val newRow = ContentValues()
        newRow.put(FoodDBHelper.COL_FOOD, food);
        newRow.put(FoodDBHelper.COL_COUNTRY, country)
        db.insert(FoodDBHelper.TABLE_NAME, null, newRow)
        helper.close()
    }


}