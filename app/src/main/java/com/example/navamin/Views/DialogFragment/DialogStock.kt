package com.example.min.DialogFragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.navamin.Model.Stock
import com.example.navamin.R
import com.example.navamin.databinding.DialogStockBinding
import kotlin.contracts.Returns

class DialogStock : DialogFragment(){
    private lateinit var myContext: Context
    lateinit var binding: DialogStockBinding
    private lateinit var langList: ArrayList<Stock>
    private var brand: String? = null
    private var model: String? = null
    private var quantity: String? = null
    private var quantity_enable: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.dialog_stock,container,false)
        initInstance()

        return binding.root
    }
    //
    private fun initInstance(){


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf {
            it.containsKey("brand")
            it.containsKey("model")
            it.containsKey("quantity")
            it.containsKey("quantity_enable")
        }?.apply {
            brand = getString("brand")
            model = getString("model")
            quantity = getString("quantity")
            quantity_enable = getString("quantity_enable")
            binding.brand1.text = brand
            binding.model1.text = model
            binding.quantity1.text = quantity
            binding.quantityEnable1.text = quantity_enable


            setStyle(STYLE_NO_INPUT, android.R.style.Theme)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.decorView?.systemUiVisibility
            //dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation //Animation xml
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
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
