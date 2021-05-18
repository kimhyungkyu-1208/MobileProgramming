package com.example.recyclerview_prac

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class DictionaryActivity : AppCompatActivity() {
    var data:ArrayList<MyDicData> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyDicAdapter
    lateinit var tts: TextToSpeech
    var isTtsReady = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initRecyclerView()
        initTTS()
    }
    private fun initTTS(){
        //tts 가 준비되면 콜백하는 리스너 OnInitListener
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.ENGLISH
        })
    }
    private fun initRecyclerView() {
        recyclerView =
                findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        adapter = MyDicAdapter(data)
        adapter.itemClickListener =
                object:MyDicAdapter.OnItemClickListener{
                    override fun OnItemClick(
                            holder: RecyclerView.ViewHolder,
                            view: View,
                            data: MyDicData,
                            position: Int
                    ) {
                        adapter.changeVisible(position)
                        if(isTtsReady){
                            tts.speak(data.word, TextToSpeech.QUEUE_ADD,
                                    null, null)
                        }
                        //익명 클래스의 내부이므로 context 정보를
                        //applicationContext 로 준다
                        Toast.makeText(applicationContext, data.meaning,
                                Toast.LENGTH_SHORT).show()
                    }
                }
        recyclerView.adapter = adapter
        val simpleCallback =
                object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or
                        ItemTouchHelper.UP,
                        ItemTouchHelper.RIGHT){
                    override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                    ): Boolean {
                        //구현해야 되는 내용은 데이터가 옮겨지고, 삭제
                        //데이터는 실질적으로 adapter 에서 관리
                        //실질적으로 adapter class 에서 함수로 정의해야함
                        adapter.moveItem(viewHolder.adapterPosition,
                                target.adapterPosition)
                        return true
                    }
                    override fun onSwiped(viewHolder:
                                          RecyclerView.ViewHolder, direction: Int) {
                        adapter.removeItem(viewHolder.adapterPosition)
                    }
                }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
    override fun onStop() {
        super.onStop()
        tts.stop()
    }
    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
    private fun initData(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyDicData(word, meaning, false))
        }
    }
}