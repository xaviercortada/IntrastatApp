package com.example.xavier.myapplication.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.xavier.myapplication.R

class FacturaAdapter(internal val ctx :Context?, resource :Int, internal val items :List<Factura>) : ArrayAdapter<Factura>(ctx, resource, items) {
    private class ViewHolder{
        var txtFecha : TextView? = null
        var txtCodigo: TextView? = null
        var txtEntrega: TextView? = null
        var txtTotal: TextView? = null
        var txtProveedor: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder :ViewHolder?
        val view :View?

        val item = items!!.get(position)


        if(convertView == null){
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(ctx)
            view = inflater.inflate(R.layout.factura_row, parent, false)
            viewHolder.txtFecha = view.findViewById(R.id.list_fecha_fact) as TextView
            viewHolder.txtCodigo = view.findViewById(R.id.list_codigo_fact) as TextView
            viewHolder.txtEntrega = view.findViewById(R.id.list_entrega_fact) as TextView
            viewHolder.txtTotal = view.findViewById(R.id.list_total_fact) as TextView
            viewHolder.txtProveedor = view.findViewById(R.id.list_proveedor_fact) as TextView
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

        viewHolder.txtProveedor!!.setText(item.proveedor.name)
        viewHolder.txtFecha!!.setText(item.fecha)
        viewHolder.txtCodigo!!.setText(item.codigo)
        viewHolder.txtEntrega!!.setText(item.entrega)
        viewHolder.txtTotal!!.setText(item.total().toString()+ "€")

        return view

    }
}