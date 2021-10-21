package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var homeProgressBar: ProgressBar
    private val baseUrl = "https://fakestoreapi.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.productRecyclerView)
        homeProgressBar = view.findViewById(R.id.homeProgressBar)
        getProducts(view)
    }

    private fun loadProductsSuccessfully(view:View, products : List<Product>){
        val clickListener: (Product, FragmentNavigator.Extras) -> Unit = { product, extras ->
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
            Navigation.findNavController(view).navigate(action,extras)
        }
        recycler.adapter = ProductAdapter(clickListener, products)
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

    private fun getProducts(view: View) {
        val isFetched: Boolean = ConfigManager.prefs(requireActivity())
            .getBoolean(getString(R.string.pref_is_database_fetched), false)
        if (isFetched) {
            loadProductsSuccessfully(view, ProductDatabase.fetchAllProducts())
        } else {
            loadProductsFromAPI(view)
        }
    }

    private fun loadProductsFromAPI(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getProducts("products")
            val productsReceived = call.body()
            activity?.runOnUiThread {
                if(call.isSuccessful) {
                    productsReceived?.forEach {
                        val amountAddedToCart = ProductDatabase
                            .fetchProduct(it.id)
                            ?.amountAddedToCart
                            ?: 0
                        ProductDatabase.addProduct(
                            it.id,
                            it.title,
                            it.price,
                            it.description,
                            it.category,
                            it.image,
                            it.rating.count,
                            it.rating.rate,
                            amountAddedToCart
                        )
                    }
                    ConfigManager.prefs(requireActivity()).edit().putBoolean(
                        getString(R.string.pref_is_database_fetched),
                        true
                    ).apply()
                    loadProductsSuccessfully(view, ProductDatabase.fetchAllProducts())
                }
                else {
                    Toast.makeText(
                        requireContext(),
                        "Error al solicitar productos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}