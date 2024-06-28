package ddwu.com.mobile.fooddbexam02

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import ddwu.com.mobile.fooddbexam02.data.FoodDBHelper
import ddwu.com.mobile.fooddbexam02.data.FoodDto
import ddwu.com.mobile.fooddbexam02.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    val updateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(updateBinding.root)

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as FoodDto

        /*기존 정보 표시*/
        updateBinding.etUpdateId.setText(dto.id.toString())     // XML 속성에서 편집금지로 지정하였음
        updateBinding.etUpdateFood.setText(dto.food)
        updateBinding.etUpdateCountry.setText(dto.country)

        updateBinding.btnUpdateFood.setOnClickListener{
            dto.food = updateBinding.etUpdateFood.text.toString()
            dto.country = updateBinding.etUpdateCountry.text.toString()
//
            if (updateFood(dto) > 0) {
                setResult(RESULT_OK)
            } else {
                setResult(RESULT_CANCELED)
            }

            finish()
        }

        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    /*ID 에 해당하는 레코드를 찾아 dto 의 내용으로 수정*/
    fun updateFood(dto: FoodDto): Int {
        val helper = FoodDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues()
        updateValue.put(FoodDBHelper.COL_FOOD, dto.food)
        updateValue.put(FoodDBHelper.COL_COUNTRY, dto.country)
        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount =  db.update(FoodDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }

}