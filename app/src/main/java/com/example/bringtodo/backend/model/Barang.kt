package com.example.bringtodo.backend.model

import com.google.gson.annotations.SerializedName

class Barang {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("attributes")
    var attr : BarangAttributes = BarangAttributes()
}

class BarangAttributes{
    @SerializedName("name")
    var name : String = ""
}
