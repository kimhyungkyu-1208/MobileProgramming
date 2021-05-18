package com.example.a4autotext

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init(){
        val autoText = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val countries2 = resources.getStringArray(R.array.countries_array)
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries2)
        autoText.setAdapter(adapter)

        val multiAutoText = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        multiAutoText.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        multiAutoText.setAdapter(adapter)

        val editText = findViewById<EditText>(R.id.editText)
        val button= findViewById<Button>(R.id.button)
        editText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                button.isEnabled = str.isNotEmpty()
            }

        })
        button.setOnClickListener {
            adapter.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }

    }
}