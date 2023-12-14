package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.ApiResponse
import com.example.bringtodo.backend.model.Barang
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class BarangData(
    @SerializedName("data")
    val data:BarangBody
)
data class BarangBody(
    val name: String
)

interface BarangService {
    @GET("barangs")
    fun getAll():Call<ApiResponse<List<Barang>>>

    @POST("barangs")
    fun insert(@Body body: BarangData): Call<Barang>

    @DELETE("barangs/{id}")
    fun delete(@Path("id")id:Int):Call<ApiResponse<Barang>>
}