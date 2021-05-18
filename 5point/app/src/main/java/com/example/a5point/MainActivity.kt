package com.example.a5point

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val items = ArrayList<MyData>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val myAdapter = MyAdapter(items)
        adapter = MyAdapter(items)
        recyclerView.adapter = myAdapter
        recyclerView.setHasFixedSize(true)


    }
    fun readFileScan(scan:Scanner){
        while(scan.hasNextLine()){
            val  word = scan.nextLine()
            val  meaning = scan.nextLine()
            items.add(MyData(word,meaning,false))

        }
        scan.close()
    }

    private fun initData() {
        try{
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)
        }catch (e:Exception){
            Toast.makeText(this, "추가된 단어가 없음", Toast.LENGTH_SHORT).show()
        }
        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)



    }
}