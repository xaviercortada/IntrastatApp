package com.example.xavier.myapplication.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.xavier.myapplication.R





class ProveedorAdapter(internal val ctx :Context?, resource :Int, val items :ArrayList<Proveedor>?) : ArrayAdapter<Proveedor>(ctx, resource, items) {
    private var mFilter :Filter? = null
    private class ViewHolder{
        var txtName : TextView? = null
        var txtCodigo: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder :ViewHolder?
        val view :View?

        val item = items!!.get(position)


        if(convertView == null){
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(ctx)
            view = inflater.inflate(R.layout.proveedor_row, parent, false)
            viewHolder.txtName = view.findViewById(R.id.list_name_pro) as TextView
            viewHolder.txtCodigo = view.findViewById(R.id.list_cod_pro) as TextView
            view.tag = viewHolder
        }else{
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }

        if(position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            view?.setBackgroundResource(R.color.BlueOdd);
        }
        else
        {
            // Set the background color for alternate row/item
            view?.setBackgroundResource(R.color.BlueEven);
        }


        viewHolder.txtName!!.setText(item.name)
        viewHolder.txtCodigo!!.setText(item.codigo)

        return view

    }

    override fun getFilter(): Filter {
        if(mFilter == null){
            mFilter = ProveedorFilter()
        }
        return mFilter!!
    }

    inner class ProveedorFilter :Filter {
        private var sourceObjects :ArrayList<Proveedor>? = null

        constructor(){
            sourceObjects = ArrayList<Proveedor>()
            synchronized(this){
                sourceObjects!!.addAll(this@ProveedorAdapter.items as ArrayList<Proveedor>)
            }

        }

        override fun performFiltering(chars: CharSequence?): FilterResults {
            val filterSeq = chars.toString().toLowerCase()
            val result = Filter.FilterResults()
            if (filterSeq != null && filterSeq!!.length > 0) {
                val filter = ArrayList<Proveedor>()

                sourceObjects!!.forEach { item ->
                    // the filtering itself:
                    if (item.name.toLowerCase().contains(filterSeq))
                        filter.add(item)
                }

                result.count = filter.size
                result.values = filter
            } else {
                // add all objects
                synchronized(this) {
                    result.values = sourceObjects
                    result.count = sourceObjects!!.size
                }
            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filtered = results?.values as ArrayList<Proveedor>
            notifyDataSetChanged()
            clear()
            filtered.forEach { item ->
                add(item)
            }
            notifyDataSetInvalidated()
        }

    }
}