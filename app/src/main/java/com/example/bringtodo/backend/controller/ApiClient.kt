package com.example.bringtodo.backend.controller

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val baseUrl = "http://10.0.2.2:1337/api/"
    companion object{
        private val client: Retrofit = Retrofit.Builder()
            .baseUrl("baseUrl")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun <T> getService(serviceClass:Class<T>):T{
            return client.create(serviceClass)
        }
    }
}