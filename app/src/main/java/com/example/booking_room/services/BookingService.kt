package com.example.booking_room.services

import com.example.booking_room.models.Booking
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BookingService private constructor() {
    private val database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().reference.child("booking")
    }

    private object Holder { val INSTANCE = BookingService() }

    companion object {
        @JvmStatic
        fun getInstance(): BookingService {
            return Holder.INSTANCE
        }
    }

    fun bookingRoom(booking: Booking): Task<Void> {
        // TODO create QR
        return database.child("${booking.apartment}_${booking.roomName}_${booking.date}${booking.fromTime}${booking.toTime}").setValue(booking)
    }
    fun getBooking(): DatabaseReference {
        return database
    }
}