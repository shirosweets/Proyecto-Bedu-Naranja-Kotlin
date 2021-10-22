package com.example.myapplication

import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductAPITest {
    private val baseLoginUrl = "https://reqres.in/"
    private val baseUserUrl = "https://reqres.in/api/users/"
    private val baseProductUrl = "https://fakestoreapi.com/"

    private fun log(msg: String) = println("DEBUG: $msg")

    private fun fetchProducts(): List<DataProduct>? {
        return runBlocking {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseProductUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val call = retrofit.create(APIService::class.java).getProducts("products")
            val products = call.body()
            log("Products found: ${products?.size ?: "null"}")
            return@runBlocking products
        }
    }

    @Test
    fun `fetch products finds products`() {
        val products = fetchProducts()
        assert(!products.isNullOrEmpty())
        assert((products?.size ?: 0) > 0)
    }

    @Test
    fun `all products found`() {
        val products = fetchProducts()
        assert(!products.isNullOrEmpty())
        assert(products?.size == 20)
    }
}
