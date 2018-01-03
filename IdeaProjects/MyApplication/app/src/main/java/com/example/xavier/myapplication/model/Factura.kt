package com.example.xavier.myapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

data class Factura(
        val id :Long,
        val codigo: String,
        val entrega: String,
        val fecha: String,
        val flujo: String,
        val proveedor: Proveedor,
        val materiales :ArrayList<Material>
) : Serializable{
    class Deserializer: ResponseDeserializable<Array<Factura>> {
        override fun deserialize(content: String): Array<Factura>? = Gson().fromJson(content, Array<Factura>::class.java)
    }

    fun total():Double{
        var total :Double = 0.0

        materiales.forEach { item ->
            total = total.plus(item.price)
        }

        return total
    }

    companion object {
        val COLUMN_ID = "_id"
        val TABLE_NAME = "facturas"
        val COLUMN_CODIGO = "codigo"
        val COLUMN_ENTREGA = "entrega"
        val COLUMN_FECHA = "fecha"
        val COLUMN_FLUJO = "flujo"
        val COLUMN_MATERIALES = "materiales"
    }
}