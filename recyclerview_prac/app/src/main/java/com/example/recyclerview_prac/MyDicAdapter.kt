package com.example.recyclerview_prac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyDicAdapter(val
                   items:ArrayList<MyDicData>):RecyclerView.Adapter<MyDicAdapter.MyViewHolder>() {
    interface OnItemClickListener{
        fun OnItemClick(holder: RecyclerView.ViewHolder, view: View,
                        data:MyDicData, position:Int)
    }
    var itemClickListener:OnItemClickListener?=null
    fun changeVisible(pos:Int){
        items[pos].visible = !items[pos].visible
        notifyItemChanged(pos)
    }
    fun moveItem(oldPos:Int, newPos:Int){
        val item = items[oldPos]
        items.removeAt(oldPos) //예전 위치에서 삭제
        items.add(newPos, item)//새로운 위치에 삽입
        //이렇게 해도 자동으로 반영 X notify 로 알려주기
        notifyItemMoved(oldPos, newPos)
    }
    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
    inner class MyViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView){
        //이 ViewHolder 로 adapter 을 만들겠다
        //itemView 는 row.xml 이 들어오게 된다. 그중의 rowView 찾아서
        //매핑
        val textView: TextView =
                itemView.findViewById(R.id.rowView)
        val meaningView:TextView =
                itemView.findViewById(R.id.meaningView)
        init{
            textView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it,
                        items[adapterPosition], adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): MyViewHolder {
        //ViewHolder 생성, Layout 에 해당하는 객체를 인스턴스화 시켜서
        //holder 객체를 만드는데 입력으로 넣어줌
        //LayoutInflater: 시스템에서 제공, xml 데이터를 인스턴스화
        //시킨다.
                //이 class 는 Adapter 만 상속받아서 context 가 없기 때문에
        //parent 라는 viewgroup 에서 context 가져온다
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.row, parent,
                        false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        //아이템의 전체 개수
        return items.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position:
    Int) {
        //holder 에 담고 있는 멤버에 데이터를 연결시켜주는 기능 수행
        holder.textView.text = items[position].word
        holder.meaningView.text = items[position].meaning
        if(items[position].visible)
            holder.meaningView.visibility = View.VISIBLE
        else
            holder.meaningView.visibility = View.GONE
    }
}
