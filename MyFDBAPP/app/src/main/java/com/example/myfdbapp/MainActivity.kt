package com.example.myfdbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfdbapp.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyProductAdapter
    lateinit var rdb:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = FirebaseDatabase.getInstance().getReference("Products/items")
        val query = rdb.limitToLast(50) //질의문
        val option = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java).build()
        adapter = MyProductAdapter(option)
        adapter.itemClickListener = object : MyProductAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                binding.apply{
                    pIdEdit.setText(adapter.getItem(position).PId.toString())
                    pNameEdit.setText(adapter.getItem(position).pName.toString())
                    pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
                }
            }

        }

        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            insertbtn.setOnClickListener {

                val item = Product(pIdEdit.text.toString().toInt(),
                    pNameEdit.text.toString(),
                    pQuantityEdit.text.toString().toInt())
                rdb.child(pIdEdit.text.toString()).setValue(item)
                clearInput()
            }

            findbtn.setOnClickListener {

            }

            deletebtn.setOnClickListener {

            }

            updatebtn.setOnClickListener {

            }
        }
    }
    fun clearInput(){
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }
}