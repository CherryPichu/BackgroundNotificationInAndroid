package kr.ac.hallym.backgroundnotice


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kr.ac.hallym.backgroundnotice.Controller.KeywordList
import kr.ac.hallym.backgroundnotice.Controller.Recycler
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.model.DatabaseHelper
import kr.ac.hallym.backgroundnotice.model.Keyword
import kr.ac.hallym.backgroundnotice.model.PostKeywordBody
import kr.ac.hallym.backgroundnotice.model.UserResponse
import kr.ac.hallym.networkretrofit2.Model.UserTable
import kr.ac.hallym.networkretrofit2.retrofitApi.Retrofit2
import kr.ac.hallym.networkretrofit2.retrofitApi.RetrofitInstance
import retrofit2.*

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater);
    };

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }

    companion object{
        var USERID = -1;
    }
    private val retrofit = RetrofitInstance.getInstance().create(Retrofit2::class.java) // 서비스 객체 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        val keywordview = KeywordList(this, binding) // restapi 요청을 보낼 수 있는 클래스


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // userid를 DB에서 꺼내와서 MainActivity.USERID 에 저장.
        var userid : MutableList<UserTable> = dbHelper.getAllData()
        Log.d("namjung", userid.count().toString())
        if(userid.count() != 0){
            MainActivity.USERID = userid[0].userid

            keywordview.getKeywordList() // 조회
            MainActivity.USERID = userid[0].userid;
            setnaviuserid("유저 아이디 : " + MainActivity.USERID )

        }else { // // 저장된 userid가 없으면 새로운 userid를 서버로부터 받아와서 db에 저장함.
//            dbHelper.insertData(4);
//            userid = dbHelper.getAllData()

            retrofit.getUser().enqueue( object : Callback<UserResponse> {
                            override fun onResponse(
                                call: Call<UserResponse>,
                                response: Response<UserResponse>
                            ) {
                                if (response.isSuccessful) {
                                    // 정상적으로 통신이 성고된 경우
                                    var newuserid: UserResponse? = response.body();
                                    dbHelper.insertData(newuserid!!.userid) // db에 저장
                                    MainActivity.USERID = newuserid!!.userid
                                    keywordview.getKeywordList()
                                    setnaviuserid("유저 아이디 : " + MainActivity.USERID)

                                } else {
                                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                    Log.d("namjung", "onResponse 실패")
                                }
                            }

                            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                Log.d("namjung", "onFailure 에러: " + t.message.toString());
                            }
            })
        }



        // navigation View 접근
        val headerView : View = binding.mainDrawerView.getHeaderView(0);
        headerView.findViewById<Button>(R.id.Btn_jobstart).setOnClickListener {
            displayMessage("account clicked")
        }

        // user 테이블 제거
        headerView.findViewById<Button>(R.id.Btn_deleteUserDB).setOnClickListener {
            dbHelper.dropTable()
            finish()
        }


        // 키워드 추가 이벤트
        binding.buttonSendKeyword.setOnClickListener {
                val listcode = when (binding.radioGroup.checkedRadioButtonId){
                    binding.radioBtMN230.id ->
                        "MN230"
                    binding.radioBtMN231.id ->
                        "MN231"
                    binding.radioBtMN233.id ->
                        "MN233"
                    binding.radioBtMN445.id ->
                        "MN445"
                    else ->
                        error("해당하는 list 코드가 없는 에러")
                }
                val keywordBody = PostKeywordBody(USERID, binding.EditKeyword.text.toString(), listcode)
                postKeywordList(keywordBody)

        }

    }

    private fun displayMessage(message :String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 네비게이션뷰 header 에 userID : xx 설정
     */
    fun setnaviuserid(text :String) {
        val headerView : View = binding.mainDrawerView.getHeaderView(0);
        headerView.findViewById<TextView>(R.id.userid).setText(text)
    }

    fun postKeywordList(PostKeywordBody : PostKeywordBody){

        retrofit.postFunction( PostKeywordBody ).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                if(response.isSuccessful){

                    var keywordList: String? = response?.message();
                    Log.d("namjung", "onResponse 성공: " + keywordList?.toString());
                    getKeywordList()
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
    }

    fun getKeywordList(){
        val context = this
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

                    val keywordListView = Recycler(context , binding.recyclerView)
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


