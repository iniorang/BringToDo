package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.Barang
import retrofit2.Call
import retrofit2.http.GET

data class BarangData(val namaBarang:String)
interface BarangService {
    @GET("barangs")
    fun getBarang():Call<List<Barang>>
}