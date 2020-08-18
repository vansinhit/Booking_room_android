package com.example.booking_room.models

data class Apartment(
    var name: String ? = null,
    var des: String ? = null
) {
    override fun toString(): String {
        return name.toString()
    }
}