package com.example.myapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var recycler: RecyclerView
    private lateinit var userFirstName : TextView
    private lateinit var userImage : ShapeableImageView
    private lateinit var userEmail : TextView
    private lateinit var sharedPreferences: SharedPreferences
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
        themeSwitch = view.findViewById(R.id.theme_switch)
        themeSwitch.isChecked = ConfigManager.isDarkTheme(requireContext())
        themeSwitch.setOnCheckedChangeListener { _, _ ->
            activity?.applicationContext?.let { ConfigManager.switchTheme(it) }
            activity?.recreate()
        }

        closeSession = view.findViewById(R.id.buttonCloseSession)
        closeSession.setOnClickListener {
            LoginManager.logOut(requireActivity())
            findNavController().navigate(
                R.id.action_profileFragment_to_mainActivity,
                null
            )
        }

        recycler.adapter = OptionAdapter( getOptionsClickListener(), getProfileOptions())
        recycler.layoutManager = LinearLayoutManager(activity)

        sharedPreferences = ConfigManager.prefs(requireActivity())
        setUserData()
    }

    private fun setUserData(){
        userFirstName.text = sharedPreferences.getString("USER_FIRST_NAME", "Janet")
        userEmail.text = sharedPreferences.getString("USER_EMAIL", "janet.weaver@reqres.in")
        Picasso.get().load(
            sharedPreferences.getString(
                "USER_AVATAR",
                "https://reqres.in/img/faces/2-image.jpg"
            )
        ).into(userImage)
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
                getString(R.string.my_address) -> {
                    val addressFragment = AddressFragment()
                    addressFragment.show(parentFragmentManager, "fragment")
                } else -> {}
            }
        }
    }
}