package com.example.myengvocabulary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Deck::class, Card::class],version = 1)
abstract class DeckDatabase: RoomDatabase(){
    abstract fun deckDao():DeckDao
    abstract fun cardDao():CardDao

    companion object{
        @Volatile
        private var INSTANCE:DeckDatabase? = null

        fun getDatabase(context: Context):DeckDatabase{
            val tempInst = INSTANCE
            if(tempInst!=null)
                return tempInst
            synchronized(this){
                val instance = Room.databaseBuilder(context, DeckDatabase::class.java, "deck_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}