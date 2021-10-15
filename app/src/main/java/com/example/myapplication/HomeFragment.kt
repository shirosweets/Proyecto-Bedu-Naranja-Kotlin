package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var homeProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.productRecyclerView)
        homeProgressBar = view.findViewById(R.id.homeProgressBar)

        val clickListener: (Product, FragmentNavigator.Extras) -> Unit = {
            product,extras ->
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
            Navigation.findNavController(view).navigate(action,extras)
        }
        while(MenuActivity.products.isEmpty()){
            //wait
        }
        recycler.adapter = ProductAdapter( clickListener, MenuActivity.products)

        recycler.layoutManager = LinearLayoutManager(activity)

        recycler.visibility = View.VISIBLE
        homeProgressBar.visibility = View.INVISIBLE

    }

}