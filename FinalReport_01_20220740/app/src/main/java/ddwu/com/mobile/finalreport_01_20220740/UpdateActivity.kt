package ddwu.com.mobile.finalreport_01_20220740

import android.R
import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDBHelper
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDto
import ddwu.com.mobile.finalreport_01_20220740.databinding.ActivityUpdateBinding
import java.util.Calendar

class UpdateActivity : AppCompatActivity() {

    val updateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(updateBinding.root)
        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as MovieDto

        /*기존 정보 표시*/
        updateBinding.etUpdateMovie.setText(dto.movie)
        updateBinding.etUpdateDirector.setText(dto.director)
        updateBinding.etUpdateDate.setText(dto.date)
        updateBinding.etUpdateRate.setText(dto.rate.toString())
        updateBinding.etUpdateSummary.setText(dto.summary)


        //date선택 R.style.Theme_Holo_Light_Dialog_MinWidth
        updateBinding.btnUpdateDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                updateBinding.etUpdateDate.setText("$year/${month + 1}/$dayOfMonth")
                dto.date = updateBinding.etUpdateDate.text.toString()
            }
            val datePickerDialog = DatePickerDialog(
                this,
                R.style.Theme_Holo_Light_Dialog, // 스피너 모드 테마 설정
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.setCalendarViewShown(false);//calendar뷰 안보이게
            datePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명하게 설정
            datePickerDialog.show()
        }

        updateBinding.btnUpdateMovie.setOnClickListener{
            dto.movie = updateBinding.etUpdateMovie.text.toString()
            dto.director = updateBinding.etUpdateDirector.text.toString()
            //dto.date = updateBinding.etUpdateDate.text.toString()

            try {
                dto.rate = Integer.parseInt(updateBinding.etUpdateRate.text.toString())
            } catch (e: NumberFormatException) {//문자열 입력된 경우
                Toast.makeText(this@UpdateActivity, "유효하지 않은 평점 입력입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 클릭 리스너에서 빠져나감
            }

            dto.summary = updateBinding.etUpdateSummary.text.toString()

            if (dto.movie.isEmpty() || dto.director.isEmpty() || dto.date.isEmpty() || dto.rate.toString().isEmpty() || dto.summary.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
    fun updateFood(dto: MovieDto): Int {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val updateValue = ContentValues()
        updateValue.put(MovieDBHelper.COL_MOVIE, dto.movie)
        updateValue.put(MovieDBHelper.COL_DIRECTOR, dto.director)
        updateValue.put(MovieDBHelper.COL_DATE, dto.date)
        updateValue.put(MovieDBHelper.COL_RATE, dto.rate)
        updateValue.put(MovieDBHelper.COL_SUMMARY, dto.summary)
        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount =  db.update(MovieDBHelper.TABLE_NAME,
            updateValue, whereClause, whereArgs)

        helper.close()
        return resultCount
    }
}