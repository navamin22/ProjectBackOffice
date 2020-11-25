package com.example.navamin.Views.DialogFragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.navamin.Model.Borrow
import com.example.navamin.Model.Machine
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.Views.Activity.MainActivity_Option
import com.example.navamin.Views.Activity.Return
import com.example.navamin.databinding.DialogButtonRepayBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main_activity6__borrow.*
import kotlinx.android.synthetic.main.dialog_icon_repay.*
import kotlinx.android.synthetic.main.recycler_item_stcok.*

class ButtonRepay(private val aRetun: Return) : DialogFragment() {
    private lateinit var myContext: Context
    lateinit var binding: DialogButtonRepayBinding
    private var id: String? = null
    private var machine_id: String? = null
    private var brand: String? = null
    private var model: String? = null
    private var serialnumber: String? = null
    private var borrowName: String? = null
    private var date_borrow: String? = null
    private var date_return: String? = null
    private var objective: String? = null
    private var status: String? = null

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Machine")
    val myRef2 = database.getReference("Stock")
    val myRef3 = database.getReference("Borrow")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_button_repay,container,false)
        initInstance()


        return binding.root
    }

    private fun initInstance(){
        binding.delBtn.setOnClickListener {
            checkItem()
            dismiss()
        }
        binding.noBtn.setOnClickListener{
            dismiss()
        }
//        println("stock is $stockList")

    }

    fun checkItem(){
        val machineList = ArrayList<Machine>()
        val listname2 = ArrayList<Stock>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children) {
                        val machine: Machine? = postSnapshot.getValue(Machine::class.java)
                        machineList.add(machine!!)
                    }
                    for (i in machineList.indices){
                        if (machine_id == machineList[i].id) {
                            println("yes")

                            val stockId = machineList[i].stock_id
                            myRef2.addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        for (postSnapshot in snapshot.children) {
                                            val stock: Stock? = postSnapshot.getValue(Stock::class.java)
                                            listname2.add(stock!!)
                                        }

                                        for (k in listname2.indices){
                                            if (stockId == machineList[k].stock_id){
                                                println("Yes")


                                                val stock = Stock(
                                                    listname2[i].id,
                                                    listname2[i].brand,
                                                    listname2[i].model,
                                                    listname2[i].model_id,
                                                    listname2[i].quantity,
                                                    (listname2[i].quantity_enable.toInt() + 1).toString()
                                                )

                                                val borrow = Borrow(
                                                    id.toString(),
                                                    brand.toString(),
                                                    model.toString(),
                                                    borrowName.toString(),
                                                    serialnumber.toString(),
                                                    date_borrow.toString(),
                                                    date_return.toString(),
                                                    objective.toString(),
                                                    machine_id.toString(),
                                                    "Return"
                                                )

                                                myRef3.child(id.toString()).setValue(borrow)
                                                myRef2.child(stock.id).setValue(stock)

                                                aRetun.queryData()
                                            }
                                        }

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })


                            break
                        } else {
                            println("No")
                        }
                    }
                    println("machine is $machineList")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey("id")
            it.containsKey("machine_id")
            it.containsKey("brand")
            it.containsKey("model")
            it.containsKey("serialnumber")
            it.containsKey("borrowName")
            it.containsKey("date_borrow")
            it.containsKey("date_return")
            it.containsKey("objective")
            it.containsKey("status")
        }?.apply {
            id = getString("id")
            machine_id = getString("machine_id")
            brand = getString("brand")
            model = getString("model")
            serialnumber = getString("serialnumber")
            borrowName = getString("borrowName")
            date_borrow = getString("date_borrow")
            date_return = getString("date_return")
            objective = getString("objective")
            status = getString("status")

            println("checkid is $id")
            println("checkmachine is $machine_id")
            println("checkserialnumber is $serialnumber")
            println("checkstatus is $status")
        }


            setStyle(STYLE_NO_INPUT, android.R.style.Theme)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.decorView?.systemUiVisibility
            //dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation //Animation xml
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
            setStyle(STYLE_NO_INPUT, android.R.style.Theme)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

}