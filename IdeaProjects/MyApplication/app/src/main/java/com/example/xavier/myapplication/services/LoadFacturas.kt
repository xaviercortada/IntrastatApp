package com.example.xavier.myapplication.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.xavier.myapplication.model.Proveedor
import com.github.kittinunf.fuel.httpPut


class LoadFacturas: BroadcastReceiver() {
    var url : String? = null
    var Proveedores = ArrayList<Proveedor>()

    override fun onReceive(ctx: Context?, intent: Intent?) {

        if (android.os.Build.MODEL.contains("SDK")) {
            url = "http://10.0.2.2:8080/appintrastat/rest/proveedores"
        } else {
            url = "http://appintrastat.alkaid.cat:30080/appintrastat/rest/proveedores"
        }

        url!!.httpPut().responseObject(Proveedor.Deserializer()) { request, response, result ->
            val (people, err) = result

            //Add to ArrayList
            people?.forEach { item ->
                Proveedores.add(item)
            }

        }


    }





}