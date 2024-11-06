// Repository 작성
// VeiwModel 작성
package ddwu.com.mobileapp.week04.wordexam

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import ddwu.com.mobileapp.week04.wordexam.databinding.ActivityMainBinding
import ddwu.com.mobileapp.week04.wordexam.data.Word
import ddwu.com.mobileapp.week04.wordexam.data.WordDao
import ddwu.com.mobileapp.week04.wordexam.data.WordDatabase
import ddwu.com.mobileapp.week04.wordexam.ui.WordViewModel
import ddwu.com.mobileapp.week04.wordexam.ui.WordViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

     val wordViewModal : WordViewModel by viewModels {
         WordViewModelFactory( (application as WordApplication).wordRepo )
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // 1. wordDao 객체 생성
        // application에 이미 만들어 놓음 db 가져올 필요 없음
        val wordDao = (application as WordApplication).wordDao


        val adapter = WordAdapter(ArrayList<Word>())
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter.setOnWordClickListener(object: WordAdapter.OnWordClickListener {
            override fun onWordClick(view: View, pos: Int) {
                // 5. rvWords 에서 클릭한 단어로 wordDao를 사용하여  DB에서 의미 검색 후 의미 칸에 표시
                val clickedWord = adapter.words[pos].toString()

                //1) repository, veiwModel 없이
//                CoroutineScope(Dispatchers.Main).launch {
////                    val meaning = wordDao.getWordMeaning(clickedWord)
////                    binding.etMeaning.setText(meaning)
//                }

                // 2) repository, viewModel 사용
                CoroutineScope(Dispatchers.Main).launch {
                    val meaning = wordViewModal.getWordMeaning(clickedWord).await()
                    withContext(Dispatchers.Main) {
                        binding.etWord.setText(clickedWord)
                        binding.etMeaning.setText(meaning)
                    }
                }

            }

        })

        binding.rvWords.layoutManager = layoutManager
        binding.rvWords.adapter = adapter


        // 2. wordDao 객체에서 전체 word 를 가져와 rvWords(RecyclerView) 에 지정
        // Flow<List<Word>> 를 사용하여 갱신 정보를 자동 반영하도록 구성
//        val allwords : Flow<List<Word>> = wordDao.showAllWords()
//        CoroutineScope(Dispatchers.Main).launch {
//            allwords.distinctUntilChanged().collect{ words ->
//                adapter.words = words
//                adapter.notifyDataSetChanged()
//            }
//        }
        wordViewModal.allWords.observe(this, {words ->
            adapter.words = words
            adapter.notifyDataSetChanged()
        })


        // 3. 화면에 입력한 단어와 의미를 읽어와 Word 로 만든 후 wordDao 를 사용하여 DB 저장
        binding.btnSave.setOnClickListener {
            val word = binding.etWord.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val wordEntity = Word (word, meaning)

//            CoroutineScope(Dispatchers.IO).launch {
//                wordDao.insertWord(wordEntity)
//                // 한 번만 사용해서 변수 없이 한줄로 써도 됨
//                // wordDao.insertWord(Word(binding.etWord.text.toString(), binding.etMeaning.text.toString())
//            }
            wordViewModal.addWord(wordEntity)
        }

        // 4. 화면에 입력한 단어로 Word 로 만둔 후(의미는 빈문자열) wordDao 를 사용하여 DB 삭제
        binding.btnDelete.setOnClickListener {
            val word = binding.etWord.text.toString()
            val wordEntity = Word (word, "")

//            CoroutineScope(Dispatchers.IO).launch {
//                wordDao.deleteWord(wordEntity)
//            }
           wordViewModal.removeWord(wordEntity)

        }

        binding.btnUpdate.setOnClickListener {
            val word = binding.etWord.text.toString()
            val newMeaning = binding.etMeaning.text.toString()
            val wordEntity = Word (word, newMeaning)

            wordViewModal.modifyWord(wordEntity)
        }


    }

}