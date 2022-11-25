package kr.ac.hallym.backgroundnotice.Controller

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.backgroundnotice.databinding.ActivityMainBinding
import kr.ac.hallym.backgroundnotice.databinding.ItemRecyclerviewBinding
import kr.ac.hallym.prac07_jetpack_homework.Adapter.MyAdapter


/**
 * @param context 넣기
 * @param recyclerView 요소
 */
class Recycler(val context: Context, val recyclerView: RecyclerView){

    /**
     * @param MutableList<String> : 아이템을 추가할 리스트
     * 리사이클러 뷰에 아이템을 추가해주는 함수
     */
    fun makeElemets(contents : MutableList<String>, keywordCode : MutableList<String>,
                    binding: ActivityMainBinding){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =  LinearLayoutManager( context)
        recyclerView.adapter = MyAdapter(context, contents, keywordCode, binding)
        recyclerView.addItemDecoration(DividerItemDecoration( context , LinearLayoutManager.VERTICAL))


    }
}