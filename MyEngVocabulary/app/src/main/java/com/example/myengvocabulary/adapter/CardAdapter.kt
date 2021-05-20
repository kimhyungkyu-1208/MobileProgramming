package com.example.myengvocabulary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.SeekBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myengvocabulary.R
import com.example.myengvocabulary.database.Card
import java.util.*

class CardAdapter (private val seekBar: SeekBar?, private val type:Int, private val cardOpenFunction:(Boolean, Int)->Unit): RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    inner class ViewHolder (v: View, type:Int): RecyclerView.ViewHolder(v){
        lateinit var textView: TextView
        lateinit var cardView: CardView
        init{
            when(type){
                0-> {
                    textView = v.findViewById<TextView>(R.id.textview_card_text)
                    cardView = v.findViewById<CardView>(R.id.cardview_card)
                }
                1->{
                    textView = v.findViewById<TextView>(R.id.textview_play_card_text)
                    cardView = v.findViewById<CardView>(R.id.cardview_play_card)
                }
            }
        }
    }

    var originData:List<Card> = listOf()
    var viewData:List<Card> = listOf()
    var flipSet: BitSet = BitSet()
    var isAllFlip:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = when(type){
            0-> LayoutInflater.from(parent.context).inflate(R.layout.item_card,parent,false)
            1-> LayoutInflater.from(parent.context).inflate(R.layout.item_card_play,parent,false)
            else-> LayoutInflater.from(parent.context).inflate(R.layout.item_card_play,parent,false)
        }
        val viewHolder = ViewHolder(v,type)
        val curView = viewHolder.cardView
        val curText = viewHolder.textView
        curView.setOnClickListener {
            when(type){
                0->{
                    cardOpenFunction(true,viewData[viewHolder.adapterPosition].id.toInt())
                }
                1->{
                    flipAnimation(curView)
                    flipSet[viewHolder.adapterPosition] = !flipSet[viewHolder.adapterPosition]
                    if(flipSet[viewHolder.adapterPosition]) {
                        curText.text = viewData[viewHolder.adapterPosition].back
                    }
                    else {
                        curText.text = viewData[viewHolder.adapterPosition].front
                    }
                }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(type==0)
            holder.itemView.startAnimation(AlphaAnimation(0f,1f).apply{
                duration=300
            })
        else
            flipAnimation(holder.cardView)

        if(flipSet[position])
            holder.textView.text = viewData[holder.adapterPosition].back
        else
            holder.textView.text = viewData[holder.adapterPosition].front
    }

    override fun getItemCount(): Int = viewData.size

    fun setCards(cardList:List<Card>){
        viewData = cardList
        originData = cardList
        flipSet = BitSet(viewData.size)
        notifyDataSetChanged()
        if(seekBar!=null)
            seekBar.max = viewData.size-1
    }

    fun flipAnimation(cardView: CardView){
        //cardView.startAnimation(AlphaAnimation(1f,0f).apply{duration=200})
        cardView.startAnimation(AlphaAnimation(0f,1f).apply{duration=300})
    }

    fun flipCard(position: Int){
        if(viewData.isNotEmpty()) {
            flipSet.flip(position)
            notifyItemChanged(position)
        }
    }

    fun flipAllCards(){
        if(viewData.isNotEmpty()) {
            isAllFlip = !isAllFlip
            if (isAllFlip)
                flipSet.set(0, flipSet.size() - 1, true)
            else
                flipSet.set(0, flipSet.size() - 1, false)
            notifyItemRangeChanged(0,viewData.size)
        }
    }

    fun shuffleCards(){

        val dataArray = mutableListOf<Card>()
        viewData.forEach{
            dataArray.add(it)
        }
        var piv = 1
        for(i in 0 until viewData.size-1){
            var rand = (piv until viewData.size).random()
            dataArray[i] = dataArray[rand].also {
                dataArray[rand] = dataArray[i]
            }
            flipSet[i] = flipSet[rand].also{flipSet[rand]=flipSet[i]}
            piv++
        }
        viewData = dataArray.toList()

        //Collections.shuffle(viewData)
        notifyItemRangeChanged(0,viewData.size)
    }
}