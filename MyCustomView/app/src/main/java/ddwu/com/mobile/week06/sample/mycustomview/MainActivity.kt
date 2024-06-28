/*추가점수 구현함 - 라디오버튼*/
package ddwu.com.mobile.week06.sample.mycustomview

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.week06.sample.mycustomview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        registerForContextMenu(mainBinding.myCustomView)/*myCustomView 등록함*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.circle_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {/*클릭에 따라 반지름 바꾸기*/
        when(item.itemId){
            R.id.bigger ->{
                mainBinding.myCustomView.radius += 10f
            }
            R.id.smaller ->{
                mainBinding.myCustomView.radius -= 10f
            }
        }
        mainBinding.myCustomView.invalidate()
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.view_menu,menu)
    }


    /*추가점수: checkBox로 변경 -> all선택 완료*/
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.red ->{
                mainBinding.myCustomView.paintColor = Color.RED
            }
            R.id.green ->{
                mainBinding.myCustomView.paintColor = Color.GREEN
            }
            R.id.blue ->{
                mainBinding.myCustomView.paintColor = Color.BLUE
            }
        }
        mainBinding.myCustomView.invalidate()
        return true
    }


}