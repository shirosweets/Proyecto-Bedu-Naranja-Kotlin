package com.example.myapplication

data class User(val data: Data) {
    data class Data(val email: String, val first_name: String, val avatar: String)
}