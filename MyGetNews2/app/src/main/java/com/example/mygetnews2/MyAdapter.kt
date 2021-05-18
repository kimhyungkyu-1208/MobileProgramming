package com.example.mygetnews2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygetnews2.databinding.RowBinding

class MyAdapter(val items:ArrayList<MyData>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View, data: MyData, position: Int)
    }
    var itemClickListener: OnItemClickListener? = null


    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) { // 뷰 홀더는 뷰 객체를 참조한다
        init {
            binding.newstitle.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = RowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.newstitle.text = items[position].newstitle
    }


    override fun getItemCount(): Int {
        return items.size
    }

}