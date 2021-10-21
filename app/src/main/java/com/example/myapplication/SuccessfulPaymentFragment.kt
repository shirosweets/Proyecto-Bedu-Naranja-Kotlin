package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class SuccessfulPaymentFragment : Fragment() {
    private lateinit var backToMenuButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sucessful_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToMenuButton = view.findViewById(R.id.successPaymentBackButton)
        backToMenuButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_sucessfulPaymentFragment_to_homeFragment,
                null
            )
        }
    }
}
