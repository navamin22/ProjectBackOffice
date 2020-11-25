package com.example.navamin.Views.Activity


import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.min.DialogFragment.DialogStock
import com.example.navamin.Control.Control
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.ActivityStockTestBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StockActivityTest : AppCompatActivity() {


    private lateinit var binding: ActivityStockTestBinding
    private lateinit var searchView: SearchView
    private lateinit var adapter: ArrayAdapter<*>

    private val listname: ArrayList<Stock> = ArrayList()
    private val list = ArrayList<String>()
    lateinit var dialogStock: DialogStock

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Stock")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_stock_test)
        supportActionBar!!.title = "Modern POS"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        searchView = findViewById(R.id.searchView1)
//        list = ArrayList()
//        list.add("Apple")
//        list.add("Banana")
//        list.add("Pineapple")
//        list.add("Orange")
//        list.add("Mango")
//        list.add("Grapes")
//        list.add("Lemon")
//        list.add("Melon")
//        list.add("Watermelon")
//        list.add("Papaya")
//        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
//        binding.listView1.adapter = adapter

//        binding.listView.onItemClickListener =
//            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->{}
//                Toast.makeText(applicationContext, p0.getItemAtPosition(p2).toString(), Toast.LENGTH_SHORT).show() }

        binding.listView1.setOnItemClickListener( object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val fm = supportFragmentManager
                dialogStock = DialogStock()
                dialogStock.show(fm,"member_login")

            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (list.contains(query)) {
                    adapter.filter.filter(query)
                } else {
                    Toast.makeText(this@StockActivityTest, "No Match found", Toast.LENGTH_LONG).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        initInstance()
    }


    private fun initInstance(){
        listname.clear()
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    snapshot.key
                    //Control.listname.clear()
                    for (postSnapshot in snapshot.children){
                        postSnapshot.key
                        val stock: Stock? = postSnapshot.getValue(Stock::class.java)
                        listname.add(stock!!)
                        Control.listname.add(stock!!)
                    }
//                    listname item [].brand or model
//                    roop ไว้ดึงจาก Firebase

                    for (item in listname.indices){
                        list.add(listname[item].model)
                    }

//                    ไว้เพิ่ม-ลด ข้อมูลใน Firebase
//                    listname[0].quantity.toInt() - 1
//                    var stock = Stock("","")
//                    myRef.child("Stock").child(stock.id).setValue(stock)


                    adapter = ArrayAdapter<String>(this@StockActivityTest, android.R.layout.simple_list_item_1, list)
                    binding.listView1.adapter = adapter

                    binding.listView1.setOnItemClickListener(object : AdapterView.OnItemClickListener{
                        override fun onItemClick(p0: AdapterView<*>?,p1: View?,p2: Int,p3: Long) {
                            val fm = supportFragmentManager
                            dialogStock = DialogStock()
                            val bundle = Bundle()
                            bundle.putString("brand",listname[p2].brand)
                            bundle.putString("model",listname[p2].model)
                            bundle.putString("quantity",listname[p2].quantity)
                            bundle.putString("quantity_enable",listname[p2].quantity_enable)
                            dialogStock.arguments = bundle
                            dialogStock.show(fm,"member_login")
                        }
                    })
//                    setintent()
                }
            }

//            private fun setintent(){
//                val intent = Intent(this@StockActivityTest,BorrowActivity ::class.java)
//                intent.putExtra("quantity_enable",Control.listname)
//                startActivity(intent)
//                println("stock is $listname")
//            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}


