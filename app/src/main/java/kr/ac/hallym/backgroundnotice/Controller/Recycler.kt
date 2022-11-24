package kr.ac.hallym.backgroundnotice.Controller

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.backgroundnotice.model.Keyword
import kr.ac.hallym.prac07_jetpack_homework.Adapter.MyAdapter
import retrofit2.Callback


/**
 * @param context 넣기
 * @param recyclerView 요소
 */
class Recycler(val context: Context, val recyclerView: RecyclerView){

    /**
     * @param MutableList<String> : 아이템을 추가할 리스트
     */
    fun makeElemets(contents : MutableList<String>, keywordCode : MutableList<String>){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =  LinearLayoutManager( context)
        recyclerView.adapter = MyAdapter(context, contents, keywordCode)
        recyclerView.addItemDecoration(DividerItemDecoration( context , LinearLayoutManager.VERTICAL))

    }
}