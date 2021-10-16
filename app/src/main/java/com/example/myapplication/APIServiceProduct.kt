package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIServiceProduct {

    @GET
    suspend fun getProducts(@Url url:String):Response<List<Product>>

}