package com.example.navamin.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.navamin.Control.Control
import com.example.navamin.Model.*
import com.example.navamin.R
import com.example.navamin.databinding.ActivityLoginBinding
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val machineList = ArrayList<Machine>()

    //var x : String? = null

    val database = FirebaseDatabase.getInstance()
    val userRef = database.getReference("User")
    val myRef2 = database.getReference("Machine")
    val myRef3 = database.getReference("Stock")
    val myRef4 = database.getReference("Brand")
    val myRef5 = database.getReference("ModelBrand")

    lateinit var btn_login: Button
    lateinit var btn2_register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        onClickCheck()

    }

    fun onClickCheck(){
        binding.login.setOnClickListener{
            if (!Control.clicked) { //ไว้กันกดซ้ำของปุ่ม
                Control.clicked = true
                checkUser()
//                firebasetest()
//                val intent = Intent(this,MainActivity_Option::class.java)
//                startActivity(intent)
//                finish()
//                Control.clicked = false

            }

        }


//            val intent = Intent(this@LoginActivity, MainActivity_Option::class.java)
//            startActivity(intent)


//                intent ส่งค่าไปอีก activity
//            var str1 = binding.user.text.toString()
//            var str2 = binding.pw.text.toString()
//            intent.putExtra("username",str1)
//            intent.putExtra("password",str2)
//
//            println("Str1 = $str1")
//            println("Str2 = $str2")


//            myRef.addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()){
//                        val id = binding.Id.text.toString()
//                        val pw = binding.pw.text.toString()
//                        for (a in snapshot.children){
//                            val user = a.getValue(User::class.java)
//                            println("User name = ${user!!.name}  Surname = ${user!!.surname} Id = ${user!!.id} Pw = ${user!!.pw}")
//
//                            if (user.id == id && user.pw == pw){
//                                println("yes it have")
//                            }else{
//                                println("No")
//                            }
//
//                        }
//
//                    } else {
//                        println("No it has not")
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//
//            })
//
//            <<ไว้เพิ่ม brand
//            val key = myRef4.push().key
//            val brand = Brand (key.toString(),"HESS")
//            myRef4.child(key.toString()).setValue(brand)
//
//            <<ไว้เพิ่ม modelbrand
//            val key = myRef5.push().key
//            val modelBrand = ModelBrand (key.toString(),"TM-08","-MMTk8JszC_Zh-7sTJYz")
//            myRef5.child(key.toString()).setValue(modelBrand)

//            <<--ไว้เพิ่ม stock
//            val key = myRef3.push().key
//            val stock = Stock (key.toString(),"Counter Plus","TM-08","-MOdJDi8Vjf2DRhFDl-A","17","17")
//            myRef3.child(key.toString()).setValue(stock)


//            <<--ไว้เพิ่ม machine
//            val key = myRef2.push().key
//            val machine = Machine (key.toString(),"Counter Plus","TM-08","2014091308348","Borrow","-MP276fVEwNpdDgGszb_")
//            myRef2.child(key.toString()).setValue(machine)


//            <<--ไว้เพิ่ม user
//            val key = myRef.push().key
//            val user = User (key.toString(),"1","Navamin","1","Min","member")
//            myRef.child(key.toString()).setValue(user)

//            myRef.child("Id").setValue(user)
//            myRef.child("Id2").setValue(user)
//            myRef.child("Id3").setValue(user)
//            myRef.child("Id4").setValue(user)
//            val intent = Intent(this,MainActivity2::class.java)
//            startActivity(intent)



//        btn2_register = findViewById(R.id.login)
//        btn2_register.setOnClickListener{
//            val intent =Intent(this,MainActivity2::class.java)
//            startActivity(intent)
//        }



    }

    fun checkUser(){
        val username = binding.user.text.toString()
        val password = binding.pw.text.toString()
        val userList = ArrayList<User>()
        userRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val user: User? = postSnapshot.getValue(User::class.java)
                        userList.add(user!!)
                    }

                    var boolean = true
                    for (i in userList.indices){
                        if (username == userList[i].user && password == userList[i].pw){
                            println("Yes")
                            boolean = true

                            if (userList[i].status == "Admin") {
                                val intent = Intent(this@LoginActivity, MainActivity_Option::class.java)
                                startActivity(intent)
//                                Control.clicked = false
                                finish()  //คำสั่งไว้ทำลายหน้านี้ทิ้ง
                            } else { //ถ้าไม่เป็น admin จะทำอันนี้
                                val intent = Intent(this@LoginActivity, PeopleStockActivity::class.java)
                                startActivity(intent)
//                                Control.clicked = false
                                finish() //คำสั่งไว้ทำลายหน้านี้ทิ้ง
                            }

                            break
                        }else{
                            println("No")
                            boolean = false
                        }
                    }

                    if (boolean){
                        Toast.makeText(this@LoginActivity,"ถูกต้อง",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@LoginActivity,"กรอกข้อมูลไม่ถูกต้อง",Toast.LENGTH_SHORT).show()
                    }

                }
                Control.clicked = false
//                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                Control.clicked = false
            }
        })
    }

    fun firebasetest(){
        val query: Query = myRef2.orderByChild("model").equalTo("EB-300UV")
        val machineList2 = ArrayList<Machine>()
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val machine: Machine? = postSnapshot.getValue(Machine::class.java)
                        machineList2.add(machine!!)
                    }
                    println("machine $machineList2")

                    println("sizaMachine ${machineList2.size}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onStop() {
        super.onStop()
//        Control.clicked = false
    }

    override fun onDestroy() {
        super.onDestroy()
        Control.clicked = false
    }

    }

