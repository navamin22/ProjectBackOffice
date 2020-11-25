package com.example.navamin.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.navamin.Control.Control
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ActivityOptionBinding
import com.example.navamin.databinding.PeopleStockBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_option.*

class PeopleStockActivity : AppCompatActivity() {

    private lateinit var binding: PeopleStockBinding
    private val stockList = ArrayList<Stock>()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Stock")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.people_stock
        )
        supportActionBar!!.title = "Modern POS"

//        intent ส่งค่าไปอีก activity
//        binding.button.text = intent.getStringExtra("username")
//        binding.button2.text = intent.getStringExtra("password")

        getStockItem()
        onClickActivate()
    }

    fun onClickActivate(){
//        binding.button.setOnClickListener{
//            button1()
//        }
        binding.button3.setOnClickListener{
            button3()
//                    <<--เพิ่มข้อมูลใน Firebase
//            val key = myRef.push().key
//            val stock = Stock (key.toString(),"Telpo","TPS 516","8","8")
//            myRef.child(key.toString()).setValue(stock)
        }
    }

    fun button1(){
        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }
    fun getStockItem(){
        stockList.clear()
        Control.listname.clear()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (posSnapshot in snapshot.children){
                        val stock: Stock? = posSnapshot.getValue(Stock::class.java)
                        stockList.add(stock!!)
                        Control.listname.add(stock!!)
                    }
                    binding.button3.setOnClickListener{
                        val intent = Intent(this@PeopleStockActivity, StockActivityTest::class.java)
                        intent.putExtra("test",Control.listname)
                        startActivity(intent)
                    }
                    println("stock is $stockList")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    fun button3(){
        val intent = Intent(this, StockActivityTest::class.java)
        startActivity(intent)
    }
}

