package com.agora.cv

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

        lateinit var etUsername: EditText
        lateinit var  etPassword: EditText
        val MIN_PASSWORD_LENGTH = 6
        private val sharedPrefFile = "Agora"

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            viewInitializations()
        }

        fun viewInitializations() {
            etUsername = findViewById(R.id.et_username)
            etPassword = findViewById(R.id.et_password)
        }

        // Checking if the input in form is valid
        fun validateInput(): Boolean {
            if (etUsername.text.toString() == "") {
                etUsername.error = "Please Enter Username"
                return false
            }
            if (etPassword.text.toString() == "") {
                etPassword.error = "Please Enter Password"
                return false
            }

            // checking minimum password Length
            if (etPassword.text.length < MIN_PASSWORD_LENGTH) {
                etPassword.error = "Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters"
                return false
            }

            if (etPassword.text.toString() != "123456") {
                etPassword.error = "Please Enter Valid Password"
                return false
            }

            return true
        }



        // Hook Click Event
        fun performLogin(v: View) {
            if (validateInput()) {
                // Input is valid, here send data to your server
                val username = etUsername!!.text.toString()
                val password = etPassword!!.text.toString()

                val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
                var editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.commit()

                Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Here you can call you API
                // Check this tutorial to call server api through Google Volley Library https://handyopinion.com
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }