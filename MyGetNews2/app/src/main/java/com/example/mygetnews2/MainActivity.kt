package com.example.mygetnews2

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygetnews2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url = "https://www.daum.net"
    val rssurl = "http://fs.jtbc.joins.com//RSS/culture.xml"
    val jsonurl = "http://api.icndb.com/jokes/random"
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    fun getjson() {
        scope.launch {
            val doc = Jsoup.connect(jsonurl).ignoreContentType(true).get()
            //Log.i("json", doc.text())
            val json = JSONObject(doc.text())
            val joke = json.getJSONObject("value")
            val jokestr = joke.getString("joke")
            Log.i("json joke",jokestr)
        }
    }

        fun getrssNews() {
            scope.launch {
                adapter.items.clear()
                val doc = Jsoup.connect(rssurl).parser(Parser.xmlParser()).get()
                val headlines = doc.select("item ")
                for (news in headlines) {
                    adapter.items.add(
                        MyData(
                            news.select("title").text(),
                            news.select("link").text()
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                    binding.swipe.isRefreshing = false
                }
            }
        }

        fun getNews() {
            scope.launch {
                adapter.items.clear()
                val doc = Jsoup.connect(url).get()
                val headlines = doc.select("ul.list_txt>li>a")
                for (news in headlines) {
                    adapter.items.add(MyData(news.text(), news.absUrl("href")))
                }
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                    binding.swipe.isRefreshing = false
                }
            }
        }

        private fun init() {

            binding.swipe.setOnRefreshListener {
                binding.swipe.isRefreshing = true
                getrssNews()
            }
            binding.recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = MyAdapter(ArrayList<MyData>())
            adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
                override fun OnItemClick(
                    holder: MyAdapter.ViewHolder,
                    view: View,
                    data: MyData,
                    position: Int
                ) {
                    val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].Url))
                    startActivity(intent)
                }
            }
            binding.recyclerView.adapter = adapter
            getrssNews()
            getjson()

        }
    }
