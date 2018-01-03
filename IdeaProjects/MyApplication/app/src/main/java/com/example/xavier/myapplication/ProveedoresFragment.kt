package com.example.xavier.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.example.xavier.myapplication.model.Proveedor
import com.example.xavier.myapplication.model.ProveedorAdapter
import com.example.xavier.myapplication.tools.OnFragmentInteractionListener
import com.example.xavier.myapplication.tools.RESTSupport
import com.github.kittinunf.fuel.httpGet
import org.json.JSONArray
import java.util.logging.Logger



class ProveedoresFragment() : Fragment(), SearchView.OnQueryTextListener {
    var mListView : ListView? = null
    var mProveedores : ArrayList<Proveedor>? = null
    var mApp :AppIntrastat? = null
    var mProgressDlg :ProgressDialog? = null
    var mCtx :Context? = null
    var mObjs :JSONArray? = null
    var mAdapter :ProveedorAdapter? = null
    var mListener :OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.proveedores_layout, container, false)

        mListView = view.findViewById(R.id.proveedores_list) as ListView
        mListView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val proveedor = parent!!.getItemAtPosition(position) as Proveedor
            Toast.makeText(context, proveedor.name, 1000).show()
            val fragment = ProveedorFragment.newInstance(proveedor)
            mListener!!.changeFragment(fragment, "Proveedor")
        }

        mCtx = activity

        mProveedores = ArrayList<Proveedor>()

        mApp = activity.applicationContext as AppIntrastat

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.proveedores, menu)

        // Associate searchable configuration with the SearchView
        //val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var menuItem = menu!!.findItem(R.id.menu_item_proveedores_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "enter Text"
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(mAdapter != null){
            mAdapter!!.filter.filter(newText)
        }
        return true
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as OnFragmentInteractionListener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onStart() {
        super.onStart()
        /*
        val titleText = TextView(this)
        titleText.text = "Proveedores"
        mListView!!.addHeaderView(titleText)
        */


        fetchItems()

    }


    private fun fetchItems() {

        //val task = FetchItemsTask()

        mProgressDlg = ProgressDialog.show(mCtx, "Intrastat", "Recuperando proveedores", true, false)

        val url = mApp!!.getURLProveedores()

        url!!.httpGet().header(mApp!!.getHttpHeaders()).responseObject(Proveedor.Deserializer()) { request, response, result ->
            val (items, err) = result

            //Add to ArrayList
            items?.forEach { item ->
                mProveedores!!.add(item)
            }
            mAdapter = ProveedorAdapter(mCtx, R.layout.proveedor_row, mProveedores)

            mListView!!.adapter = mAdapter


        }
        mProgressDlg!!.cancel()

        //task.execute()
    }

    inner class FetchItemsTask :AsyncTask<Void, Void, Boolean>() {
        val Log = Logger.getLogger(ProveedoresFragment::class.java.name)
        var items :JSONArray? = null

        override fun doInBackground(vararg params: Void): Boolean? {
            val support = RESTSupport(mApp)
            try {
                items = support.GetProveedores()
            }catch (e :Exception){
                return false
            }
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            mProgressDlg!!.cancel()

            if (success!!) {
                for (i in 0..(items!!.length() -1)){
                    val item = items!!.getJSONObject(i)
                    val p = Proveedor(item.getInt("id"),
                            item.getString("codigo"),
                            item.getString("name"))

                    mProveedores!!.add(p)
                }
                var mAdapter = ProveedorAdapter(mCtx, R.layout.proveedor_row, mProveedores)

                mListView!!.adapter = mAdapter
            }
        }

        override fun onCancelled() {

        }
    }

}
