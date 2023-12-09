package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.Barang
import com.example.bringtodo.backend.model.BarangResponse
import retrofit2.Call
import retrofit2.http.GET

data class BarangData(val namaBarang:String)
interface BarangService {
    @GET("barangs")
    fun getBarang():Call<BarangResponse<List<Barang>>>
}