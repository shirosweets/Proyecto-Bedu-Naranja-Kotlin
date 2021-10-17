package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var recycler:RecyclerView
    private lateinit var userFirstName : TextView
    private lateinit var userImage : ShapeableImageView
    private lateinit var userEmail : TextView
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.optionRecyclerView)
        userFirstName = view.findViewById(R.id.user_first_name)
        userImage = view.findViewById(R.id.user_shapeable_image)
        userEmail = view.findViewById(R.id.user_email)


        recycler.adapter = OptionAdapter( getOptionsClickListener(), getProfileOptions())
        recycler.layoutManager = LinearLayoutManager(activity)

        sharedPreferences =
            this.activity?.getSharedPreferences("org.bedu.sharedpreferences", Context.MODE_PRIVATE)

        setUserData()

    }

    private fun setUserData(){
        userFirstName.setText(sharedPreferences?.getString("USER_FIRST_NAME","Janet"))
        userEmail.setText(sharedPreferences?.getString("USER_EMAIL","janet.weaver@reqres.in"))
        Picasso.get().load(sharedPreferences?.getString("USER_AVATAR","https://reqres.in/img/faces/2-image.jpg")).into(userImage)
    }

    private fun getProfileOptions():List<Option>{
        return listOf(
            Option("Mis direcciones",R.drawable.ic_location),
            Option("Método de pago",R.drawable.ic_credit_card),
            Option("Pedidos",R.drawable.ic_restore),
            Option("Notificaciones",R.drawable.ic_notifications_active),
            Option("Cambiar contraseña",R.drawable.ic_lock),)
    }

    private fun getOptionsClickListener():(String) -> Unit{

        val clickListener: (String) -> Unit = {
            when(it){
                "Mis direcciones" -> {
                    val addressFragment = AddressFragment()
                    addressFragment.show(this.parentFragmentManager,"fragment")
                }

                else -> {}
            }
        }

        return clickListener
    }


}