package com.example.navamin.Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navamin.Model.Stock
import com.example.navamin.Views.Activity.StockActivityTest

class StockAdapters(private val context: Context, private val item: ArrayList<Stock>, private val stockActivityTest: StockActivityTest): RecyclerView.Adapter<StockAdapters.ViewHolder>() {


    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {

    }


}

