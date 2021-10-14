package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileFragment : Fragment() {

    private lateinit var recycler:RecyclerView

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

        val clickListener: () -> Unit = {}

        recycler.adapter = OptionAdapter( clickListener, getProfileOptions())
        recycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun getProfileOptions():List<Option>{
        return listOf(
            Option("Mis direcciones",R.drawable.ic_location),
            Option("Método de pago",R.drawable.ic_credit_card),
            Option("Pedidos",R.drawable.ic_restore),
            Option("Notificaciones",R.drawable.ic_notifications_active),
            Option("Cambiar contraseña",R.drawable.ic_lock),)
    }


}