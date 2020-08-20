package com.example.booking_room.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.booking_room.R
import com.example.booking_room.models.Apartment
import com.example.booking_room.models.Room
import com.example.booking_room.services.ApartmentService
import com.example.booking_room.services.RoomService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class RoomEditorDialog : DialogFragment() {
    private var list = ArrayList<Apartment>()
    private lateinit var adapter: ArrayAdapter<Apartment>
    private lateinit var spinner: Spinner
    private lateinit var btnRoomCreate: Button
    private lateinit var btnRoomCancel: Button
    private lateinit var etRoomName: EditText
    private lateinit var etNumberOfSeat: EditText
    private var isEdit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.room_editor_layout, container, false)
        var room: Room ? = null

        spinner = view.findViewById(R.id.spinner_apartment)
        btnRoomCreate = view.findViewById(R.id.btn_room_create)
        btnRoomCancel = view.findViewById(R.id.btn_room_cancel)
        etRoomName = view.findViewById(R.id.et_room_name)
        etNumberOfSeat = view.findViewById(R.id.et_room_seats)

        if (arguments?.getSerializable("room") is Room) {
            room = arguments?.getSerializable("room") as Room

            etRoomName.setText(room.name)
            etRoomName.isEnabled = false
            etNumberOfSeat.setText(room.numberOfSeats.toString())
            spinner.isEnabled = false
            btnRoomCreate.text = "Update"
            view.findViewById<TextView>(R.id.txt_room_label).text = "Update Room"

            isEdit = true
        }

        ApartmentService.getInstance().getApartment().addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                children.forEach {
                    val apartment = it.getValue(Apartment::class.java)
                    if (apartment != null) {
                        list.add(apartment)
                    }
                }

                if (room?.apartment != null) {
                    spinner.setSelection(list.indexOf(list.find { apartment -> apartment.name == room.apartment }))
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        adapter = ArrayAdapter<Apartment>(requireActivity(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    println(parent.selectedItem)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnRoomCancel.setOnClickListener {
            this.dismiss()
        }

        btnRoomCreate.setOnClickListener {
            createRoom()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    private fun createRoom() {
        if (etRoomName.text.toString().trim().isEmpty()) {
            etRoomName.error = "Please enter room name"
            etRoomName.requestFocus()
            return
        }

        if (etNumberOfSeat.text.toString().trim().isEmpty()) {
            etNumberOfSeat.error = "Please enter number of seat"
            etNumberOfSeat.requestFocus()
            return
        }

        val room = Room(etRoomName.text.toString(), spinner.selectedItem.toString(), etNumberOfSeat.text.toString().toInt())

        if (isEdit) {
            RoomService.getInstance().createRoom(room).addOnCompleteListener {
                    this@RoomEditorDialog.dismiss()
            }
        } else {
            RoomService.getInstance().isExisted("${room.apartment}_${room.name}").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    println(snapshot.exists())
                    if (!snapshot.exists()) {
                        RoomService.getInstance().createRoom(room).addOnCompleteListener {
                            this@RoomEditorDialog.dismiss()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Room is existed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    println(error)
                }
            })
        }
    }
}