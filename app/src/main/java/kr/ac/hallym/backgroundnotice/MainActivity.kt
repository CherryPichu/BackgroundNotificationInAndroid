package kr.ac.hallym.backgroundnotice

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.core.view.get
import kr.ac.hallym.backgroundnotice.Controller.KeywordList
import kr.ac.hallym.backgroundnotice.Controller.PostKeywordBody
import kr.ac.hallym.backgroundnotice.Controller.Recycler
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.model.DatabaseHelper
import kr.ac.hallym.backgroundnotice.model.Keyword
import kr.ac.hallym.networkretrofit2.retrofitApi.Retrofit2
import kr.ac.hallym.networkretrofit2.retrofitApi.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }


    private val userid = 4;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)


        binding.title.setText( "키워드 리스트 userid = " + userid )

        //
        val keywordview = KeywordList(this,  binding)
        keywordview.getKeywordList()


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
            
//            Log.d("namjung", "$listcode")
            val keywordBody =PostKeywordBody(userid, binding.EditKeyword.text.toString(), listcode)
            keywordview.postKeywordList(keywordBody)

        }


    }




}