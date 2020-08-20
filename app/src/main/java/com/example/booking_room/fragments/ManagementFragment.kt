package com.example.booking_room.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import com.example.booking_room.R
import com.example.booking_room.adapters.RoomAdapter
import com.example.booking_room.dialog.RoomEditorDialog
import com.example.booking_room.models.Room
import com.example.booking_room.services.RoomService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManagementFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listView: ListView ? = null
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var arrayList: ArrayList<Room>
    private lateinit var eventListener: ValueEventListener
    private lateinit var databaseReference: DatabaseReference

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
        val v: View = inflater.inflate(R.layout.fragment_management, container, false)

        listView = v.findViewById(R.id.list_view)
        arrayList = ArrayList()
        roomAdapter = RoomAdapter(activity?.applicationContext!!, arrayList, R.layout.item_layout, requireActivity().supportFragmentManager)
        listView?.adapter = roomAdapter

        val btnAddRoom = v.findViewById<ImageView>(R.id.add_room)

                btnAddRoom.setOnClickListener {
                val dialog = RoomEditorDialog()

            dialog.show(requireActivity().supportFragmentManager, "")
        }

        setRoomData()

        return v
    }

    private fun setRoomData() {
        val progress = ProgressDialog(requireContext())
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)
        progress.show()

        eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                arrayList.clear()

                children.forEach {
                    val room = it.getValue(Room::class.java)
                    if (room != null) {
                        arrayList.add(room)
                    }
                }
                roomAdapter.notifyDataSetChanged()

                Timer("", false).schedule(500) {
                    progress.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        databaseReference = RoomService.getInstance().getRoom()

        databaseReference.addValueEventListener(eventListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        databaseReference.removeEventListener(eventListener)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManagementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}