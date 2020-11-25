package com.example.navamin.Views.DialogFragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.navamin.Model.Borrow
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.Views.Activity.Return
import com.example.navamin.databinding.DialogIconRepayBinding

class IconDialogRepay(private val items: ArrayList<Borrow>, private val aReturn: Return) : DialogFragment() {
    private lateinit var myContext: Context
    lateinit var buttonRepay: ButtonRepay
    //private val iconDialogRepay = IconDialog()
    lateinit var binding: DialogIconRepayBinding
    private var langList = ArrayList<Borrow>()
    private var id: String? = null
    private var machine_id: String? = null
    private var model: String? = null
    private var serialnumber: String? = null
    private var borrowName: String? = null
    private var date_borrow: String? = null
    private var date_return: String? = null
    private var objective: String? = null
    private var status: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_icon_repay,container,false)
        initInstance()

        return binding.root
    }

    private fun initInstance(){

        binding.buttonrepay.setOnClickListener {
            dismiss()
            val fm = aReturn.supportFragmentManager
            buttonRepay = ButtonRepay(aReturn)
            val bundle = Bundle()
            bundle.putString("id",id.toString())
            bundle.putString("machine_id",machine_id.toString())
            bundle.putString("model",model.toString())
            bundle.putString("serialnumber",serialnumber.toString())
            bundle.putString("borrowName",borrowName.toString())
            bundle.putString("date_borrow",date_borrow.toString())
            bundle.putString("date_return",date_return.toString())
            bundle.putString("objective",objective.toString())
            bundle.putString("status",status.toString())
            buttonRepay.arguments = bundle
            buttonRepay.show(fm,"member_login")
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey("id")
            it.containsKey("machine_id")
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
            model = getString("model")
            serialnumber = getString("serialnumber")
            borrowName = getString("borrowName")
            date_borrow = getString("date_borrow")
            date_return = getString("date_return")
            objective = getString("objective")
            status = getString("status")
            binding.serialNumber.text = model
            binding.serialnumberTxt2.text = serialnumber
            binding.serialnumber2.text = borrowName
            binding.name.text = date_borrow
            binding.appCompatTextView16.text = date_return
            binding.objective.text = objective
//
//            println("stock is $stock")
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