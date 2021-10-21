package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class PaymentResumeFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var payButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_resume, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.paymentResumeRecyclerView)
        recycler.adapter = OptionAdapter( getOptionsClickListener(), getPaymentOptions())
        recycler.layoutManager = LinearLayoutManager(activity)
        payButton = view.findViewById(R.id.paymentResumePayButton)
        payButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_paymentResumeFragment_to_sucessfulPaymentFragment,
                null,
            )
        }
    }

    private fun getPaymentOptions():List<Option>{
        return listOf(
            Option("Dirección Actual",R.drawable.ic_location),
            Option("Método de pago actual",R.drawable.ic_credit_card)
        )
    }

    private fun getOptionsClickListener():(String) -> Unit = {}
}