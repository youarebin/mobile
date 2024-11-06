package ddwu.com.mobileapp.week05.archictectureref.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.week05.archictectureref.data.RefRepository
import ddwu.com.mobileapp.week05.archictectureref.data.database.RefEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RefViewModel (val refRepository: RefRepository) : ViewModel() {
    // Flow 를 사용하여 지속 관찰
    val allRefs : LiveData<List<RefEntity>> = refRepository.allRefs.asLiveData()

    // one-shot 결과를 확인하고자 할 때 사용
    private var _name = MutableLiveData<String>() // *2* 바꿀수 있게 MutableLiveData를 반듬
    val nameData : LiveData<String> = _name // *1* LiveData는 한 번 생성되면 고정됨 (nameData는 대입이 아니라 객체를 _name이 가리키는것 가리킴)

    // viewModelScope 는 Dispatcher.Main 이므로 긴시간이 걸리는 IO 작업은 Dispatchers.IO 에서 작업
    fun findName(id: Int) = viewModelScope.launch {  // CoroutineScope(Dispatchers.Main).launch {  } 과 비슷함
        var result : String
        withContext(Dispatchers.IO) { // ** CoroutineScope의 Main(화면 쓰레드)에서 실행 중 -> IO 쓰레드로 옮겨서 실행
            result = refRepository.getNameById(id) // ** 얘는 Main이서 실행되면 안됨 (IO 없으면 db작업하는 앤데 Main에서 실행시켜서 멈춤)
        }
        _name.value = result // *3* _name 값이 바뀜 -> nameData (간접적으로)바뀜
    }

    fun addRef(ref: RefEntity) = viewModelScope.launch { // db작업인데 Main이어서 오류 발생됨
        withContext(Dispatchers.IO){ // IO로 잠시 옮겨 줘야함!
            refRepository.insertRef(ref)
        }
    }


}