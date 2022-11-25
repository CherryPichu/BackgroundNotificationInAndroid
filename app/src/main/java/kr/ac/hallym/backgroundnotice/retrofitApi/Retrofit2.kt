package kr.ac.hallym.networkretrofit2.retrofitApi

import kr.ac.hallym.backgroundnotice.model.PostKeywordBody
import kr.ac.hallym.backgroundnotice.model.Keyword
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

//https://jaejong.tistory.com/33
interface Retrofit2{
    @GET("/query/KEYWORD")
    fun getFunction(
        @Query("userid") userid: Int
    ) : Call<List<Keyword>>

    @POST("/query/KEYWORD")
    fun postFunction(
        @Body PostKeywordBody : PostKeywordBody
    ) : Call<String>

    @DELETE("/query/KEYWORD")
    fun deleteFunction(
        @Query("userid") userid: Int,
        @Query("keyword") keyword : String
    ) : Call<String>




}