package com.example.booking_room.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.booking_room.R
import com.example.booking_room.adapters.RoomAdapter
import com.example.booking_room.models.Room
import com.example.booking_room.services.RoomService
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listView: ListView? = null
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var arrayList: ArrayList<Room>
    private lateinit var eventListener: ValueEventListener
    private lateinit var databaseReference: DatabaseReference
    private var searchString : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_booking, container, false)
        databaseReference = RoomService.getInstance().getRoom()
        listView = v.findViewById(R.id.list_room_booking)
        searchString =v.findViewById(R.id.et_search_booking)
        arrayList = ArrayList()
        roomAdapter = RoomAdapter(
            activity?.applicationContext!!,
            arrayList,
            R.layout.item_booking_layout,
            requireActivity().supportFragmentManager
        )
        listView?.adapter = roomAdapter
        setRoomData()
        searchString!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(ccs: CharSequence?, start: Int, before: Int, count: Int) {
                search(ccs.toString().toLowerCase())
            }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        return v;
    }

    private fun setRoomData() {
        val progress = ProgressDialog(requireContext())
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)
        progress.show()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                arrayList.clear()
                if (searchString!!.text.toString() == "") {
                    children.forEach {
                        val room = it.getValue(Room::class.java)
                        if (room != null) {
                            arrayList.add(room)
                        }
                    }
                    roomAdapter
                    listView!!.adapter = roomAdapter
                }
                Timer("", false).schedule(500) {
                    progress.dismiss()
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun search(data :String){
        var query : Query = databaseReference.orderByChild("name").startAt(data).endAt(data+"\uf8ff")
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                arrayList.clear()
                children.forEach {
                    val room = it.getValue(Room::class.java)
                    if (room != null) {
                        arrayList.add(room)
                    }
                }
                roomAdapter
                listView?.adapter = roomAdapter

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}