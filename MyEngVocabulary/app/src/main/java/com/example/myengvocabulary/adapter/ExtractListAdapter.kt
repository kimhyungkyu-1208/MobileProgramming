package com.example.myengvocabulary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myengvocabulary.R
import com.example.myengvocabulary.database.Card

class ExtractListAdapter : RecyclerView.Adapter<ExtractListAdapter.ViewHolder>(){
    var viewData:List<Card> = listOf()

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val extractFront = v.findViewById<TextView>(R.id.textview_extract_front)
        val extractBack = v.findViewById<TextView>(R.id.textview_extract_back)
    }
    override fun getItemCount(): Int = viewData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.extractFront.text = viewData[position].front
        holder.extractBack.text = viewData[position].back
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_extract_text,parent,false)
        return ViewHolder(v)
    }

    fun setExtractList(extractList:List<Card>){
        viewData = extractList
        notifyDataSetChanged()
    }
}