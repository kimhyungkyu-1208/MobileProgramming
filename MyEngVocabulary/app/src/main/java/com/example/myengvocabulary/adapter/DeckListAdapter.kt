package com.example.myengvocabulary.adapter

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myengvocabulary.R
import com.example.myengvocabulary.R.layout.dialog_deck_delete
import com.example.myengvocabulary.activity.CardListActivity
import com.example.myengvocabulary.activity.DeckPlayActivity
import com.example.myengvocabulary.database.Deck
import com.example.myengvocabulary.viewmodel.DeckViewModel
import java.lang.Exception

class DeckListAdapter(val activity: FragmentActivity?, val lifecycleOwner: LifecycleOwner): RecyclerView.Adapter<DeckListAdapter.ViewHolder>(){

    private var viewData = listOf<Deck>()
    private lateinit var viewModel: DeckViewModel

    init{
        viewModel = activity?.run{
            ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(DeckViewModel::class.java)
        }?:throw Exception("invalid")
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val button_deck: Button = v.findViewById(R.id.button_deck)
        val textView_name:TextView = v.findViewById(R.id.textview_name)
        val textview_desc:TextView = v.findViewById(R.id.textview_desc)
    }

    override fun getItemCount(): Int = viewData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button_deck.text = viewData[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_deck,parent,false)
        val viewHolder = ViewHolder(v)
        viewHolder.button_deck.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(parent.context, R.style.DialogStyle)
            val dialogView = LayoutInflater.from(parent.context).inflate(R.layout.dialog_deck_click,parent,false)
            val data = viewData[viewHolder.adapterPosition]

            dialogView.findViewById<TextView>(R.id.textview_name).text = data.name
            dialogView.findViewById<TextView>(R.id.textview_desc).text = data.description
            viewModel.countCards(data.id).observe(lifecycleOwner, Observer {
                dialogView.findViewById<TextView>(R.id.textview_size).text = it.toString()
            })

            val dialog = dialogBuilder.setView(dialogView).create()

            dialogView.findViewById<Button>(R.id.button_delete).setOnClickListener {
                dialog.hide()

                val secondDialogBuilder = AlertDialog.Builder(parent.context,R.style.DialogStyle)
                val secondDialogView = LayoutInflater.from(parent.context).inflate(R.layout.dialog_deck_delete,parent,false)

                secondDialogView.findViewById<TextView>(R.id.textview_dialog_delete).text = "Are you sure you want to delete Deck \"${data.name}\""
                val secondDialog = secondDialogBuilder.setView(secondDialogView).setPositiveButton("OK"){ dialog, i ->
                    viewModel.delete(data.id)
                    dialog.dismiss()
                }.setNegativeButton("Cancel"){ dialog,i-> }.show()
            }
            dialogView.findViewById<Button>(R.id.button_edit).setOnClickListener {
                val intent = Intent(parent.context, CardListActivity::class.java)
                parent.context.startActivity(intent.putExtra("deckId",data.id))
            }
            dialogView.findViewById<Button>(R.id.button_play).setOnClickListener {
                val intent = Intent(parent.context, DeckPlayActivity::class.java)
                parent.context.startActivity(intent.putExtra("deckId",data.id).putExtra("deckName",data.name))
            }
            dialog.show()
        }
        return viewHolder
    }

    fun setDeck(deckList:List<Deck>){
        viewData = deckList
        notifyDataSetChanged()
    }
}