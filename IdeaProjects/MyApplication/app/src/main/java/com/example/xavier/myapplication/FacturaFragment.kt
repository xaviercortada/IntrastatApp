package com.example.xavier.myapplication

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.xavier.myapplication.model.Factura
import com.example.xavier.myapplication.model.Material
import com.example.xavier.myapplication.model.MaterialAdapter
import com.example.xavier.myapplication.tools.OnFragmentInteractionListener

class FacturaFragment : Fragment() {

    private var mCtx: Context? = null
    private var mApp: AppIntrastat? = null

    private var mCodigoView :TextView?=null
    private var mFechaView :TextView?=null

    private var mListView :ListView?=null

    private var mFactura: Factura?=null
    private var mMateriales :ArrayList<Material> = ArrayList<Material>()

    var mListener : OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.factura_layout, container, false)

        mListView = view.findViewById(R.id.materiales_list) as ListView

        mCodigoView = view.findViewById(R.id.cod_factura) as TextView
        mFechaView = view.findViewById(R.id.fecha_factura) as TextView

        setupListView()

        mCtx = activity

        mApp = activity.applicationContext as AppIntrastat

        loadArguments(arguments)

        mCodigoView!!.text = mFactura!!.codigo
        mFechaView!!.text = mFactura!!.fecha


        return view
    }

    private fun setupListView() {
        addHeaderFooterView()


    }

    private fun addHeaderFooterView() {
    }


    fun loadArguments(bundle: Bundle){
        mFactura = bundle.getSerializable(ARG_ITEM) as Factura
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as OnFragmentInteractionListener
    }

    override fun onStart() {
        super.onStart()

        fetchItems()

    }

    private fun fetchItems() {

        //Add to ArrayList
        mFactura!!.materiales?.forEach { item ->
            mMateriales!!.add(item)
        }
        var mAdapter = MaterialAdapter(mCtx, R.layout.material_row, mMateriales)
        //var mAdapter = Factura2Adapter(mCtx!!, this)

        mListView!!.adapter = mAdapter


    }


    companion object {
        private val ARG_ITEM = "factura_item"

        fun newInstance(factura : Factura) :FacturaFragment{
            val args :Bundle = Bundle()
            args.putSerializable(ARG_ITEM, factura)

            val instance = FacturaFragment()
            instance.arguments = args

            return instance
        }
    }



}
