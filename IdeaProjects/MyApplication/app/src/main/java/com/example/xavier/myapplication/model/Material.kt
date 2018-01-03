package com.example.xavier.myapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

data class Material(
        val id :Long,
        val codigo: String,
        val importe: String,
        val peso: String,
        val price: Double,
        val unidades :String,
        val vestadistico: String,
        val nomenclature: Nomenclature
) : Serializable {
    class Deserializer: ResponseDeserializable<Array<Material>> {
        override fun deserialize(content: String): Array<Material>? = Gson().fromJson(content, Array<Material>::class.java)
    }
    companion object {
        val COLUMN_ID = "id"
        val TABLE_NAME = "materiales"
        val COLUMN_CODIGO = "codigo"
        val COLUMN_IMPORTE = "importe"
        val COLUMN_PESO = "peso"
        val COLUMN_PRICE = "price"
        val COLUMN_UNIDADES = "unidades"
        val COLUMN_VESTADISTICO = "vestadistico"
    }
}