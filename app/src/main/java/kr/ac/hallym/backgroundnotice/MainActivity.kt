package kr.ac.hallym.backgroundnotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.hallym.backgroundnotice.Controller.KeywordList
import kr.ac.hallym.backgroundnotice.model.PostKeywordBody
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.model.DatabaseHelper

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater);
    };

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }


    companion object{
        val USERID = 4;

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        binding.title.setText( "키워드 리스트 userid = " + USERID )


        val keywordview = KeywordList(this, binding)
        keywordview.getKeywordList() // 조회


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
            keywordview.postKeywordList(keywordBody)

        }


    }




}