package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.BarangModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class BarangData(val namaBarang:String?)
interface BarangService {
    @GET("api/barangs")
    fun getBarang():Call<List<BarangModel>>

    @POST("api/barangs")
    fun saveBarang(@Body body: BarangData): Call<BarangModel>
}