package com.example.xavier.myapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

data class Proveedor(
        val id :Int = 0,
        val codigo :String = "",
        val documento :String = "",
        val name: String = ""
) :Serializable
{
    class Deserializer: ResponseDeserializable<Array<Proveedor>> {
        override fun deserialize(content: String): Array<Proveedor>? = Gson().fromJson(content, Array<Proveedor>::class.java)
    }
    companion object {
        val COLUMN_ID = "id"
        val TABLE_NAME = "proveedores"
        val COLUMN_CODIGO = "codigo"
        val COLUMN_DOCUMENTO = "documento"
        val COLUMN_NAME = "name"
    }

}

