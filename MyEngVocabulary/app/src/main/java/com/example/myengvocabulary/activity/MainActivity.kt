package com.example.myengvocabulary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myengvocabulary.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton as FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var viewPager2: ViewPager2
    lateinit var tabLatout: TabLayout
    lateinit var deckFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}