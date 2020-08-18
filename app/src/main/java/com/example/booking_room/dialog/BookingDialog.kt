package com.example.booking_room.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.booking_room.R
import com.example.booking_room.models.Booking
import com.example.booking_room.models.Room
import com.example.booking_room.services.AuthService
import com.example.booking_room.services.BookingService
import java.util.*

class BookingDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_booking, container, false)

        val etEmail: EditText = view.findViewById(R.id.et_booking_room_email)
        val etRoomName: EditText = view.findViewById(R.id.et_booking_room_name)
        val etRoomApartment: EditText = view.findViewById(R.id.et_booking_room_apartment)
        val etBookingDate: EditText = view.findViewById(R.id.et_booking_date)
        val etBookingTimeFrom: EditText = view.findViewById(R.id.et_booking_time_from)
        val etBookingTimeTo: EditText = view.findViewById(R.id.et_booking_time_to)
        val btnBooking: Button = view.findViewById(R.id.btn_booking_room_verify)
        val btnCancel: Button = view.findViewById(R.id.btn_booking_room_cancel)

        var date = ""
        var fromTime = ""
        var toTime = ""

        if (arguments?.getSerializable("room") is Room) {
            val room = arguments?.getSerializable("room") as Room

            etRoomName.setText(room.name)
            etRoomApartment.setText(room.apartment)
            etEmail.setText(AuthService.getInstance().getCurrentUser()?.email)
        }

        val calendar = Calendar.getInstance()
        var y = calendar.get(Calendar.YEAR)
        var m = calendar.get(Calendar.MONTH)
        var d = calendar.get(Calendar.DAY_OF_MONTH)

        etBookingDate.setOnClickListener {
            DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                etBookingDate.setText("${day}-${month + 1}-${year}")
                date = "${day}${month + 1}${year}"
                y = year
                m = month
                d = day
            }, y, m, d).show()
        }

        var fromHour = 0
        var fromMinute = 0
        var toHour = 0
        var toMinute = 0

        etBookingTimeFrom.setOnClickListener {
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                etBookingTimeFrom.setText("$hour:$minute")
                fromTime = "$hour$minute"
                fromHour = hour
                fromMinute = minute
            }, fromHour, fromMinute, true).show()
        }

        etBookingTimeTo.setOnClickListener {
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                etBookingTimeTo.setText("$hour:$minute")
                toTime = "$hour$minute"
                toHour = hour
                toMinute = minute
            }, toHour, toMinute, true).show()
        }

        btnCancel.setOnClickListener {
            this.dismiss()
        }

        btnBooking.setOnClickListener {
            if (date.trim().isNotEmpty() && fromTime.trim().isNotEmpty() && toTime.trim().isNotEmpty()) {
                val booking = Booking(
                    etEmail.text.toString(),
                    etRoomName.text.toString(),
                    etRoomApartment.text.toString(),
                    etBookingDate.text.toString(),
                    etBookingTimeFrom.text.toString(),
                    etBookingTimeTo.text.toString()
                )

                BookingService.getInstance().bookingRoom(booking).addOnCompleteListener {
                    Toast.makeText(requireContext(), "Booking Successfully", Toast.LENGTH_SHORT).show()
                    this.dismiss()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill full schedule info", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}