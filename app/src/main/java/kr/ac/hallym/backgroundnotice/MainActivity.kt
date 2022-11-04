package kr.ac.hallym.backgroundnotice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(R.layout.activity_main)
//        setContentView(R.layout.activity_keyword_list)

        binding.ButtonGoKeyword.setOnClickListener {
            val intent : Intent = Intent(this, KeywordList::class.java)
            startActivity(intent)
        }
    }
}