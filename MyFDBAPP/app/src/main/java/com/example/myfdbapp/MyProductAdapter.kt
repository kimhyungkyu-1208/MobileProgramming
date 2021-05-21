package com.example.myfdbapp

import androidx.recyclerview.widget.RecyclerView
import com.example.myfdbapp.databinding.RowBinding

class MyProductAdapter (options: FirebaseRecyclerOptions<Product>):FirebaseRecyclerAdapter<Product, MyProductAdapter.ViewHolder>(options){

    inner class ViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root){

    }


}