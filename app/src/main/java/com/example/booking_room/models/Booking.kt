package com.example.booking_room.models

data class Booking(
    var userEmail: String ? = null,
    var roomName: String ? = null,
    var apartment: String ? = null,
    var date: String ? = null,
    var fromTime: String ? = null,
    var toTime: String ? = null,
)