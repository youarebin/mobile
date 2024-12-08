package ddum.com.mobile.week09.naverretrofitsample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import ddum.com.mobile.week09.naverretrofitsample.data.util.FileManager
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityDetailBinding
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {

    val TAG = "DETAIL_ACTIVITY_TAG"

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        // MainActivity 로부터 전달받은 이미지의 URL
        imageUrl = intent.getStringExtra("url")
        val imageFileName = "${FileManager.getCurrentTime()}.jpg"

        Glide.with(this@DetailActivity)
            .load(imageUrl)
            .into(detailBinding.ivBookCover)

        detailBinding.btnSave.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into( object: CustomTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val imageFile = File("${filesDir}/images", imageFileName)
                        val fos = FileOutputStream(imageFile)
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.close()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        Log.d(TAG, "Image load cleared")
                    }

                })
        }

        detailBinding.btnRead.setOnClickListener {
            val imageFile = File("${filesDir}/images", imageFileName)
            val bitmap = BitmapFactory.decodeFile(imageFile.path)
            detailBinding.ivBookCover.setImageBitmap(bitmap)
        }

        detailBinding.btnInit.setOnClickListener {
            val imageFile = File(filesDir, "/images/image.jpg")
            val bitmap = BitmapFactory.decodeFile(imageFile.path)
            detailBinding.ivBookCover.setImageBitmap(bitmap)
        }

        detailBinding.btnRemove.setOnClickListener {
            val deleteFile = File("${filesDir}/images", imageFileName)
            deleteFile.delete()
        }
    }
}