package com.example.navamin.Views.DialogFragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.navamin.Model.Borrow
import com.example.navamin.R
import com.example.navamin.databinding.DialogIconBinding

class IconDialog : DialogFragment(){
    private lateinit var myContext: Context
    lateinit var binding: DialogIconBinding
    private var langList = ArrayList<Borrow>()
    private var model: String? = null
    private var serialnumber: String? = null
    private var borrowName: String? = null
    private var date_borrow: String? = null
    private var date_return: String? = null
    private var objective: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_icon,container,false)
        initInstance()

        return binding.root
    }
//
    private fun initInstance(){


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey("model")
            it.containsKey("serialnumber")
            it.containsKey("borrowName")
            it.containsKey("date_borrow")
            it.containsKey("date_return")
            it.containsKey("objective")
        }?.apply {
            model = getString("model")
            serialnumber = getString("serialnumber")
            borrowName = getString("borrowName")
            date_borrow = getString("date_borrow")
            date_return = getString("date_return")
            objective = getString("objective")
            binding.serialNumber.text = model
            binding.serialnumber2.text = serialnumber
            binding.name.text = borrowName
            binding.appCompatTextView16.text = date_borrow
            binding.objective1.text = date_return
            binding.objective.text = objective


            setStyle(STYLE_NO_INPUT, android.R.style.Theme)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.decorView?.systemUiVisibility
            //dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation //Animation xml
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
            setStyle(STYLE_NO_INPUT, android.R.style.Theme)
        }
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