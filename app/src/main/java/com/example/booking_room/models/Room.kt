package com.example.booking_room.models

import java.io.Serializable

data class Room(
    var name: String ? = null,
    var apartment: String ? = null,
    var numberOfSeats: Int ? = null
) : Serializable {

}