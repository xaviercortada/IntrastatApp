package com.example.xavier.myapplication.model

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.xavier.myapplication.R
import org.jetbrains.anko.layoutInflater

class FacturaCursorAdapter(internal val ctx :Context?, internal val cursor :Cursor?, internal val autorequery :Boolean) : CursorAdapter(ctx, cursor, autorequery) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return context!!.layoutInflater.inflate(R.layout.factura_row, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val viewHolder :ViewHolder?

        viewHolder = ViewHolder()
        viewHolder.txtFecha = view!!.findViewById(R.id.list_fecha_fact) as TextView
        viewHolder.txtCodigo = view!!.findViewById(R.id.list_codigo_fact) as TextView
        viewHolder.txtEntrega = view!!.findViewById(R.id.list_entrega_fact) as TextView
        viewHolder.txtTotal = view!!.findViewById(R.id.list_total_fact) as TextView
        viewHolder.txtProveedor = view!!.findViewById(R.id.list_proveedor_fact) as TextView

        viewHolder.txtFecha!!.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(FacturaView.COLUMN_FECHA)))
        viewHolder.txtCodigo!!.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(FacturaView.COLUMN_CODIGO)))
        viewHolder.txtEntrega!!.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(FacturaView.COLUMN_ENTREGA)))
        viewHolder.txtProveedor!!.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(FacturaView.COLUMN_PROVEEDOR)))
        viewHolder.txtTotal!!.setText(cursor!!.getString(cursor!!.getColumnIndexOrThrow(FacturaView.COLUMN_TOTAL)))

        //viewHolder.txtTotal!!.setText(item.total().toString()+ "€")
        if(cursor.position %2 == 1)
        {
            // Set a background color for ListView regular row/item
            view?.setBackgroundResource(R.color.BlueOdd);
        }
        else
        {
            // Set the background color for alternate row/item
            view?.setBackgroundResource(R.color.BlueEven);
        }


    }

    private class ViewHolder{
        var txtFecha : TextView? = null
        var txtCodigo: TextView? = null
        var txtEntrega: TextView? = null
        var txtTotal: TextView? = null
        var txtProveedor: TextView? = null
    }

}