package com.example.booking_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.booking_room.services.AuthService

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var etRePass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<Button>(R.id.btn_back)
        val btnSignUp = findViewById<Button>(R.id.btn_sign_up)

        etEmail = findViewById<EditText>(R.id.et_register_email)
        etPass = findViewById<EditText>(R.id.et_register_pass)
        etRePass = findViewById<EditText>(R.id.et_register_re_pass)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        btnSignUp.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
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

        if (etPass.text.toString().trim() != etRePass.text.toString().trim()) {
            etRePass.error = "Password not match"
            etRePass.requestFocus()
            return
        }

        AuthService.getInstance().signUp(etEmail.text.toString().trim(), etPass.text.toString().trim()).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, "SignUp Successfully", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(baseContext, task.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}