package ddwu.com.mobileapp.week07.retrofittest.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


// @Get:  kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json
// @Query:  key
// @Query:  targetDt

interface IBoxOfficeService {
    @GET("kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.{type}")
    //suspend함수로 Coroutine함수로 만듬retropit안에서 자동적으로 바꿔줌
    suspend fun getDailyBoxOffice(
        @Path("type") type: String,
        @Query("key") key: String,
        @Query("targetDt") target: String
    ) : BoxOfficeRoot

}