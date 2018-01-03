package com.example.xavier.myapplication

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.xavier.myapplication.tools.RESTSupport
import java.util.logging.Logger

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null

    // UI references.
    private var mEmailView: EditText? = null
    private var mPasswordView: EditText? = null
    var mProgressDlg : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.
        mEmailView = findViewById(R.id.account) as EditText

        mPasswordView = findViewById(R.id.password) as EditText
        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.account || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })


        val mEmailSignInButton = findViewById(R.id.button) as Button
        mEmailSignInButton.setOnClickListener { attemptLogin() }

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView!!.error = getString(R.string.error_invalid_password)
            focusView = mPasswordView
            //cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView!!.error = getString(R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true)
            mAuthTask = UserLoginTask(email, password)

            mProgressDlg = ProgressDialog.show(this, "Intrastat", "Validando", true, false)

            mAuthTask!!.execute(null as Void?)
        }
    }


    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val username: String, private val password: String) : AsyncTask<Void, Void, Boolean>() {
        val Log = Logger.getLogger(MainActivity::class.java.name)


        override fun doInBackground(vararg params: Void): Boolean? {
            val mApp = applicationContext as AppIntrastat
            val support = RESTSupport(mApp)


            var result = false

            try{
                support.Login(username, password)
                result = true
            }catch(e :Exception){
                android.util.Log.e("Cheking authoritations",e.message)
                result = false
            }
            return result
        }

        override fun onPostExecute(success: Boolean?) {
            mProgressDlg!!.cancel()

            if (success!!) {
                val mainPage = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(mainPage)
                finish()
            } else {
                mPasswordView!!.error = getString(R.string.error_incorrect_password)
                mPasswordView!!.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null

        }
    }

}

