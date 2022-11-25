package kr.ac.hallym.backgroundnotice.Controller

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import kr.ac.hallym.backgroundnotice.MainActivity
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.model.Keyword
import kr.ac.hallym.backgroundnotice.model.PostKeywordBody
import kr.ac.hallym.networkretrofit2.retrofitApi.Retrofit2
import kr.ac.hallym.networkretrofit2.retrofitApi.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * 
 * @param context this
 * 키워드와 관련된 REST API를 처리해주는 클래스
 */
class KeywordList(val context: Context, val binding : ActivityMainBinding) {
    private val retrofit = RetrofitInstance.getInstance().create(Retrofit2::class.java) // 서비스 객체 생성
//    val binding : ActivityMainBinding = ActivityMainBinding.inflate( LayoutInflater.from(context) )



        // MainActivity 클래스 밖에서 binding을 얻는 법
        // 참고 : https://developer-joe.tistory.com/39

        /**
         * 키워드 리스트를 조회 후 리사이클러 뷰에 업데이트
         */
        fun getKeywordList(){
            retrofit.getFunction( MainActivity.USERID ).enqueue(object: Callback<List<Keyword>> {
                override fun onResponse(call: Call<List<Keyword>>, response: Response<List<Keyword>>) {
                    if(response.isSuccessful){
                        // 정상적으로 통신이 성고된 경우
                        var keywordList: List<Keyword>? = response.body();
//                    Log.d("namjung", "onResponse 성공: " + keywordList.toString());
//                    binding.title.setText( "키워드 리스트 userid = " + result )

                        //  키워드 리스트 리사이클러뷰
                        val contents = mutableListOf<String>()
                        val keywordCodes = mutableListOf<String>()
                        keywordList?.map { keyword: Keyword ->
                            contents.add(keyword.keyword)
                            keywordCodes.add(keyword.listcode)
                            true
                        }

                        val keywordListView = Recycler(context, binding.recyclerView)
                        keywordListView.makeElemets( contents, keywordCodes , binding)




                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("namjung", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<List<Keyword>>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("namjung", "onFailure 에러: " + t.message.toString());
                }


            })


        }

        /**
         * @param PostKeywordBody 삭제할 키워드의 DTO 객체
         * 추가할 키워드를 POST 요청
         */
        fun postKeywordList(PostKeywordBody : PostKeywordBody){

            retrofit.postFunction( PostKeywordBody ).enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if(response.isSuccessful){

                        var keywordList: String? = response?.message();
                        Log.d("namjung", "onResponse 성공: " + keywordList?.toString());


                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("namjung", "onResponse 실패")
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("namjung", "onFailure 에러: " + t.message.toString());
                }
            })

            val handler = android.os.Handler()
            handler.postDelayed({
                getKeywordList()
            }, 500)


        }




}