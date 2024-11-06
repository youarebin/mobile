package ddwu.com.mobileapp.week06.naverparsing.data.network.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import kotlin.jvm.Throws

class StreamUtil {

    companion object {
        fun readStreamToString(inputStream: InputStream?): String? {
            val resultBuilder = StringBuilder()

            return inputStream.use { iStream ->
                InputStreamReader(iStream).use { isReader ->
                    BufferedReader(isReader).use { bReader ->
                        var readLine: String? = bReader.readLine()
                        while (readLine != null) {
                            resultBuilder.append(readLine + System.lineSeparator())
                            readLine = bReader.readLine()
                        }
                        resultBuilder.toString()
                    }
                }
            }
        }

        fun readStreamToImage(iStream: InputStream?): Bitmap? {
            return BitmapFactory.decodeStream(iStream)
        }
    }

}