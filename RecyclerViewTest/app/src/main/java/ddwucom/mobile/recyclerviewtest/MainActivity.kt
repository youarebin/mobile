package ddwucom.mobile.recyclerviewtest

import android.net.IpSecManager.SpiUnavailableException
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ddwucom.mobile.recyclerviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = SubjectDao()
        val dataList = dao.dataList

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(this, R.layout.list_item, dataList)
        binding.recyclerView.adapter = adapter

        //실습2
//        binding.button.setOnClickListener{
//            dataList.add(binding.etSubject.text.toString())
//            adapter.notifyDataSetChanged()
//        }
    }
}