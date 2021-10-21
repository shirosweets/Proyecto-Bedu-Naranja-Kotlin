package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_payment_resume.*

class PaymentResumeFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var payButton: MaterialButton
    private lateinit var subTotalValue: TextView
    private lateinit var totalValue: TextView
    private val shippingCost: Float = 30f


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

        subTotalValue = view.findViewById(R.id.subtotalValue)
        subTotalValue.text = "$ %.2f".format(getSubtotal())
        totalValue = view.findViewById(R.id.totalValue)
        totalValue.text = "$ %.2f".format(getSubtotal() + shippingCost)

        payButton = view.findViewById(R.id.paymentResumePayButton)
        payButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_paymentResumeFragment_to_sucessfulPaymentFragment,
                null,
            )
        }
    }

    private fun getSubtotal(): Float {
        val products = ProductDatabase.fetchCartProducts()
        var subTotal = 0f
        products.forEach { subTotal += it.price ?: 0f }
        return subTotal
    }

    private fun getPaymentOptions():List<Option>{
        return listOf(
            Option("Dirección Actual",R.drawable.ic_location),
            Option("Método de pago actual",R.drawable.ic_credit_card)
        )
    }

    private fun getOptionsClickListener():(String) -> Unit = {}
}