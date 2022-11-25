package kr.ac.hallym.prac07_jetpack_homework.Adapter

import android.app.Activity
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.backgroundnotice.Controller.KeywordList
import kr.ac.hallym.backgroundnotice.Controller.Recycler
import kr.ac.hallym.backgroundnotice.MainActivity
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.databinding.ItemRecyclerviewBinding
import kr.ac.hallym.backgroundnotice.model.Keyword
import kr.ac.hallym.networkretrofit2.retrofitApi.Retrofit2
import kr.ac.hallym.networkretrofit2.retrofitApi.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @param (this, 키워드 리스트, 게시판코드리스트)
 * 키워드 요소들을 받아서 리사이클러 뷰에 추가하는 코드
 */
class MyAdapter(val context: Context, val contents: MutableList<String>, val keywordCode: MutableList<String>, val binding: ActivityMainBinding) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val retrofit = RetrofitInstance.getInstance().create(Retrofit2::class.java) // 서비스 객체 생성


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
        ItemRecyclerviewBinding.inflate(LayoutInflater.from( parent.context ), parent, false))


    override fun getItemCount(): Int {
//        Log.d("namjung", "init contents size : ${contents.size}")
        return contents.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d("namjung", "onBindViewHolder : $position")
        val binding = (holder as MyViewHolder).binding
        // 뷰에 데이터 출력
        binding.KeywordListTitle.text = contents[position]
        binding.KeywordListCode.text = keywordCode[position]
        // 삭제 이벤트 추가.
        binding.KeywordListDelete.setOnClickListener{
            deleteKeywordList(contents[position])
            getKeywordList()
        }
    }

    /**
     * @param deleteKeyword 삭제할 키워드 문자열
     * 키워드 삭제 요청
     */
    fun deleteKeywordList(deleteKeyword : String){

        retrofit.deleteFunction(MainActivity.USERID, deleteKeyword ).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.isSuccessful){

                    var resopnse: String? = response?.message();
                    Log.d("namjung", "onResponse 성공: " + resopnse?.toString());


                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("namjung", "onResponse 실패")
                }

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d("namjung", "onFailure 에러: " + t.message.toString());
            }

        } )

    }

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

}

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

