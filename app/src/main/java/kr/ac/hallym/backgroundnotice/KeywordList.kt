package kr.ac.hallym.backgroundnotice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import java.util.zip.Inflater

class KeywordList : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(R.layout.activity_keyword_list)

    }
}