package com.example.navamin.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navamin.Model.Borrow
import com.example.navamin.R
import com.example.navamin.Views.Activity.SoldOut
import com.example.navamin.Views.DialogFragment.DialogSold
import com.example.navamin.Views.DialogFragment.IconDialog
import java.util.ArrayList

class SoldAdapters(private val context: Context, private val items: ArrayList<Borrow>, private val soldOut: SoldOut): RecyclerView.Adapter<SoldAdapters.ViewHolder>() {
    lateinit var dialogSold: DialogSold
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_sold, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.model.text = items[position].model
        holder.borrowName.text = items[position].borrowName

        holder.no1.text = ((1 + position).toString())
        holder.button5.setOnClickListener{
            //เรียกใช้ Dialog Fragment here
            val fm = soldOut.supportFragmentManager
            dialogSold = DialogSold()
            val bundle = Bundle()
            bundle.putString("model",holder.model.text.toString())
            bundle.putString("serialnumber",items[position].serialnumber)
            bundle.putString("borrowName",holder.borrowName.text.toString())
            bundle.putString("date_borrow",items[position].date_borrow)
            bundle.putString("date_return",items[position].date_return)
            bundle.putString("objective",items[position].objective)



            dialogSold.arguments = bundle
//            iconDialog.show(fm,"icon_dialog")
            dialogSold.show(fm,"member_login")
        }
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val model = itemsView.findViewById(R.id.number0) as AppCompatTextView
        val borrowName = itemsView.findViewById(R.id.serialnumber0) as AppCompatTextView
        val button5 = itemsView.findViewById(R.id.button0) as AppCompatButton
        val no1 = itemsView.findViewById(R.id.no0) as AppCompatTextView
    }

}
