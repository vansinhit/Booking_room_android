
package com.example.booking_room.adapters
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.booking_room.R
import com.example.booking_room.models.Booking


class BookingAdapter(var context: Context, var arrayList: ArrayList<Booking>, var layoutResource: Int, var fragmentManager: FragmentManager) : BaseAdapter()  {
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

        val username: TextView? = view.findViewById(R.id.card_item_booking_username)
        val roomName: TextView? = view.findViewById(R.id.card_item_booking_roomname)
        val apartment: TextView? = view.findViewById(R.id.card_item_booking_apartment)
        val date: TextView? = view.findViewById(R.id.card_item_booking_date)
        val fromTime: TextView? = view.findViewById(R.id.card_item_booking_fromTime)
        val toTime: TextView? = view.findViewById(R.id.card_item_booking_toTime)

        val booking: Booking = arrayList[position]

        username?.text = booking.userEmail.toString()
        roomName?.text = booking.roomName.toString()
        apartment?.text = booking.apartment.toString()
        date?.text = booking.date.toString()
        fromTime?.text = booking.fromTime.toString()
        toTime?.text = booking.toTime.toString()

        return view!!
    }
}