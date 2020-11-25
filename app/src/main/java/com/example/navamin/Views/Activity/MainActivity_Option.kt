package com.example.navamin.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.navamin.Control.Control
import com.example.navamin.Model.Brand
import com.example.navamin.Model.ModelBrand
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ActivityOptionBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_option.*
import kotlinx.android.synthetic.main.recycler_item_stcok.*

class MainActivity_Option : AppCompatActivity() {

    private lateinit var binding: ActivityOptionBinding
    private val stockList = ArrayList<Stock>()
    private val brandList = ArrayList<Brand>()
    private val modelList = ArrayList<ModelBrand>()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Stock")
    val myRef2 = database.getReference("Brand")
    val myRef3 = database.getReference("ModelBrand")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_option
        )
        supportActionBar!!.title = "Modern POS"

//        intent ส่งค่าไปอีก activity
//        binding.button.text = intent.getStringExtra("username")
//        binding.button2.text = intent.getStringExtra("password")

        getStockItem()
        onClickActivate()
    }

    fun onClickActivate(){
        binding.button.setOnClickListener{
            getBrandItem()
            //getModelItem()
        }
        binding.button3.setOnClickListener{
            button3()
//                    <<--เพิ่มข้อมูลใน Firebase
//            val key = myRef.push().key
//            val stock = Stock (key.toString(),"Telpo","TPS 516","8","8")
//            myRef.child(key.toString()).setValue(stock)
        }
        binding.soldout.setOnClickListener{
            getSoldout()
        }
    }

    fun getBrandItem(){
        brandList.clear()
        Control.listname2.clear()
        myRef2.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (posSnapshot in snapshot.children){
                        val brand: Brand? = posSnapshot.getValue(Brand::class.java)
                        brandList.add(brand!!)
                        Control.listname2.add(brand!!)
                    }
                    //println("brand is $brandList")

                    modelList.clear()
                    Control.listname3.clear()
                    myRef3.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()){
                                for (posSnapshot in snapshot.children){
                                    val modelBrand: ModelBrand? = posSnapshot.getValue(ModelBrand::class.java)
                                    modelList.add(modelBrand!!)
                                    Control.listname3.add(modelBrand!!)
                                }

                                val intent = Intent(this@MainActivity_Option,MainActivity3::class.java)
                                intent.putExtra("test2", Control.listname2)
                                intent.putExtra("test3", Control.listname3)
                                startActivity(intent)

                            }
                            //println("model is $modelList")
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
                    binding.button2.setOnClickListener{
                        val intent = Intent(this@MainActivity_Option, Return::class.java)
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

    fun getModelItem(){
        modelList.clear()
        Control.listname3.clear()
        myRef3.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (posSnapshot in snapshot.children){
                        val modelBrand: ModelBrand? = posSnapshot.getValue(ModelBrand::class.java)
                        modelList.add(modelBrand!!)
                        Control.listname3.add(modelBrand!!)
                    }
                    println("model is $modelList")
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

    fun getSoldout(){
        brandList.clear()
        Control.listname2.clear()
        myRef2.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (posSnapshot in snapshot.children){
                        val brand: Brand? = posSnapshot.getValue(Brand::class.java)
                        brandList.add(brand!!)
                        Control.listname2.add(brand!!)
                    }
                    //println("brand is $brandList")

                    modelList.clear()
                    Control.listname3.clear()
                    myRef3.addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()){
                                for (posSnapshot in snapshot.children){
                                    val modelBrand: ModelBrand? = posSnapshot.getValue(ModelBrand::class.java)
                                    modelList.add(modelBrand!!)
                                    Control.listname3.add(modelBrand!!)
                                }

                                val intent = Intent(this@MainActivity_Option,SoldOut::class.java)
                                intent.putExtra("test2", Control.listname2)
                                intent.putExtra("test3", Control.listname3)
                                startActivity(intent)

                            }
                            //println("model is $modelList")
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
}

