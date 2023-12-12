package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.backend.model.Barang
import com.example.bringtodo.backend.model.ApiResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class AcaraData(
    @SerializedName("data")
    val data: AcaraBody
)

data class AcaraBody(
    val name: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("date")
    val date: String,
)
interface AcaraService {
    @POST("acaras")
    fun insert(@Body body: AcaraData): Call<Acara>

    @GET("acaras")
    fun getall() : Call<ApiResponse<List<Acara>>>

    @DELETE("acaras/{id}")
    fun delete(@Path("id") id: Int) : Call<ApiResponse<Acara>>
}