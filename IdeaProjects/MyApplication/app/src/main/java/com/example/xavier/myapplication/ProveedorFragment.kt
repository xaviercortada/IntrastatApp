package com.example.xavier.myapplication

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.xavier.myapplication.facturas.FacturasFragment
import com.example.xavier.myapplication.model.Proveedor
import com.example.xavier.myapplication.tools.OnFragmentInteractionListener

class ProveedorFragment : Fragment(){
    var proveedor :Proveedor? = null
    var nameView :TextView? = null
    var codView :TextView? = null
    var facturasButton :Button? = null
    var mListener : OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.proveedor_layout, container, false)

        nameView = view.findViewById(R.id.name_proveedor)as TextView
        codView = view.findViewById(R.id.cod_proveedor)as TextView
        facturasButton = view.findViewById(R.id.facturas_proveedor) as Button

        loadArguments(arguments)

        nameView!!.setText(proveedor!!.name)
        codView!!.setText(proveedor!!.codigo)

        facturasButton!!.setOnClickListener { view ->
            val fragment = FacturasFragment.newInstance(proveedor!!)
            mListener!!.changeFragment(fragment, "Facturas")
        }

        return view
    }

    fun loadArguments(bundle: Bundle){
        proveedor = bundle.getSerializable(ARG_ITEM) as Proveedor
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as OnFragmentInteractionListener
    }

    companion object {
        private val ARG_ITEM = "proveedor_item"

        fun newInstance(proveedor :Proveedor) :ProveedorFragment{
            val args :Bundle = Bundle()
            args.putSerializable(ARG_ITEM, proveedor)

            val instance = ProveedorFragment()
            instance.arguments = args

            return instance
        }
    }

}