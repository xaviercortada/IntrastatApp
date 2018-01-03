package com.example.xavier.myapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

data class FacturaView(
        val id :Long,
        val codigo: String,
        val entrega: String,
        val fecha: String,
        val flujo: String,
        val proveedor: String,
        val total :String
) : Serializable{
    class Deserializer: ResponseDeserializable<Array<FacturaView>> {
        override fun deserialize(content: String): Array<FacturaView>? = Gson().fromJson(content, Array<FacturaView>::class.java)
    }

    companion object {
        val COLUMN_ID = "_id"
        val TABLE_NAME = "view_facturas"
        val COLUMN_CODIGO = "codigo"
        val COLUMN_ENTREGA = "entrega"
        val COLUMN_FECHA = "fecha"
        val COLUMN_FLUJO = "flujo"
        val COLUMN_PROVEEDOR = "proveedor"
        val COLUMN_TOTAL = "total"
    }
}