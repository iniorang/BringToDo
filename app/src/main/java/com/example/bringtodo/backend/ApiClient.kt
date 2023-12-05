package com.example.bringtodo.backend

import retrofit2.Retrofit

class ApiClient {
    private var retrofit: Retrofit? = null
    private val baseUrl = "http://10.0.2.2:1337/api/"
    private val token = "0cc4471e8da2fde8120ff45d899a5ead28c69d3f126d6902b3ecd4922dbd8462e2832f7c08f854b23e5b010512e915842e627bccd49860dc7bd9674d762c684f116313b500f9028e67e9df58c445fca4285baa5879bffd73ece92ada9d58275f828e142b34738d0fa81b284a724180f8f1c15b4312208654d5807c7a65632fcb"
}