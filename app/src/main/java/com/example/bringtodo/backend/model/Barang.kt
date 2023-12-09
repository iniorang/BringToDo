package com.example.bringtodo.backend.model

import com.google.gson.annotations.SerializedName

class Barang {
    var id: Int = 0
    var namaBarang : String = ""
}

data class BarangResponse<T>(
    @SerializedName("data")
    val data: T?
)