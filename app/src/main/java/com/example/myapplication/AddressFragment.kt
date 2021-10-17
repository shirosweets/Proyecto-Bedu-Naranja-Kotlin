package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddressFragment :  BottomSheetDialogFragment() {


    private lateinit var imageButtonClose :ImageView
    private lateinit var buttonUpdateAddress : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonClose = view.findViewById(R.id.imageButtonClose)
        buttonUpdateAddress = view.findViewById(R.id.buttonUpdateAddress)

        imageButtonClose.setOnClickListener {
            dismiss()
        }
        buttonUpdateAddress.setOnClickListener {
            //SET ACTUAL LOCATION
        }

    }


}