package com.example.myengvocabulary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myengvocabulary.database.Deck
import com.example.myengvocabulary.database.DeckDatabase
import kotlinx.coroutines.launch
import com.example.myengvocabulary.database.DeckRepo as DeckRepo

class DeckViewModel(application: Application): AndroidViewModel(application) {

    var repo: DeckRepo = DeckRepo(DeckDatabase.getDatabase(application.applicationContext).deckDao())
    var deckList: LiveData<List<Deck>> = repo.deckList
    var recentInsertedDeckId:Long = 0

//    init{
//        var repo: DeckRepo = DeckRepo(DeckDatabase.getDatabase(application.applicationContext).deckDao())
//        var deckList: LiveData<List<Deck>> = repo.deckList
//        //recentInsertedDeckId = repo.recentInsertedDeckId
//    }
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