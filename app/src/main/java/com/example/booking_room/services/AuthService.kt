package com.example.booking_room.services

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

public class AuthService private constructor() {
    private val auth: FirebaseAuth

    init {
        auth = FirebaseAuth.getInstance()
    }

    private object Holder { val INSTANCE = AuthService() }

    companion object {
        @JvmStatic
        fun getInstance(): AuthService {
            return Holder.INSTANCE
        }
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut() {
        auth.signOut()
    }
    fun ResetPassword(username : String): Task<Void> {
        return auth.sendPasswordResetEmail(username)
    }


}