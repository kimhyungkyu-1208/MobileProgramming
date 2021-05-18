package com.example.textinput

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val emailText = findViewById<TextInputEditText>(R.id.emailText)
        emailText.addTextChangedListener {
            if(it.toString().contains('@')){
                textInputLayout.error = null
            }else{
                textInputLayout.error = "이메일 형식이 올바르지 않습니다."
            }
        }
    }
}