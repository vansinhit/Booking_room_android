package com.example.booking_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booking_room.fragments.BookingFragment
import com.example.booking_room.fragments.HomeFragment
import com.example.booking_room.fragments.ManageBookingFragmentFragment
import com.example.booking_room.fragments.ManagementFragment
import com.example.booking_room.services.AuthService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navigationMenu: BottomNavigationView  = findViewById(R.id.navigation)
        val navigationMenuAdmin: BottomNavigationView  = findViewById(R.id.navigation_admin)

        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        val homeFragment = HomeFragment()
        val bookingFragment = BookingFragment()
        val managementFragment = ManagementFragment()
        val manageBookingFragment = ManageBookingFragmentFragment()

        if (AuthService.getInstance().getCurrentUser()?.email == "hainguyen27798@gmail.com") {
            navigationMenu.visibility = View.GONE;
            navigationMenuAdmin.visibility = View.VISIBLE;
            makeCurrentFragment(managementFragment)
        } else {
            navigationMenu.visibility = View.VISIBLE;
            navigationMenuAdmin.visibility = View.GONE;
            makeCurrentFragment(homeFragment)
        }

        bottomSheetDialog.setContentView(view)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.booking -> makeCurrentFragment(bookingFragment)
                R.id.account -> bottomSheetDialog.show()
            }
            true
        }

        navigation_admin.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.management -> makeCurrentFragment(managementFragment)
                R.id.managementBooking -> makeCurrentFragment(manageBookingFragment)
                R.id.account -> bottomSheetDialog.show()
            }
            true
        }

        view.logout.setOnClickListener {
            bottomSheetDialog.hide()
            bottomSheetDialog.dismiss()
            Toast.makeText(baseContext, "Log Out", Toast.LENGTH_LONG).show()
            AuthService.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        view.change_pasword.setOnClickListener{
            startActivity(Intent(this, ChagePassword::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}