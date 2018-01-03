package com.example.xavier.myapplication.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.Serializable

data class Nomenclature(
        val id :Long,
        val code: String,
        val codeCN8: String,
        val description: String,
        val section: String,
        val sunit :String
) : Serializable {
    class Deserializer: ResponseDeserializable<Array<Nomenclature>> {
        override fun deserialize(content: String): Array<Nomenclature>? = Gson().fromJson(content, Array<Nomenclature>::class.java)
    }
    companion object {
        val COLUMN_ID = "id"
        val TABLE_NAME = "nomenclatures"
        val COLUMN_CODE = "code"
        val COLUMN_CODECN8 = "codeCN8"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_SECTION = "section"
        val COLUMN_SUNIT = "sunit"
    }
}