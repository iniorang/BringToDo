package com.example.bringtodo.backend.model

import com.google.gson.annotations.SerializedName
data class ApiResponse<T>(
    @SerializedName("data")
    var data: T?
)

data class DataWrapper(
    @SerializedName("data")
    var data: Any?
)