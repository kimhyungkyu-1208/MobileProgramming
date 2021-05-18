package com.example.a5point

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintStream

class AddVocaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voca)
        init()
    }

    private fun init(){
        val addbtn = findViewById<Button>(R.id.button3)
        val cancelbtn = findViewById<Button>(R.id.button4)
        val editword = findViewById<EditText>(R.id.words)
        val editmeaning = findViewById<EditText>(R.id.meanings)

        addbtn.setOnClickListener {
            val word = editword.text.toString()
            val meaning = editmeaning.text.toString()
            writeFile(word, meaning)
        }

        cancelbtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()
        val intent = Intent()
        intent.putExtra("voc", MyData(word, meaning, false))
        setResult(Activity.RESULT_OK, intent)
        finish()

    }
}