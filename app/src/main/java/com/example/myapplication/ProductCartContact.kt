package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class ProductCartContact : Fragment() {
    private lateinit var addItem: ImageView
    private lateinit var removeItem: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_product_cart_contact,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addItem = view.findViewById(R.id.cart_contact_plus_sign)
        removeItem = view.findViewById(R.id.cart_contact_remove_sign)

        addItem.setOnClickListener {

        }
    }
}