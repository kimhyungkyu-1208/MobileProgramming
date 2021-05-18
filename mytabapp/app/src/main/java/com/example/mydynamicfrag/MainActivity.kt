package com.example.mydynamicfrag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydynamicfrag.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val textarr = arrayListOf<String>("이미지", "텍스트")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
       binding.viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewPager){
            tab, position ->
            tab.text = textarr[position]
        }.attach()
    }
}