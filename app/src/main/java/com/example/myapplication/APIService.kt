package com.example.myapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    @GET
    suspend fun getProducts(@Url url: String): Response<List<DataProduct>>

    @GET
    suspend fun getUserData(@Url url: String): Response<User>

    @FormUrlEncoded
    @POST("api/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseBody>
}