package com.example.xavier.myapplication.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.xavier.myapplication.R

class MaterialAdapter(internal val ctx :Context?, resource :Int, internal val items :ArrayList<Material>?) : ArrayAdapter<Material>(ctx, resource, items) {
    private class ViewHolder{
        var txtCodigo: TextView? = null
        var txtImporte: TextView? = null
        var txtPeso: TextView? = null
        var txtUnidades: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val viewHolder :ViewHolder?
        val view :View?

        val item = items!!.get(position)


        if(convertView == null){
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(ctx)
            view = inflater.inflate(R.layout.material_row, parent, false)
            viewHolder.txtCodigo = view.findViewById(R.id.list_codigo_mat) as TextView
            viewHolder.txtImporte = view.findViewById(R.id.list_importe_mat) as TextView
            viewHolder.txtPeso = view.findViewById(R.id.list_peso_mat) as TextView
            viewHolder.txtUnidades = view.findViewById(R.id.list_unidades_mat) as TextView
            view.tag = viewHolder
        }else{
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }

        viewHolder.txtCodigo!!.setText(item.nomenclature.code + item.nomenclature.description)
        viewHolder.txtImporte!!.setText(item.importe)
        viewHolder.txtPeso!!.setText(item.peso)
        viewHolder.txtUnidades!!.setText(item.unidades)

        return view

    }
}