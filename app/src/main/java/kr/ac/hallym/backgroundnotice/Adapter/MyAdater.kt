package kr.ac.hallym.prac07_jetpack_homework.Adapter

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.NonDisposableHandle.parent
import kr.ac.hallym.backgroundnotice.databinding.ItemRecyclerviewBinding



/**
 * @param (this, 키워드 리스트, 게시판코드리스트)
 * 키워드 요소들을 받아서 리사이클러 뷰에 추가하는 코드
 */
class MyAdapter (val context : Context,  val contents : MutableList<String>, val keywordCode : MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
        ItemRecyclerviewBinding.inflate(LayoutInflater.from( parent.context ), parent, false))


    override fun getItemCount(): Int {
        Log.d("namjung", "init contents size : ${contents.size}")
        return contents.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("namjung", "onBindViewHolder : $position")
        val binding = (holder as MyViewHolder).binding
        // 뷰에 데이터 출력
        binding.KeywordListTitle.text = contents[position]
        binding.KeywordListCode.text = keywordCode[position]
        // 삭제 이벤트 추가.
        binding.KeywordListDelete.setOnClickListener{
            Log.d("namjung", "item root click : $position")
        }
    }

}

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

