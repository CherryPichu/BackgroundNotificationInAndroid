package kr.ac.hallym.backgroundnotice

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.model.DatabaseHelper
import kr.ac.hallym.prac07_jetpack_homework.Adapter.MyAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding;

    private val dbHelper: DatabaseHelper by lazy {
        DatabaseHelper.getInstance(applicationContext)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        val contents = mutableListOf<String>("제목1", "제목2")


        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =  LinearLayoutManager( this)
        binding.recyclerView.adapter = MyAdapter(this, contents)
        binding.recyclerView.addItemDecoration(DividerItemDecoration( this , LinearLayoutManager.VERTICAL))



    }
}