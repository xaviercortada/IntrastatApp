package com.example.xavier.myapplication

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.xavier.myapplication.db.DatabaseHelper

val Context.database: DatabaseHelper
    get() = DatabaseHelper.Instance(applicationContext)

class AppIntrastat : Application() {
    private var mAuthId: String? = null
    private var mAuthToken: String? = null
    private var mCompanyId: String? = null
    private var mPreferences :SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        mPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mAuthId = mPreferences!!.getString("authId",null)
        mAuthToken = mPreferences!!.getString("authToken", null)
        mCompanyId = mPreferences!!.getString("companyId", null)

    }

    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun getAuthId():String? {
        return mAuthId
    }

    fun getAuthToken():String? {
        return mAuthToken
    }

    fun getCompanyId():String? {
        return mCompanyId
    }

    fun setAuthToken(value :String){
        mAuthToken = value
        mPreferences!!.edit().putString("authToken", value).apply()
    }

    fun setAuthId(value :String){
        mAuthId = value
        mPreferences!!.edit().putString("authId", value).apply()
    }

    fun setCompanyId(value :String){
        mCompanyId = value
        mPreferences!!.edit().putString("companyId", value).apply()
    }

    fun getHttpHeaders() : Map<String, String> {
        val parameters = HashMap<String, String>()

        parameters.put("Accept", "application/json; charset=UTF-8")
        parameters.put("auth-id", getAuthId()!!)
        parameters.put("Authorization", "Bearer "+getAuthToken())
        parameters.put("company-id", "191")

        return parameters

    }

    fun getURLProveedores() :String{

        if (android.os.Build.MODEL.contains("SDK")) {
            return "http://10.0.2.2:8080/appintrastat/rest/proveedores"
        } else {
            return "http://appintrastat.alkaid.cat:30080/appintrastat/rest/proveedores"
        }
    }

    fun getURLFacturasByProveedor(idProveedor :Int) : String {
        if (android.os.Build.MODEL.contains("SDK")) {
            return String.format("http://10.0.2.2:8080/appintrastat/rest/facturas/proveedor/%d?flujo=X&present=X&start=0&max=100", idProveedor)
        } else {
            return String.format("http://appintrastat.alkaid.cat:30080/appintrastat/rest/facturas/proveedor/%d?flujo=X&present=X&start=0&max=100", idProveedor)
        }

    }

    companion object {
        @get:Synchronized var instance: AppIntrastat? = null
            private set
    }

}