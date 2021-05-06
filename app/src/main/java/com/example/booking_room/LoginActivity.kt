package com.example.booking_room

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.booking_room.services.AuthService
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_reset_password.view.*


class LoginActivity : AppCompatActivity() {
    lateinit var etEmail: EditText
    lateinit var etPass: EditText
    lateinit var username: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val bottomSheetDialog = BottomSheetDialog(this)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        etEmail = findViewById<EditText>(R.id.et_email)
        etPass = findViewById<EditText>(R.id.et_pass)

        // Resset password
        et_forgot_password.setOnClickListener {
            val builder = AlertDialog.Builder(this) // tạo ra 1 giá trị  gọi là trình tạo
            val view = layoutInflater.inflate(R.layout.activity_reset_password,null) // gọi đến view của trang đó
            username = view.findViewById<EditText>(R.id.et_txt_email)
            builder.setView(view) // đặt chế độ xem bằng dialog
            view.btn_sent_mail.setOnClickListener{
                forgotPassword(username) // truyền usernaem dưới dạng tham số. Để ta có thể thực hiện
            }
            view.btn_reset_cancel.setOnClickListener{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            builder.show()
        }

        //

        if (AuthService.getInstance().getCurrentUser() != null) {
            goToHome()
        }

        btnLogin.setOnClickListener {
            login()
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
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

        AuthService.getInstance().login(etEmail.text.toString(), etPass.text.toString()).addOnCompleteListener(this) { task -> //
            if (task.isSuccessful) {
                goToHome()
            } else {
                Toast.makeText(baseContext,  task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun forgotPassword(username : EditText){
        if (username.text.toString().isEmpty()) {
            username.error = "Please enter Email"
            username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()) {
            return
        }
        AuthService.getInstance().ResetPassword(username.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Sent Email.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
}
}