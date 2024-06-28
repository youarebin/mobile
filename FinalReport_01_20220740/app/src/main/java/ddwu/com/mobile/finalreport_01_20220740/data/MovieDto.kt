package ddwu.com.mobile.finalreport_01_20220740.data

import android.text.Editable
import java.io.Serializable

class MovieDto(val id: Int, var movie: String, var director: String, var date: String, var rate: Int, var summary: String) : Serializable {
    override fun toString() = "$id - $movie ( $director )"
}