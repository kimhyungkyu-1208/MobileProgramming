package com.example.myvocapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DeckFragment(val deckViewModel:DeckViewModel): Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var deckListAdapter:DeckListAdapter
    private lateinit var viewManager:RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = inflater.inflate(R.layout.fragment_deck,container,false)

        deckListAdapter = DeckListAdapter(activity,this)
        deckViewModel.deckList.observe(this,
            Observer {
                    decks-> deckListAdapter.setDeck(decks)
            }
        )

        viewManager = LinearLayoutManager(activity)

        recyclerView = view.recyclerview_deck.apply{
            setHasFixedSize(true)
            adapter = deckListAdapter
            layoutManager = viewManager
            addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        }

        return view
    }
}