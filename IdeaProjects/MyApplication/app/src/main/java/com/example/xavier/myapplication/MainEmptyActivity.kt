package com.example.xavier.myapplication

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.xavier.myapplication.db.DatabaseHelper
import com.example.xavier.myapplication.tools.RESTSupport
import org.json.JSONArray

class MainEmptyActivity : AppCompatActivity() {
    var mApp : AppIntrastat? = null
    var activityIntent: Intent? = null
    var mProgressDlg :ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.empty_layout)

        mApp = applicationContext as AppIntrastat

        val task = RESTTask()

        mProgressDlg = ProgressDialog.show(this, "Intrastat", "Cargando aplicación", true, false)

        task.execute()

    }

    inner class RESTTask: AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean {
            val support = RESTSupport(mApp)
            var result = false

            try{
                if (mApp!!.getAuthId() != null) {
                    val array : JSONArray? = support.GetProveedores()
                    result = true
                }
            }catch(e :Exception){
                Log.e("Cheking authoritations",e.message)
            }
            return result
        }

        override fun onPostExecute(success: Boolean?) {
            mProgressDlg!!.cancel()

            if(success!!){
                activityIntent = Intent(this@MainEmptyActivity, MainActivity::class.java)
            }else{
                mApp!!.setAuthToken("")
                mApp!!.setAuthId("")
                activityIntent = Intent(this@MainEmptyActivity, LoginActivity::class.java)
            }
            startActivity(activityIntent)
            finish()
        }

    }
}
