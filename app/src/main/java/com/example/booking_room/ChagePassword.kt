package com.example.booking_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.booking_room.services.AuthService
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.android.synthetic.main.activity_chage_password.*
import kotlinx.android.synthetic.main.activity_register.*

class ChagePassword : AppCompatActivity() {
    private lateinit var etPasswordOld: EditText
    private lateinit var etNewPassword : EditText
    private lateinit var etComfimPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chage_password)

        etPasswordOld = findViewById(R.id.et_pass_old)
        etNewPassword = findViewById(R.id.et_change_pass_new)
        etComfimPassword = findViewById(R.id.et_comfim_pass)
        val btnBack = findViewById<Button>(R.id.btn_back_change)
        val btnChage = findViewById<Button>(R.id.btn_chage)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        btnChage.setOnClickListener{
            chagePassword()
        }

    }

    fun chagePassword() {
        if (etPasswordOld.text.toString().trim().isEmpty()) {
            etPasswordOld.error = "Please enter password"
            etPasswordOld.requestFocus()
            return
        }

        if (etNewPassword.text.toString().trim().isEmpty()) {
            etNewPassword.error = "Please enter password"
            etNewPassword.requestFocus()
            return
        }

        if (etNewPassword.text.toString().trim() != etComfimPassword.text.toString().trim()) {
            etComfimPassword.error = "Password not match"
            etComfimPassword.requestFocus()
            return
        }
        val user = AuthService.getInstance().getCurrentUser() // thông tin của người dùng
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, et_pass_old.text.toString()) // nhận thông tin của người dùng
            user?.reauthenticate(credential)
                .addOnCompleteListener { task -> // nhắc người dùng cung cấp lại thông tin đăng nhập của họ
                    if (task.isSuccessful) {
                        user?.updatePassword(etNewPassword.text.toString())
                            .addOnCompleteListener { task -> // đặt lại mk người dùng
                                Toast.makeText(
                                    this,
                                    "Password successfully changed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                AuthService.getInstance().signOut()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }

                    } else {
                        Toast.makeText(baseContext, task.exception?.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }
}
