package com.example.myengvocabulary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myengvocabulary.database.Card
import com.example.myengvocabulary.database.CardRepo
import com.example.myengvocabulary.database.DeckDatabase
import kotlinx.coroutines.launch

class CardViewModel(application: Application, deckId: Int):AndroidViewModel(application) {
    lateinit var repo: CardRepo
    lateinit var cardList: LiveData<List<Card>>

    init{
        repo = CardRepo(DeckDatabase.getDatabase(application).cardDao(),deckId)
        cardList = repo.cardList
    }

    fun getCardFromDeck(deckId: Int):LiveData<List<Card>>{
        return repo.getCardsFromDeck(deckId)
    }

    fun selectCard(cardId: Int):LiveData<Card>{
        return repo.selectCard(cardId)
    }

    fun updateCard(cardId: Int,front:String,back:String)= viewModelScope.launch{
        repo.updateCard(cardId,front,back)
    }

    fun insert(card:Card) = viewModelScope.launch {
        repo.insert(card)
    }

    fun delete(cardId:Int) = viewModelScope.launch {
        repo.delete(cardId)
    }

}