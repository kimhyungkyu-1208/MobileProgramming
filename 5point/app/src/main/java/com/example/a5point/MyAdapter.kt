package com.example.a5point

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items:List<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var wordTxt : TextView = itemView.findViewById(R.id.word)
        var meaningTxt : TextView = itemView.findViewById(R.id.meaning)
        var linearLayout : LinearLayout = itemView.findViewById(R.id.linearLayout)
        var expandablelayout : RelativeLayout = itemView.findViewById(R.id.expandable_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mydata : MyData = items[position]
        holder.wordTxt.text = mydata.word
        holder.meaningTxt.text = mydata.meaning

        val isExpandable: Boolean = items[position].isExpanded
        holder.expandablelayout.visibility = if(isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val mydata = items[position]
            mydata.isExpanded = !mydata.isExpanded
            notifyItemChanged(position)
        }
    }

}