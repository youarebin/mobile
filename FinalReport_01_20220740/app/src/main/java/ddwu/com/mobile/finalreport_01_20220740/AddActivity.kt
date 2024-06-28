package ddwu.com.mobile.finalreport_01_20220740

import android.R
import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDBHelper
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDto
import ddwu.com.mobile.finalreport_01_20220740.databinding.ActivityAddBinding
import java.util.Calendar

class AddActivity : AppCompatActivity() {
    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(addBinding.root)

        //date선택 R.style.Theme_Holo_Light_Dialog_MinWidth
        addBinding.btnDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                addBinding.etAddDate.setText("$year/${month + 1}/$dayOfMonth")
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

        /*EditText에서 값을 읽어와 DB에 저장*/
        addBinding.btnAddMovie.setOnClickListener{
            val movie = addBinding.etAddMovie.text.toString()
            val director = addBinding.etAddDirector.text.toString()
            val date = addBinding.etAddDate.text.toString()
            val rateStr = addBinding.etAddRate.text.toString()
            val summary = addBinding.etAddSummary.text.toString()

            if (movie.isEmpty() || director.isEmpty() || date.isEmpty() || rateStr.isEmpty() || summary.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rate = try {
                Integer.parseInt(rateStr)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "평점을 올바르게 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (addMovie ( MovieDto( 0, movie, director, date, rate, summary  )) > 0) {
                setResult(RESULT_OK)
            }

            finish()
        }

        addBinding.btnAddCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

    }

    fun addMovie(newDto : MovieDto) : Long {
        val helper = MovieDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues()
        newValue.put(MovieDBHelper.COL_MOVIE, newDto.movie)
        newValue.put(MovieDBHelper.COL_DIRECTOR, newDto.director)
        newValue.put(MovieDBHelper.COL_DATE, newDto.date)
        newValue.put(MovieDBHelper.COL_RATE, newDto.rate)
        newValue.put(MovieDBHelper.COL_SUMMARY, newDto.summary)

        val newCount = db.insert(MovieDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }
}