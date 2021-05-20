package com.example.myengvocabulary.database

import androidx.lifecycle.LiveData

class CardRepo(val cardDao:CardDao, val deckID:Int) {

    lateinit var cardList: LiveData<List<Card>>

    init{
        if(deckID != -1) {
            cardList = cardDao.getCardsFromDeck(deckID)
        }
        else{
            cardList = cardDao.getAll()
        }
    }
    suspend fun insert(card: Card){
        cardDao.insertCard(card)
    }
    fun getCardsFromDeck(deckId: Int):LiveData<List<Card>>{
        return cardDao.getCardsFromDeck(deckId)
    }
    suspend fun updateCard(cardId:Int, front:String, back:String){
        cardDao.updateCard(cardId,front,back)
    }

    fun selectCard(cardId:Int):LiveData<Card>{
        return cardDao.selectCardById(cardId)
    }

    suspend fun delete(cardId:Int){
        cardDao.deleteCardById(cardId)
    }
}