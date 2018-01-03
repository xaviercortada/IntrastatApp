package com.example.xavier.myapplication.facturas

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTabHost
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.xavier.myapplication.AppIntrastat
import com.example.xavier.myapplication.R
import com.example.xavier.myapplication.database
import com.example.xavier.myapplication.model.Factura
import com.example.xavier.myapplication.model.FacturaAdapter
import com.example.xavier.myapplication.model.FacturaView
import com.example.xavier.myapplication.model.Proveedor
import com.example.xavier.myapplication.services.FacturaTabService
import com.google.gson.GsonBuilder
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.json.JSONArray



class FacturasFragment :Fragment(), SearchView.OnQueryTextListener {
    var mTabHost :FragmentTabHost? = null
    var mProveedor : Proveedor? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.facturas_layout, container, false)

        mTabHost = view.findViewById(android.R.id.tabhost) as FragmentTabHost


        mTabHost?.setup(this.context, childFragmentManager, android.R.id.tabcontent)
        mTabHost?.addTab(mTabHost?.newTabSpec("tab1")!!.setIndicator("Todas"),
                FacturaTab1::class.java, arguments)
        mTabHost?.addTab(mTabHost?.newTabSpec("tab2")!!.setIndicator("Introducción"),
                FacturaTab2::class.java, arguments)
        mTabHost?.addTab(mTabHost?.newTabSpec("tab3")!!.setIndicator("Expedición"),
                FacturaTab3::class.java, arguments)


        context.database.use {
            delete(FacturaView.TABLE_NAME)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.facturas, menu)

        // Associate searchable configuration with the SearchView
        //val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var menuItem = menu!!.findItem(R.id.menu_item_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "enter Text"
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        fetchItems()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mTabHost = null
    }

    fun loadArguments(bundle: Bundle){
        mProveedor = bundle.getSerializable(FacturasFragment.ARG_ITEM) as Proveedor
    }

    private fun fetchItems() {

        val url = AppIntrastat.instance?.getURLFacturasByProveedor(mProveedor!!.id)

        var jsObjRequest = object : JsonArrayRequest(Request.Method.GET, url, null,
                Response.Listener<JSONArray>() { response ->
                    var gson = GsonBuilder().create()

                    mFacturas = gson.fromJson(response.toString(), Array<Factura>::class.java).toList()
                    var mAdapter = FacturaAdapter(activity, R.layout.factura_row, mFacturas)

                    context.database.use{
                        delete(FacturaView.TABLE_NAME)

                        mFacturas.forEach { item ->
                                var total =  0.0
                                item.materiales.forEach { item ->
                                    total = total + item.price
                                }
                                insert(FacturaView.TABLE_NAME,
                                        FacturaView.COLUMN_CODIGO to item.codigo,
                                        FacturaView.COLUMN_ENTREGA to item.entrega,
                                        FacturaView.COLUMN_FLUJO to item.flujo,
                                        FacturaView.COLUMN_FECHA to item.fecha,
                                        FacturaView.COLUMN_PROVEEDOR to item.proveedor.name,
                                        FacturaView.COLUMN_TOTAL to total
                                )
                            }

                    }

                    mTabs.forEach { item ->
                        item.Adapterr?.notifyDataSetChanged()
                        item.Adapterr?.notifyDataSetInvalidated()
                    }


                }, Response.ErrorListener { error ->
            Log.e("", error.message)

        }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                return AppIntrastat.instance?.getHttpHeaders()!!
            }
        }

        AppIntrastat.instance?.requestQueue?.add(jsObjRequest)

    }

    companion object {
        val ARG_ITEM = "proveedor_item"
        var mFacturas : List<Factura> = ArrayList()
        var mTabs :ArrayList<FacturaTabService> = ArrayList<FacturaTabService>()


        fun newInstance(proveedor :Proveedor) : FacturasFragment {
            val args :Bundle = Bundle()
            args.putSerializable(ARG_ITEM, proveedor)

            val instance = FacturasFragment()
            instance.arguments = args
            instance.mProveedor = proveedor

            mTabs.clear()
            return instance
        }

        fun addTab(tab :FacturaTabService){
            mTabs.add(tab)
        }
    }

}
