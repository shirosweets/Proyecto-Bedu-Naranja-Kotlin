package com.example.myapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ShoppingCartFragment : Fragment() {
    private lateinit var cartRecycler : RecyclerView
    private lateinit var proceedToPaymentButton: MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        proceedToPaymentButton = view.findViewById(R.id.cart_proceed_buy_button)
        proceedToPaymentButton.setOnClickListener {
            if (ProductDatabase.fetchCartProducts().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.need_to_add_prod),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                findNavController().navigate(
                    R.id.action_shoppingCartFragment_to_paymentResumeFragment,
                    null
                )
            }
        }
        cartRecycler = view.findViewById(R.id.cart_recycler_view)
        cartRecycler.layoutManager = LinearLayoutManager(activity)
        val products = ProductDatabase.fetchAllProducts().filter {
            (it.amountAddedToCart ?: 0) > 0
        }
        cartRecycler.adapter = ProductCartAdapter(products)
    }
}