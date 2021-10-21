package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var userFirstName : TextView
    private lateinit var userImage : ShapeableImageView
    private lateinit var userEmail : TextView
    private var sharedPreferences: SharedPreferences? = null

    private lateinit var profSettings: ImageView
    private lateinit var closeSession: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.optionRecyclerView)
        userFirstName = view.findViewById(R.id.user_first_name)
        userImage = view.findViewById(R.id.user_shapeable_image)
        userEmail = view.findViewById(R.id.user_email)

        profSettings = view.findViewById(R.id.prof_settings)
        closeSession = view.findViewById(R.id.buttonCloseSession)

        recycler.adapter = OptionAdapter( getOptionsClickListener(), getProfileOptions())
        recycler.layoutManager = LinearLayoutManager(activity)

        sharedPreferences =
            this.activity?.getSharedPreferences(
                "org.bedu.sharedpreferences",
                Context.MODE_PRIVATE
            )

        profSettings.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_settingsFragment,
                null
            )
        }
        closeSession.setOnClickListener { closeSession() }
        setUserData()

    }

    private fun setUserData(){
        userFirstName.setText(sharedPreferences?.getString("USER_FIRST_NAME","Janet"))
        userEmail.setText(sharedPreferences?.getString("USER_EMAIL","janet.weaver@reqres.in"))
        Picasso.get().load(sharedPreferences?.getString("USER_AVATAR","https://reqres.in/img/faces/2-image.jpg")).into(userImage)
    }

    private fun closeSession(){
        sharedPreferences?.edit()
            ?.putBoolean("USER_ACCESS",false)
            ?.apply()
        findNavController().navigate(
            R.id.action_profileFragment_to_mainActivity,
            null
        )
    }

    private fun getProfileOptions():List<Option>{
        return listOf(
            Option(getString(R.string.my_address),R.drawable.ic_location),
            Option(getString(R.string.payment_methods),R.drawable.ic_credit_card),
            Option(getString(R.string.my_orders_cart),R.drawable.ic_restore),
            Option(getString(R.string.notifications),R.drawable.ic_notifications_active),
            Option(getString(R.string.change_password),R.drawable.ic_lock),)
    }

    private fun getOptionsClickListener(): (String) -> Unit {
        return {
            when (it) {
                "Mis direcciones" -> {
                    val addressFragment = AddressFragment()
                    addressFragment.show(parentFragmentManager, "fragment")
                }

                else -> {
                }
            }
        }
    }
}