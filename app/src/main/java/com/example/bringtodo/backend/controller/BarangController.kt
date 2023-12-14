package com.example.bringtodo.backend.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.bringtodo.backend.Service.AcaraBody
import com.example.bringtodo.backend.Service.AcaraData
import com.example.bringtodo.backend.Service.AcaraService
import com.example.bringtodo.backend.Service.BarangBody
import com.example.bringtodo.backend.Service.BarangData
import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.backend.model.Barang
import com.example.bringtodo.backend.model.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class BarangController {
    companion object{
        private var barangService : BarangService = ApiClient.getService(BarangService::class.java)
        fun insertBarang(name: String,callback: (Barang?) -> Unit) {
            val BarangData = BarangData(
                BarangBody(name = name)
            )
            barangService.insert(BarangData).enqueue(object : Callback<Barang> {
                override fun onResponse(call: Call<Barang>, response: Response<Barang>): Unit =
                    if (response.isSuccessful) {
//                        println(response.body())
                        callback(response.body())
                    } else {
//                        println("Empty")
                        callback(null)
                    }

                override fun onFailure(call: Call<Barang>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }

        fun getBarangs(callback: (ApiResponse<List<Barang>>?) -> Unit){
            barangService.getAll().enqueue(object : Callback<ApiResponse<List<Barang>>> {
                override fun onResponse(call: Call<ApiResponse<List<Barang>>>, response: Response<ApiResponse<List<Barang>>>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                    } else {
//                        println("Empty")
                        callback(null)
                    }

                override fun onFailure(call: Call<ApiResponse<List<Barang>>>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }

        fun deleteBarangs(id: Int) {
            barangService.delete(id).enqueue(object : Callback<ApiResponse<Barang>>{
                override fun onResponse(
                    call: Call<ApiResponse<Barang>>,
                    response: Response<ApiResponse<Barang>>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                    } else {
//                        println("Empty")
                    }

                override fun onFailure(
                    call: Call<ApiResponse<Barang>>,
                    t: Throwable
                ) {
                    print(t.message)
                }
            })
        }

    }
}