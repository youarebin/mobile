package ddwucom.mobile.week5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddwucom.mobile.week5.databinding.ActivityMainBinding
import ddwucom.mobile.week5.databinding.LinearLayoutBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val binding: LinearLayoutBinding =
//            LinearLayoutBinding.inflate(layoutInflater)

//        binding.button1.setOnClickListener{
//            val layout:LinearLayout = findViewById(R.id.linear)
//            layout.orientation = LinearLayout.HORIZONTAL
//        }

        val myView = MyOuterView(this)//별도의 클래스 만들어줌
        setContentView(R.layout.activity_main)

    }

//    class MyView constructor(context: Context?) : View(context){//내가 만든 뷰
//        override fun onDraw(canvas: Canvas){
//            super.onDraw(canvas)
//            canvas?.drawColor(Color.LTGRAY)
//            val paint = Paint()
//            paint.setColor(Color.BLUE)
//            canvas?.drawCircle(200.toFloat(),200.toFloat(),100.toFloat(),paint)
//        }
//    }

}