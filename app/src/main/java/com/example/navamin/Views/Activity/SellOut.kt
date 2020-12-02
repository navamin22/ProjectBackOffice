package com.example.navamin.Views.Activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.navamin.Model.*
import com.example.navamin.R
import com.example.navamin.databinding.ActivitySellOutBinding
import com.example.navamin.snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main_activity6__borrow.*
import kotlinx.android.synthetic.main.dialog_day.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SellOut : AppCompatActivity() {

    private lateinit var binding: ActivitySellOutBinding
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var arrAdapter2: ArrayAdapter<String>
    private lateinit var arrAdapter3: ArrayAdapter<String>
    private lateinit var arrAdapter4: ArrayAdapter<String>
    private lateinit var calendarList: List<Calendar>
    private val spinnerlist = ArrayList<String>()
    private val spinnerlist2 = ArrayList<String>()
    private var listname2 = ArrayList<Stock>()
    private var listname3 = ArrayList<Brand>()
    private var listname4 = ArrayList<ModelBrand>()
    private var listmachine = ArrayList<Machine>()
    private val nameList = ArrayList<Borrow>()



    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Borrow")
    val myRef2 = database.getReference("Stock")
    val myRef3 = database.getReference("Machine")
    val myRef4 = database.getReference("Brand")
    val myRef5 = database.getReference("ModelBrand")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_sell_out
        )
        supportActionBar!!.title = "Modern POS"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initInstance()
    }

    fun initInstance() {
        getStockItem()
        onClickCheck()
    }

    fun getStockItem(){
        listname2 = intent.getParcelableArrayListExtra<Stock>("test") as ArrayList<Stock>
        listname3 = intent.getParcelableArrayListExtra<Brand>("test2") as ArrayList<Brand>
        listname4 = intent.getParcelableArrayListExtra<ModelBrand>("test3") as ArrayList<ModelBrand>


        println("stock is $listname2")
        println("brand is $listname3")
        println("model is $listname4")

        for (item in listname3.indices){
            spinnerlist.add(listname3[item].brand_name)
        }


        arrAdapter3 = ArrayAdapter(
            this@SellOut, R.layout.style_spinner,
            spinnerlist
        )


        binding.spinnerBrand.adapter = arrAdapter3

        binding.spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                spinnerlist2.clear()

                for (k in listname4.indices){
                    if (listname3[p2].brand_id == listname4[k].brand_id) {

                        spinnerlist2.add(listname4[k].name_model)
                    }
                }

                arrAdapter = ArrayAdapter(
                    this@SellOut, R.layout.style_spinner,
                    spinnerlist2
                )

                binding.spinner1.adapter = arrAdapter

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        arrAdapter2 = ArrayAdapter(
            this, R.layout.style_spinner,
            arrayListOf("เฮีย","เต๊าะ","เสือ","ต้น","ขวัญ","ทิพย์","บ๊อบ","ริน","ฟลุ๊ค","มานพ","เต้","จู้","มิน")
        )
        binding.spinner.adapter = arrAdapter2
        println("Stock list is $listname2")

    }

    fun onClickCheck(){
        binding.Submit.setOnClickListener {
            checkSerialNum()
        }
        binding.day.setOnClickListener {
            val dialogDate = LayoutInflater.from(this).inflate(R.layout.dialog_day, null)
            val builderDate = AlertDialog.Builder(this).setView(dialogDate)
            val alertDialog = builderDate.show()

            dialogDate.submit_date.setOnClickListener {
                calendarList = dialogDate.dayView.selectedDates
                Log.v("Promo Date", calendarList.toString())
                Log.v("Promo Date", "Calendar size = ${calendarList.size}")

                if (calendarList.isNotEmpty()) {
                    if (calendarList.size >= 2) {
                        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                        val year_start = calendarList[0].get(Calendar.YEAR)
                        val month_start = calendarList[0].get(Calendar.MONTH)
                        val day_start = calendarList[0].get(Calendar.DAY_OF_MONTH)

                        val year_expire = calendarList[calendarList.size - 1].get(Calendar.YEAR)
                        val month_expire = calendarList[calendarList.size - 1].get(Calendar.MONTH)
                        val day_expire =
                            calendarList[calendarList.size - 1].get(Calendar.DAY_OF_MONTH)

                        calendarList[0].set(year_start, month_start, day_start)
                        calendarList[calendarList.size - 1].set(
                            year_expire,
                            month_expire,
                            day_expire
                        )

                        Log.v("Promo Start Date", sdf.format(calendarList[0].time))
                        Log.v(
                            "Promo Expire Date",
                            sdf.format(calendarList[calendarList.size - 1].time)
                        )

                        binding.day1.text = sdf.format(calendarList[0].time)
                        println(sdf.format(calendarList[0].time))
                        binding.appCompatTextView7.text =
                            sdf.format(calendarList[calendarList.size - 1].time)
                        alertDialog.dismiss()

                    } else {
                        dialogDate.snackbar("Require Start date and Expire date")
                    }
                } else {
                    dialogDate.snackbar("Require Start date and Expire date")
                }

                binding.delete.setOnClickListener {
                    binding.day1.text = ""
                    binding.appCompatTextView7.text = ""
                }
            }
        }
    }

    fun checkSerialNum(){
        val serialNum = binding.serialnumber3.text.toString()
        val key = myRef.push().key
        val spinner1 = spinner1.selectedItem.toString()
        val spinnerBrand = spinner_brand.selectedItem.toString()
        val spinner = spinner.selectedItem.toString()
        val serialnumber3 = binding.serialnumber3.text.toString().trim()
        val day1 = binding.day1.text.toString().trim()
        val dayreturn = binding.appCompatTextView7.text.toString().trim()
        val reason = binding.reason.text.toString().trim()
        val machineList = ArrayList<Machine>()


        machineList.clear()

        myRef3.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children) {
                        val machine: Machine? = postSnapshot.getValue(Machine::class.java)
                        machineList.add(machine!!)
                    }

                    var boolean = false
                    for (i in machineList.indices){
                        if (serialNum == machineList[i].serialnumber) {
                            println("Yes")
                            boolean = true

                            val stockId = machineList[i].stock_id

                                        for (k in listname2.indices){
                                            if (stockId == listname2[k].id) {
                                                println("Yes")


                                                boolean = true

                                                val borrow = Borrow(key.toString(),spinnerBrand,spinner1,spinner,serialnumber3,day1,dayreturn,reason,machineList[i].id,"SellOut")



                                                val stock = Stock(
                                                    listname2[k].id,
                                                    listname2[k].brand,
                                                    listname2[k].model,
                                                    listname2[k].model_id,
                                                    listname2[k].quantity,
                                                    (listname2[k].quantity_enable.toInt() - 1).toString()
                                                )

                                                myRef.child(key.toString()).setValue(borrow)
                                                myRef2.child(stock.id).setValue(stock)

                                                val intent = Intent(this@SellOut, SoldOut::class.java)
                                                startActivity(intent)

                                                //myRef.child(key.toString()).setValue(borrow)
                                                //Calculate Stock

                                                break
                                            }else{
                                                println("No")
                                                boolean = false
                                            }
                                        }


                            break
                        } else{
                            //Toast.makeText(this@BorrowActivity,"No",Toast.LENGTH_SHORT).show()
                            println("No")
                            boolean = false
                        }
                    }

                    if (boolean){
                        Toast.makeText(this@SellOut,"เสร็จสิ้น", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@SellOut,"กรอกข้อมูลไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}