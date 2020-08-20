package com.example.booking_room.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.booking_room.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view :View = inflater.inflate(R.layout.fragment_home, container, false)
        val vfliper: ViewFlipper = view.findViewById(R.id.vfliper)
               val arrhinhanh: ArrayList<String> = ArrayList()
        arrhinhanh.add("https://noithatduonggia.vn/wp-content/uploads/2018/05/thiet-ke-phong-hop-dep-1.jpg")
        arrhinhanh.add("https://noithatduonggia.vn/wp-content/uploads/2018/05/thiet-ke-phong-hop-dep-6.jpg")
        arrhinhanh.add("https://saigonnoithat.net/data/sgnt/img/upload/NoiThatVanPhong/thiet-ke-noi-that-phong-hop-dep-17.gif")
        arrhinhanh.add("https://dplusvn.com/wp-content/uploads/2020/01/tam-quan-trong-cua-thiet-ke-phong-giam-doc.jpg")
        for (i in arrhinhanh){
            var imageview = ImageView(activity?.applicationContext!!)
            Picasso.with(activity?.application)
                .load(i).into(imageview)
            imageview.scaleType = ImageView.ScaleType.FIT_XY
            vfliper.addView(imageview)

        }
        vfliper.flipInterval = 5000
        vfliper.isAutoStart = true
        var inAnimation: Animation = AnimationUtils.loadAnimation(activity?.applicationContext!!,R.anim.slide_in_right)
        var outAnimation: Animation = AnimationUtils.loadAnimation(activity?.applicationContext!!,R.anim.slide_out_right)
        vfliper.inAnimation = inAnimation
        vfliper.outAnimation = outAnimation
        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}