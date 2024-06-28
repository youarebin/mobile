package ddwucom.mobile.week4_1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun HelloOnClick(view : View){
        val etName : EditText = findViewById(R.id.etName)
        val etPhone : EditText = findViewById(R.id.etPhone)
        val text : String = "안녕하세요, 저는 ${etName.text.toString()} 입니다. " +
                "전화번호는 ${etPhone.text.toString()} 입니다."

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
    fun exitOnClick(view: View){
        finish()
    }
}