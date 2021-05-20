package com.example.myengvocabulary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myengvocabulary.database.Deck
import com.example.myengvocabulary.database.DeckDatabase
import com.example.myengvocabulary.database.DeckRepo
import kotlinx.coroutines.launch

class DeckViewModel(application: Application): AndroidViewModel(application) {
    lateinit var repo: DeckRepo
    lateinit var deckList: LiveData<List<Deck>>
    var recentInsertedDeckId:Long = 0

    init{
        repo = DeckRepo(DeckDatabase.getDatabase(application).deckDao())
        deckList = repo.deckList
        //recentInsertedDeckId = repo.recentInsertedDeckId
    }
    fun insert(deck:Deck) = viewModelScope.launch {
        recentInsertedDeckId = repo.insert(deck)
    }

    fun delete(deckId:Int) = viewModelScope.launch{
        repo.delete(deckId)
    }
    fun deleteAllDeck()=viewModelScope.launch {
        repo.deleteAllDeck()
    }

    fun countCards(deckId: Int):LiveData<Int> = repo.countCards(deckId)
}