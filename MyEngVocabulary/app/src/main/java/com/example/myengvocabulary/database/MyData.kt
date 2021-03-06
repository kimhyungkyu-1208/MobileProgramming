package com.example.myengvocabulary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "deck_db")
data class Deck(@PrimaryKey(autoGenerate = true) val id:Int,
                @ColumnInfo(name = "name") var name:String,
                @ColumnInfo(name = "description") var description:String,
                @ColumnInfo(name = "size") var size:Int)

@Entity(tableName = "card_db",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Deck::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("deckID"),
            onDelete = CASCADE)
        )
    )

data class Card(@PrimaryKey(autoGenerate = true) val id:Long,
                @ColumnInfo(name = "front") var front:String,
                @ColumnInfo(name = "back") var back:String,
                val deckId:Int)
