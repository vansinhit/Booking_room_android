package com.example.booking_room.services

import com.example.booking_room.models.Room
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RoomService private constructor() {
    private val database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().reference.child("room")
    }

    private object Holder { val INSTANCE = RoomService() }

    companion object {
        @JvmStatic
        fun getInstance(): RoomService {
            return Holder.INSTANCE
        }
    }

    fun getRoom(): DatabaseReference {
        return database
    }

    fun createRoom(room: Room): Task<Void> {
        return database.child("${room.apartment}_${room.name}").setValue(room)
    }

    fun isExisted(id: String): DatabaseReference {
        return database.child(id)
    }

    fun remoteRoom(id: String): Task<Void> {
        return database.child(id).removeValue()
    }
}