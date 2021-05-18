package com.example.myengvoc
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class MyAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(holder:ViewHolder,view:View, data:MyData,position:Int)
    }

    var itemClickListener:OnItemClickListener?=null


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){//이벤트 처리는 뷰홀더에서 처리
    val textView: TextView = itemView.findViewById(R.id.textView)
        init{ //초기화블록
            itemView.setOnClickListener{
                itemClickListener?.onItemClick(this, it, items[adapterPosition], adapterPosition)
            }//아이템 클릭할 때마다 작업 수행!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word //이거는 뭐냐면 처음 화면에 워드(미닝말고) 뿌려준다는 거임

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun moveItem(oldPos:Int, newPos:Int){
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos,item)
        notifyItemMoved(oldPos,newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
}


