package com.example.navamin.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navamin.Adapters.NameAdapters
import com.example.navamin.Adapters.SoldAdapters
import com.example.navamin.Control.Control
import com.example.navamin.Model.Borrow
import com.example.navamin.Model.Brand
import com.example.navamin.Model.ModelBrand
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ActivitySoldOutBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_option.*

class SoldOut : AppCompatActivity() {

    private lateinit var binding: ActivitySoldOutBinding
    private val nameList = ArrayList<Borrow>()
    private val stockList = ArrayList<Stock>()
    private val brandList = ArrayList<Brand>()
    private val modelBrandList = ArrayList<ModelBrand>()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Borrow")
    val myRef2 = database.getReference("Stock")
    val myRef3 = database.getReference("Brand")
    val myRef4 = database.getReference("ModelBrand")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_sold_out)
        supportActionBar!!.title = "Modern POS"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initInstance()
    }

    private fun initInstance() {
        queryDta()

        binding.sold.setOnClickListener {
            getStockItem()
        }

    }

    fun getStockItem(){
        stockList.clear()
        Control.listname.clear()
        myRef2.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val stock: Stock? = postSnapshot.getValue(Stock::class.java)
                        stockList.add(stock!!)
                        Control.listname.add(stock!!)
                    }

                    brandList.clear()
                    Control.listname2.clear()
                    myRef3.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (postSnapshot in snapshot.children) {
                                    val brand: Brand? = postSnapshot.getValue(Brand::class.java)
                                    brandList.add(brand!!)
                                    Control.listname2.add(brand!!)
                                }

                                modelBrandList.clear()
                                Control.listname3.clear()
                                myRef4.addListenerForSingleValueEvent(object : ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (snapshot.exists()) {
                                            for (postSnapshot in snapshot.children) {
                                                val modelBrand: ModelBrand? = postSnapshot.getValue(ModelBrand::class.java)
                                                modelBrandList.add(modelBrand!!)
                                                Control.listname3.add(modelBrand!!)
                                            }
                                            val intent = Intent(this@SoldOut, SellOut::class.java)
                                            intent.putExtra("test", Control.listname)
                                            intent.putExtra("test2", Control.listname2)
                                            intent.putExtra("test3", Control.listname3)
                                            startActivity(intent)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                })
                                println("brand is $brandList")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun queryDta(){
        nameList.clear()
        myRef.addListenerForSingleValueEvent(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val borrow: Borrow? = postSnapshot.getValue(Borrow::class.java)
                        if (borrow?.status == "SellOut") {
                            nameList.add(borrow!!)
                        }
                    }

                    val adapters = SoldAdapters(this@SoldOut, nameList, this@SoldOut)
                    binding.recyclerFood0.let {
                        it.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
                        it.adapter = adapters
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}