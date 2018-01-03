package com.example.xavier.myapplication.tools

import android.util.Log
import com.example.xavier.myapplication.AppIntrastat
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class RESTSupport {
    var mCtx : AppIntrastat? = null

    constructor(appCtx : AppIntrastat?){
        mCtx = appCtx
    }



    fun Login(username :String?, password :String?): Boolean {
        var url : URL? = null
        try {
            val loginObj = JSONObject()
            loginObj.put("username", username)
            loginObj.put("password", password)
            if(android.os.Build.MODEL.contains("SDK")) {
                url = URL("http://10.0.2.2:8080/appintrastat/rest/accounts/signin")
            }else {
                //url = URL("http://81.184.32.42:30080/appintrastat/rest/accounts/signin")
                url = URL("http://appintrastat.alkaid.cat:30080/appintrastat/rest/accounts/signin")

            }
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.setRequestProperty("Accept", "application/json; charset=UTF-8")
            conn.doOutput = true
            conn.requestMethod = "POST"

            conn.connect()
            val ow = DataOutputStream(conn.getOutputStream())
            ow.writeBytes(loginObj.toString())
            ow.flush()
            ow.close()

            val reader = conn.getInputStream()
            val allText = reader.bufferedReader().use(BufferedReader::readText)
            val response = JSONObject(allText)
            //response data
            Log.w("login data received",allText)
            mCtx!!.setAuthId(response.getString("authId"))
            mCtx!!.setAuthToken(response.getString("authToken"))
            mCtx!!.setCompanyId(response.getString("companyId"))


        } catch (e: Exception) {
            Log.e("Login error", e.message)
            throw e
        }

        return true
    }

    fun GetProveedores() : JSONArray? {
        var url : URL? = null
        var items :JSONArray? = null
        try {
            if(android.os.Build.MODEL.contains("SDK")) {
                url = URL("http://10.0.2.2:8080/appintrastat/rest/proveedores")
            }else {
                url = URL("http://appintrastat.alkaid.cat:30080/appintrastat/rest/proveedores")
            }
            val conn = url.openConnection() as HttpURLConnection
            //conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.setRequestProperty("Accept", "application/json; charset=UTF-8")
            conn.setRequestProperty("auth-id", mCtx!!.getAuthId())
            conn.setRequestProperty("Authorization", "Bearer "+mCtx!!.getAuthToken())
            conn.doOutput = false
            conn.requestMethod = "GET"

            conn.connect()

            val reader = conn.getInputStream()
            val allText = reader.bufferedReader().use(BufferedReader::readText)
            try {
                items = JSONArray(allText)
            }catch (e :ClassCastException){
                items = null
            }catch( e:Exception){
                throw e
            }
            //response data


        } catch (e: Exception) {
            Log.e("Receiving proveedores", e.message)
            throw e
        }

        return items

    }


    fun GetFacturasByProveedor(idProveedor :Int) : JSONArray? {
        var url : URL? = null
        var items :JSONArray? = null
        try {
            if(android.os.Build.MODEL.contains("SDK")) {
                url = URL(String.format("http://10.0.2.2:8080/appintrastat/rest/facturas/proveedor/%d?flujo=X&present=X&start=0&max=100", idProveedor))
            }else {
                url = URL("http://appintrastat.alkaid.cat:30080/appintrastat/rest/facturas/proveedor/66?flujo=X&present=X")
            }
            val conn = url.openConnection() as HttpURLConnection
            //conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn.setRequestProperty("Accept", "application/json; charset=UTF-8")
            conn.setRequestProperty("auth-id", mCtx!!.getAuthId())
            conn.setRequestProperty("Authorization", "Bearer "+mCtx!!.getAuthToken())
            conn.setRequestProperty("company-id", "191")

            conn.doOutput = false
            conn.requestMethod = "GET"

            conn.connect()

            val reader = conn.getInputStream()
            val allText = reader.bufferedReader().use(BufferedReader::readText)
            try {
                items = JSONArray(allText)
            }catch (e :ClassCastException){
                items = null
            }catch( e:Exception){
                throw e
            }
            //response data


        } catch (e: Exception) {
            Log.e("Receiving facturas", e.message)
            throw e
        }

        return items

    }
}