package com.example.mylogtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val MainTAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(MainTAG, "onCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onStart(){
        super.onStart()
        Log.i(MainTAG,"onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.i(MainTAG,"onResume")
    }

    override fun onStop(){
        super.onStop()
        Log.i(MainTAG,"onStop")
    }
    override fun onPause(){
        super.onPause()
        Log.i(MainTAG,"onPause")
    }
    override fun onRestart(){
        super.onRestart()
        Log.i(MainTAG,"onRestart")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(MainTAG,"onDestroy")
    }


}