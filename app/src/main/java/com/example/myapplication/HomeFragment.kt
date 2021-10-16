package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var homeProgressBar: ProgressBar
    private val baseUrl = "https://fakestoreapi.com/"

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

        getProducts(view)
    }


    private fun loadProductsSuccessfully(view:View, products : List<Product>){
        val clickListener: (Product, FragmentNavigator.Extras) -> Unit = {
                product,extras ->
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
            Navigation.findNavController(view).navigate(action,extras)
        }
        recycler.adapter = ProductAdapter( clickListener, products)

        recycler.layoutManager = LinearLayoutManager(activity)

        recycler.visibility = View.VISIBLE
        homeProgressBar.visibility = View.INVISIBLE

    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getProducts(view: View){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIServiceProduct::class.java).getProducts("products")
            val productsReceived = call.body()
            activity?.runOnUiThread{
                if(call.isSuccessful){
                    loadProductsSuccessfully(view,productsReceived ?: listOf<Product>())
                }else{
                    Toast.makeText(requireContext(),"Error al solicitar productos",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}