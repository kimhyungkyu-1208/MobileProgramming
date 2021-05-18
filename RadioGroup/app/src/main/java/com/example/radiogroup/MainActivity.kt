package com.example.radiogroup

import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init(){
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val imageView = findViewById<ImageView>(R.id.imageView)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton1 -> imageView.setImageResource(R.drawable.img3)
                R.id.radioButton2 -> imageView.setImageResource(R.drawable.img2)
                R.id.radioButton3 -> imageView.setImageResource(R.drawable.img1)
            }
        }
    }
}