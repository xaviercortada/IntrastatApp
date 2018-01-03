package com.example.xavier.myapplication.facturas

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListView
import com.example.xavier.myapplication.AppIntrastat
import com.example.xavier.myapplication.FacturaFragment
import com.example.xavier.myapplication.R
import com.example.xavier.myapplication.database
import com.example.xavier.myapplication.model.Factura
import com.example.xavier.myapplication.model.FacturaAdapter
import com.example.xavier.myapplication.model.FacturaCursorAdapter
import com.example.xavier.myapplication.model.Proveedor
import com.example.xavier.myapplication.services.FacturaTabService
import com.example.xavier.myapplication.tools.OnFragmentInteractionListener

class FacturaTab3 : Fragment(), FacturaTabService {
    var mListView : ListView? = null
    var mFacturas : ArrayList<Factura> = ArrayList()
    var mProveedor : Proveedor? = null
    var mApp : AppIntrastat? = null
    var mCtx : Context? = null
    var mListener : OnFragmentInteractionListener? = null
    var mAdapter : CursorAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.factura_tab1, container, false)


        mListView = view.findViewById(R.id.facturas_list) as ListView

        setupListView()

        mCtx = activity

        mApp = activity.applicationContext as AppIntrastat

        loadArguments(arguments)

        return view
    }

    private fun setupListView() {
        //addHeaderFooterView()

        mListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val factura = mFacturas!!.get(id.toInt())
            val fragment = FacturaFragment.newInstance(factura)
            mListener!!.changeFragment(fragment, "Factura")
        }


    }

    fun loadArguments(bundle: Bundle){
        mProveedor = bundle.getSerializable(FacturasFragment.ARG_ITEM) as Proveedor
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        FacturasFragment.addTab(this)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onStart() {
        super.onStart()

        var db = context.database.writableDatabase
        var query = db.rawQuery("SELECT * FROM view_facturas WHERE flujo = 'E'", null)

        mAdapter = FacturaCursorAdapter(activity, query, true)
        mListView!!.adapter = mAdapter
        //mFacturaService?.addAdapter(adapter)
//fetchItems()

    }


    private fun fetchItems() {
        var facturas = ArrayList<Factura>()

        FacturasFragment.mFacturas.forEach { item->
            if(item.flujo == "E") {
                facturas.add(item)
            }
        }
        var mAdapter = FacturaAdapter(activity, R.layout.factura_row, facturas)
        mListView!!.adapter = mAdapter

        mListView!!.deferNotifyDataSetChanged()

    }


    override val Adapterr: CursorAdapter?
        get() = mAdapter

}