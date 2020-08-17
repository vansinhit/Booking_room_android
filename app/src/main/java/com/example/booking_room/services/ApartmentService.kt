package com.example.booking_room.services

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

public class ApartmentService private constructor() {
    private val database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().reference
    }

    private object Holder { val INSTANCE = ApartmentService() }

    companion object {
        @JvmStatic
        fun getInstance(): ApartmentService {
            return Holder.INSTANCE
        }
    }

    fun getApartment(): DatabaseReference {
        return database.child("apartment")
    }
}