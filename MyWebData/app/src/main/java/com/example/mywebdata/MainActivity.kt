package com.example.mywebdata

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mywebdata.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun loadNetworkSomething(url: URL): String {
        var result = ""
        val connect = url.openConnection() as HttpURLConnection
        connect.connectTimeout = 4000
        connect.readTimeout = 4000
        connect.requestMethod = "GET"
        connect.connect()
        val responseCode = connect.responseCode
        if (responseCode == 200) {
            result = streamToString(connect.inputStream)
        }
        return result
    }

    fun streamToString(inputStream: InputStream): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""
        try {
            do {
                line = bufferReader.readLine()
                if (line != null)
                    result += line
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {
            Log.e("error", "읽기 실패")
        }
        return result
    }
    private fun init() {
        binding.apply {
            button.setOnClickListener{
                scope.launch {
                    var data = ""
                    progressBar.visibility = View.VISIBLE
                    withContext(Dispatchers.IO){
                        data = loadNetworkSomething(URL(editText.text.toString()))
                    }
//                    CoroutineScope(Dispatchers.IO).async {
//                        data = loadNetworkSomething(URL(editText.text.toString()))
//                    }.await()
                    textView.text = data
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}