package com.greimul.simpleflashcard.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeckDao {
    @Query("SELECT * FROM deck_db")
    fun getAll(): LiveData<List<Deck>>

    @Query("SELECT * FROM deck_db WHERE id = :deckId")
    fun selectDeckByID(deckId:Int):Deck

    @Query("SELECT COUNT(*) FROM card_db WHERE deckId = :deckId")
    fun countCards(deckId: Int):LiveData<Int>

    @Query("DELETE FROM deck_db WHERE id = :deckId")
    suspend fun deleteDeckById(deckId: Int)

    @Query("DELETE FROM deck_db")
    suspend fun deleteAllDeck()

    @Insert
    suspend fun insertDeck(deck:Deck):Long
}