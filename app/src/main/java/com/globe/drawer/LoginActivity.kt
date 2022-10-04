package com.globe.drawer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

//Set LoginActivity as the Launcher
// Set ActionBarTitle to Login
class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isLoggedIn()){
            launchMainActivity()
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        supportActionBar?.title = "Login"

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (isCredentialsValid()) {
                    saveCredentials()
                    launchMainActivity()
                    finish()
                    //start MainActivity
                } else {
                    Toast.makeText(
                        this@LoginActivity, "Invalid username or password",
                        Toast.LENGTH_SHORT
                    ).show()
                    //TODO show error via Toast
                }
            }
        })
    }

    private fun launchMainActivity() {
        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(mainIntent)
    }

    private fun isCredentialsValid(): Boolean {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        // email + password
        // example: check if email == josesantiago@gmail.com and password = welcome1234
        return email == "josesantiago@gmail.com" && password == "welcome1234"
    }

    private fun getMySharedPreference() : SharedPreferences{
        return getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getMySharedPreference()
        val email = sharedPreferences.getString(Constants.EMAIL,null)
        return email != null
    }


    private fun saveCredentials() {
        val sharedPreferences = getMySharedPreference()

        val email = etEmail.text.toString().trim()

        sharedPreferences
            .edit()
            .putString(Constants.EMAIL, email)
            .apply()
    }
}