package ddwu.mobile.week02.foodrecyclerview

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.mobile.week02.foodrecyclerview.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val foods = FoodDao().foods
        val adapter = FoodAdapter(foods)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.recycleView.layoutManager = layoutManager
        binding.recycleView.adapter = adapter

        val longClickListener = object : FoodAdapter.OnItemLongClickListener{
            override fun onItemLongClick(view: View, position: Int): Boolean {
                AlertDialog.Builder(this@MainActivity).apply {
                    title="삭제 확인"
                    setMessage("정말 삭제하시겠습니까?")
                    setPositiveButton("확인"){dialogInterface: DialogInterface, _: Int ->
                        foods.removeAt(position)
                        adapter.notifyDataSetChanged()
                    }
                    setNegativeButton("취소", null)
                    show()
                }
                return true
            }
        }
        adapter.setOnItemLongClickListener( longClickListener )

        //추가 미션
        binding.button.setOnClickListener{
            val text = (binding.editTextText).text.toString()

            val random = Random()
            val num = random.nextInt(9)+1
            foods.add(FoodDto( R.drawable.ic_launcher_foreground,text,num))
        }

    }
}