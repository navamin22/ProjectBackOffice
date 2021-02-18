package com.example.navamin.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navamin.Adapters.NameAdapters
import com.example.navamin.Control.Control
import com.example.navamin.Model.Borrow
import com.example.navamin.Model.Brand
import com.example.navamin.Model.ModelBrand
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ActivityMain3Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding
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
            R.layout.activity_main3)
        supportActionBar!!.title = "Modern POS"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initInstance()


    }

    private fun initInstance(){
        queryData()
//        getStockItem()

        binding.button4.setOnClickListener {
            if (!Control.clicked) {
                Control.clicked = true
//            button1()
                getStockItem()
            }
//        binding.button5.setOnClickListener {
//            val intent = Intent(this, MainActivity6_Borrow::class.java)
//            startActivity(intent)
//        }
//        binding.button6.setOnClickListener {
//            val intent = Intent(this, MainActivity6_Borrow::class.java)
//            startActivity(intent)
//        }
        }
        Control.clicked = false
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        println("on resume")
    }

    override fun onPause() {
        super.onPause()
        println("on Pause")
    }

    override fun onStop() {
        super.onStop()
        Control.clicked = false
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
                                            val intent = Intent(this@MainActivity3, BorrowActivity::class.java)
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

    fun getBrandItem(){
        brandList.clear()
        Control.listname2.clear()
        myRef3.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val brand: Brand? = postSnapshot.getValue(Brand::class.java)
                        brandList.add(brand!!)
                        Control.listname2.add(brand!!)
                    }

//                    val intent = Intent(this@MainActivity3,BorrowActivity::class.java)
//                    intent.putExtra("test", Control.listname2)
//                    startActivity(intent)

//                    println("brand is $brandList ")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getModelItem(){
        modelBrandList.clear()
        Control.listname3.clear()
        myRef4.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val modelBrand: ModelBrand? = postSnapshot.getValue(ModelBrand::class.java)
                        modelBrandList.add(modelBrand!!)
                        Control.listname3.add(modelBrand!!)
                    }

//                    val intent = Intent(this@MainActivity3,BorrowActivity::class.java)
//                    intent.putExtra("test", Control.listname3)
//                    startActivity(intent)

//                    println("model is $modelBrandList ")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun queryData(){
        nameList.clear()
        myRef.addListenerForSingleValueEvent(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val borrow: Borrow? = postSnapshot.getValue(Borrow::class.java)
                        if (borrow?.status == "Borrowed"){
                            nameList.add(borrow!!)
                        }
                    }

                    val adapters = NameAdapters(this@MainActivity3,nameList,this@MainActivity3)
                    binding.recyclerFood.let {
                        it.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
                        it.adapter = adapters
                    }
//                    binding

//                    binding.name.text = nameList [0].name
//                    binding.number.text = nameList [0].number
//                    binding.name1.text = nameList [1].name
//                    binding.number1.text = nameList [1].number
                }

//                if(snapshot.exists()) {
//                    val name = binding.appCompatTextView9.toString()
//                    val number = binding.appCompatTextView8.toString()
//                    for (a in snapshot.children) {
//                        val name = a.getValue(Name::class.java)
//                        println("Name = ${name!!.name} Number = ${name.number}")
//
//                        if (name.number == number && name.number == number) {
//                            println("yes it have")
//                        }
//
//                    }
//
//                } else {
//                    println("No it has not")
//
//                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        
    }



}

