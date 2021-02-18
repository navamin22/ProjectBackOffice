package com.example.navamin.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navamin.Control.Control
import com.example.navamin.Views.Activity.MainActivity3
import com.example.navamin.Model.Borrow
import com.example.navamin.R
import com.example.navamin.Views.DialogFragment.IconDialog
import java.util.ArrayList

class NameAdapters(private val context: Context, private val items: ArrayList<Borrow>, private val mainActivity3: MainActivity3): RecyclerView.Adapter<NameAdapters.ViewHolder>() {
    lateinit var iconDialog: IconDialog
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_activity3, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.model.text = items[position].model
        holder.borrowName.text = items[position].borrowName

        holder.no1.text = ((1 + position).toString())
        holder.button5.setOnClickListener {
            if (!Control.clicked) {
                Control.clicked = true

                //เรียกใช้ Dialog Fragment here
                val fm = mainActivity3.supportFragmentManager
                iconDialog = IconDialog()

                val bundle = Bundle()
                bundle.putString("model", holder.model.text.toString())
                bundle.putString("serialnumber", items[position].serialnumber)
                bundle.putString("borrowName", holder.borrowName.text.toString())
                bundle.putString("date_borrow", items[position].date_borrow)
                bundle.putString("date_return", items[position].date_return)
                bundle.putString("objective", items[position].objective)
                iconDialog.arguments = bundle
                iconDialog.show(fm, "member_login")

            }
        }
    }


    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val model = itemsView.findViewById(R.id.number) as AppCompatTextView
        val borrowName = itemsView.findViewById(R.id.serialnumber1) as AppCompatTextView
        val button5 = itemsView.findViewById(R.id.button5) as AppCompatButton
        val no1 = itemsView.findViewById(R.id.no1) as AppCompatTextView
    }
}
