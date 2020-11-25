package com.example.navamin.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navamin.Adapters.RepayAdapters
import com.example.navamin.Control.Control
import com.example.navamin.Model.Borrow
import com.example.navamin.Model.Machine
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ReturnBorrowBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Return : AppCompatActivity() {

    private lateinit var binding: ReturnBorrowBinding
    private val nameList = ArrayList<Borrow>()
    private val machineList = ArrayList<Machine>()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Borrow")
    val myRef2 = database.getReference("Machine")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.return_borrow
        )
        supportActionBar!!.title = "Modern POS"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        queryData()
    }


    fun queryData(){
        nameList.clear()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val borrow: Borrow? = postSnapshot.getValue(Borrow::class.java)
                        if (borrow?.status == "Borrowed"){
                            nameList.add(borrow!!)
                        }
                    }

//                    println("borrow is $nameList")

                    val adapters = RepayAdapters(this@Return,nameList,this@Return)
                    binding.recyclerFood.let {
                        it.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
                        it.adapter = adapters
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}