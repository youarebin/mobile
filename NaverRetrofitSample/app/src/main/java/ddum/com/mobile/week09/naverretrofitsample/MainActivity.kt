package ddum.com.mobile.week09.naverretrofitsample

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import ddum.com.mobile.week09.naverretrofitsample.data.util.FileManager
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityDetailBinding
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityMainBinding
import ddum.com.mobile.week09.naverretrofitsample.ui.BookAdapter
import ddum.com.mobile.week09.naverretrofitsample.ui.NVViewModel
import ddum.com.mobile.week09.naverretrofitsample.ui.NVViewModelFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    val TAG = "MAIN_ACTIVITY_TAG"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = BookAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvBooks.layoutManager = layoutManager
        binding.rvBooks.adapter = adapter

        val nvViewModel : NVViewModel by viewModels {
            NVViewModelFactory( (application as NVApplication).nvRepository )
        }

        nvViewModel.books.observe(this) { books ->
            adapter.books = books
            adapter.notifyDataSetChanged()
        }

        nvViewModel.drawable.observe(this) { drawable ->
            binding.imageView.setImageBitmap(drawable)
        }


        // 필요할 경우 파일 디렉토리 생성
        // 내부저장소 전용위치에 images 하위 디렉토리 생성
        Log.d(TAG, "${filesDir}")
        Log.d(TAG, "${cacheDir}")
//
//        //파일 읽기
//        val writeData = "Mobile Application"
//
//        val writeFile = File(filesDir, "test.txt")
//        val outputStream  = FileOutputStream(writeFile)
//        outputStream.write(writeData.toByteArray())
//        outputStream.close()
//
//        val newFile = File(filesDir, "test.txt")
//        val result = StringBuffer()
//        val fileReader = FileReader(newFile)
//        BufferedReader(fileReader).useLines{lines->
//            for(line in lines) { result.append(line+"\n") }
//            Log.d(TAG, "read: ${result}")
//        }
//
//        //이미지 읽기
//        val imageFile = File(filesDir, "image.jpg")
//        val bitmap = BitmapFactory.decodeFile(imageFile.path)
//        binding.imageView.setImageBitmap(bitmap)
        FileManager.createSubDirectory(filesDir, "images")

        Glide.with(this)
            .load("${filesDir}/images/image.jpg")
            .into(binding.imageView)

        adapter.setOnItemClickListener(object: BookAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val url = adapter.books?.get(position)?.image

                // 실습1. url 에 해당하는 이미지 바로 표시
                Glide.with(this@MainActivity)
                    .load(url)
                    .into(binding.imageView)

                // 실습2. ViewModel 을 통해 Bitmap 을 가져와 표시
                nvViewModel.setImage(url)

                // 실습3. 클릭할 경우 Image 의 url 을 Intent 에 저장(key: url) 후 DetailActivity 호출
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)

            }
        })

        binding.btnSearch.setOnClickListener{
            val query = binding.etKeyword.text.toString()
            nvViewModel.getBooks(query)
        }


    }


}