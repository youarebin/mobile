package ddwu.com.mobile.finalreport_01_20220740

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalreport_01_20220740.databinding.ActivityAddBinding
import ddwu.com.mobile.finalreport_01_20220740.databinding.ActivityIntroduceBinding

class IntroduceActivity: AppCompatActivity() {

    val introduceBinding by lazy {
        ActivityIntroduceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(introduceBinding.root)

        introduceBinding.btnIntroduce.setOnClickListener{
            finish()
        }

    }
}