package com.example.myenglishvocabularyapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myNumberViewModel: MyNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.e(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myNumberViewModel = ViewModelProvider(this).get(MyNumberViewModel::class.java)

        myNumberViewModel.currentValue.observe(this, Observer {
            Log.e(TAG, "LiveData : $it")
            number_textview.text = it.toString()
        })

        plus_btn.setOnClickListener(this)
        minus_btn.setOnClickListener(this)


    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onClick(view: View?) {

        val userInput = userinput_edittext.text.toString().toInt()

        when (view) {
            plus_btn ->
                myNumberViewModel.updateValue(actionType = ActionType.PLUS, input = userInput)
            minus_btn ->
                myNumberViewModel.updateValue(actionType = ActionType.MINUS, input = userInput)
        }


    }
}