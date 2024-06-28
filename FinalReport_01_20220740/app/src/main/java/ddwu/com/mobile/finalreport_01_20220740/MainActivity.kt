// 과제명: 영화 정보 관리 앱
// 분반: 01 분반
// 학번: 20220740 성명: 가유빈
// 제출일: 2024년 6월 22일
package ddwu.com.mobile.finalreport_01_20220740

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDao
import ddwu.com.mobile.finalreport_01_20220740.data.MovieDto
import ddwu.com.mobile.finalreport_01_20220740.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var movies : ArrayList<MovieDto>

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        binding.rvMovies.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        movies = movieDao.getAllMovies()          // DB 에서 모든 movie를 가져옴
        val adapter = MovieAdapter(movies)        // adapter 에 데이터 설정
        binding.rvMovies.adapter = adapter   // RecylcerView 에 adapter 설정

        adapter.setOnItemLongClickListener( object: MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                // 현재 항목 DB 삭제 ID 기준
                AlertDialog.Builder(this@MainActivity).apply {
                    title="영화 삭제"
                    setMessage(movies[position].movie +"를 삭제하시겠습니까?")
                    setPositiveButton("삭제"){ dialog, which ->
                        if ( movieDao.deleteMovie( movies[position] ) > 0 ) {
                            // 화면 갱신
                            movies.clear()                       // 기존 항목 제거
                            movies.addAll(movieDao.getAllMovies())         // 항목 추가
                            binding.rvMovies.adapter?.notifyDataSetChanged()      // RecyclerView 갱신
                        }
                    }
                    setNegativeButton("취소"){ dialog, which ->
                        dialog.dismiss()//대화상자 삭제
                    }
                    show()
                }
                return true
            }
        })

        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", movies[position])   // 클릭한 RecyclerView 항목 위치의 dto 전달
                Log.d(TAG, "onItemClick: Movie selected: ${movies[position]}")
                startActivityForResult(intent, REQ_UPDATE)      // 수정결과를 받아오도록 Activity 호출
            }
        })

        //검색 기능
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //검색 버튼 눌렀을때 실행됨
                adapter.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //검색창에 값 입력될때 마다 실행됨
                adapter.filter?.filter(newText)
                return false
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                if (resultCode == RESULT_OK) {
                    movies.clear()                       // 기존 항목 제거
                    movies.addAll(movieDao.getAllMovies())         // 항목 추가
                    binding.rvMovies.adapter?.notifyDataSetChanged()      // RecyclerView 갱신
                } else {
                    Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
                }
            }
            REQ_ADD -> {
                if (resultCode == RESULT_OK) {
                    movies.clear()                       // 기존 항목 제거
                    movies.addAll(movieDao.getAllMovies())         // 항목 추가
                    binding.rvMovies.adapter?.notifyDataSetChanged()      // RecyclerView 갱신
                } else {
                    Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //옵션 메뉴 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMovie ->{
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            R.id.introduce ->{
                val intent = Intent(this@MainActivity, IntroduceActivity::class.java)
                startActivity(intent)
            }
            R.id.end ->{
                AlertDialog.Builder(this@MainActivity).apply {
                    title="앱 종료"
                    setMessage("앱을 종료하시겠습니까?")
                    setPositiveButton("종료"){ dialog, which ->
                        finishAffinity()//모든 액티비티 종료
                    }
                    setNegativeButton("취소"){ dialog, which ->
                        dialog.dismiss()//대화상자 삭제
                    }
                    show()
                }
            }
        }

        return true
    }


}