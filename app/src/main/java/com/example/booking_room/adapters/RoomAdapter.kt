package com.example.booking_room.adapters

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.booking_room.R
import com.example.booking_room.dialog.BookingDialog
import com.example.booking_room.dialog.RoomEditorDialog
import com.example.booking_room.models.Room
import com.example.booking_room.services.RoomService

class RoomAdapter(var context: Context, var arrayList: ArrayList<Room>, var layoutResource: Int, var fragmentManager: FragmentManager) : BaseAdapter()  {
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return arrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = View.inflate(context, layoutResource, null)

        val apartment: TextView = view.findViewById(R.id.card_item_apartment)
        val name: TextView = view.findViewById(R.id.card_item_name)
        val seats: TextView = view.findViewById(R.id.card_item_seats)
        val btnEdit: Button ? = view.findViewById(R.id.btn_card_edit)
        val btnDelete: Button ? = view.findViewById(R.id.btn_card_delete)
        val btnBooking: Button ? = view.findViewById(R.id.btn_booking)

        val room: Room = arrayList[position]

        apartment.text = room.apartment
        name.text = room.name
        seats.text = room.numberOfSeats.toString()

        btnEdit?.setOnClickListener {
            val dialog = RoomEditorDialog()

            val args = Bundle()
            args.putSerializable("room", room)

            dialog.arguments = args

            dialog.show(fragmentManager, "")
        }

        btnDelete?.setOnClickListener {
            RoomService.getInstance().remoteRoom("${room.apartment}_${room.name}")
        }

        btnBooking?.setOnClickListener {
            val dialog = BookingDialog()

            val args = Bundle()
            args.putSerializable("room", room)

            dialog.arguments = args

            dialog.show(fragmentManager, "")
        }

        return view!!
    }
}