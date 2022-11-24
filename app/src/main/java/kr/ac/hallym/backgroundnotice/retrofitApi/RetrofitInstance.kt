package kr.ac.hallym.networkretrofit2.retrofitApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 레트로핏 객체 생성을 위한 object
object RetrofitInstance {
    val BASE_URL = "http://namjung.blog:8084/"

    val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance(): Retrofit{
        return client
    }
}