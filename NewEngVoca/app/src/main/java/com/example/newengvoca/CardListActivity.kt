package com.example.newengvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.RecyclerView

class CardListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardListAdapter: CardAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var cardViewModel:CardViewModel

    private var deckId:Int = 0

    var isAllFlip:Boolean = false

    var isAddCardOpen:Boolean = false

    var isEditCard:Boolean = false
    var selectedEditCardId:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!isAddCardOpen)
            menuInflater.inflate(R.menu.menu_card_list,menu)
        else
            if(isEditCard)
                menuInflater.inflate(R.menu.menu_card_list_edit, menu)
            else
                menuInflater.inflate(R.menu.menu_card_list_add, menu)
        return true
    }
}