package kr.ac.hallym.backgroundnotice.JobScheduler

import android.app.Notification
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import kr.ac.hallym.backgroundnotice.MainActivity
import kr.ac.hallym.backgroundnotice.model.BackgroundResponeDto
import kr.ac.hallym.backgroundnotice.model.DatabaseHelper
import kr.ac.hallym.backgroundnotice.model.UserResponse
import kr.ac.hallym.networkretrofit2.Model.UserTable
import kr.ac.hallym.networkretrofit2.retrofitApi.Retrofit2
import kr.ac.hallym.networkretrofit2.retrofitApi.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyJobService : JobService() {
    private val retrofit = RetrofitInstance.getInstance().create(Retrofit2::class.java) // 서비스 객체 생성
    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }
    override fun onCreate(){
        Log.d("namjung", "Scheduled onCreate")
    }

    override fun onDestroy() {
        Log.d("namjung", "Scheduled onDestory")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("namjung", "Start Job")

        /**
         * 유저 TABLE 에서 userid 가져오기
         */
//        Log.d("namjung", "파이널 ")
//        Log.d("namjung", "발표 : "+dbHelper.getAllData())
        if(dbHelper.getAllData().isEmpty()){
            return true;
        }

        val userid = dbHelper.getAllData()[0].userid

        /**
         * userid 기반으로 서버에 요청
         */
        retrofit.getBackgroundRequest(userid).enqueue( object : Callback<BackgroundResponeDto> {
            override fun onResponse(
                call: Call<BackgroundResponeDto>,
                response: Response<BackgroundResponeDto>
            ) {
                if (response.isSuccessful) {
                    /**
                     * 결과의 텍스트를 받아옴
                     */
                    var res: BackgroundResponeDto? = response.body();
//                    Log.d("namjung", res?.title.toString());
                    Log.d("namjung", "keyword "+res?.keyword.toString());
//                    Log.d("namjung", res.toString());
//                    Log.d("namjung", (res?.title == null).toString());


                    /**
                     * 알람 기능
                     */
                    val notify = Notification(this@MyJobService)
                    notify.notification("키워드알람 : " + res?.title,"게시판 : " + res?.keyword );

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("namjung", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<BackgroundResponeDto>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("namjung", "onFailure 에러: " + t.message.toString());
            }
        })


        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("namjung", "Stop Job")
        return false // false : 작업이 완벽하게 종료되었음을 의미한다.
        // true : 작입이 아직 끝나지 않았음을 의미한다.
    }

}