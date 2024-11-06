package ddwu.com.mobileapp.week07.retrofittest.data.network

import android.content.Context
import android.util.Log
import ddwu.com.mobileapp.week07.retrofittest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefService(val context: Context) {
    val TAG = "RefService"
    val movieService: IBoxOfficeService

    init {
        //retrofit 객체 생성 : 인터페이스 함수 자동으로 만들고 사용 가능
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.kobis_url))
            .addConverterFactory( GsonConverterFactory.create() ) //convertor설정
            .build()


        movieService =  retrofit.create(IBoxOfficeService::class.java) //IBoxOfficeService객체 생성됨
    }

    suspend fun getMovies(key: String, date: String)  : List<Movie>?   {
//        val movieCallback = object : Callback<BoxOfficeRoot> {
//            //성공
//            override fun onResponse(call: Call<BoxOfficeRoot>, response: Response<BoxOfficeRoot>) {
//                if (response.isSuccessful) {
//                    val boxOfficeRoot : BoxOfficeRoot? = response.body()
//                    Log.d(TAG, boxOfficeRoot.toString())
//                    val movies = boxOfficeRoot?.movieResult?.movieList
//                    movies?.forEach { movie -> // 영화 하나하나씩 꺼냄
//                        Log.d(TAG, movie.toString())
//                    }
//                }
//            }
//            //실패
//            override fun onFailure(call: Call<BoxOfficeRoot>, t: Throwable) {
//                Log.d(TAG, t.stackTraceToString())
//            }
//        }
//
//        val movieCall : Call <BoxOfficeRoot> =
//            movieService.getDailyBoxOffice("json", key, date)/* IBoxOfficeService 의 함수 호출 */
////        movieCall.enqueue(movieCallback)    // 1) 비동기적
//
//        val response = movieCall.execute() // 2) 동기적
//        response.body()?.movieResult?.movieList?.forEach {movie ->
//            Log.d(TAG, movie.toString())
//        }
//        return null // response.body()?.boxOfficeResult?.boxOfficeList

        // Coroutine함수로 만들었을 때
        val boxOfficeRoot = movieService.getDailyBoxOffice("json", key, date)
        return boxOfficeRoot.movieResult.movieList


    }

}