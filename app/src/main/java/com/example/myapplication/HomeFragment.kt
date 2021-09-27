package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private val detailTransitionOption = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        val clickListener: (Product) -> Unit = {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(it)
            Navigation.findNavController(view).navigate(action, detailTransitionOption)
        }

        recycler.adapter = ProductAdapter(view, clickListener, MenuActivity.products)
        recycler.layoutManager = LinearLayoutManager(activity)
    }

}