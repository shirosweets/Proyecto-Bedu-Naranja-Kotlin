package com.example.myapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShoppingCartFragment : Fragment() {
    private lateinit var cartRecycler : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRecycler = view.findViewById(R.id.productCartRecyclerView)
        cartRecycler.layoutManager = LinearLayoutManager(activity)
        val products = ProductDatabase.fetchAllProducts().filter {
            (it.amountAddedToCart ?: 0) > 0
        }
        cartRecycler.adapter = ProductCartAdapter(products)
    }
}