package com.example.booking_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.booking_room.services.AuthService

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        etEmail = findViewById<EditText>(R.id.et_email)
        etPass = findViewById<EditText>(R.id.et_pass)

        if (AuthService.getInstance().getCurrentUser() != null) {
            goToHome()
        }

        btnLogin.setOnClickListener {
            login()
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun goToHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun login() {
        if (etEmail.text.toString().trim().isEmpty()) {
            etEmail.error = "Please enter email"
            etEmail.requestFocus()
            return
        }

        if (etPass.text.toString().trim().isEmpty()) {
            etPass.error = "Please enter password"
            etPass.requestFocus()
            return
        }

        AuthService.getInstance().login(etEmail.text.toString(), etPass.text.toString()).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                goToHome()
            } else {
                Toast.makeText(baseContext,  task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}