package com.example.bringtodo.backend.controller

import android.util.Log
import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.Barang
import com.example.bringtodo.backend.model.BarangResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class BarangController {
    companion object{
        private var barangService : BarangService = ApiClient.getService(BarangService::class.java)
        fun getBarang(callback:(BarangResponse<List<Barang>>?)->Unit){
            barangService.getBarang().enqueue(object : Callback<BarangResponse<List<Barang>>>{
                override fun onResponse(call: Call<BarangResponse<List<Barang>>>,response: Response<BarangResponse<List<Barang>>>): Unit =
                    if(response.isSuccessful){
                        val barangResponse = response.body()
                        if(barangResponse?.data != null){
                            callback(barangResponse)
                        }else{
                            callback(null)
                        }
                    }
                else{
                    callback(null)
                    }
                override fun onFailure(call: Call<BarangResponse<List<Barang>>>, t: Throwable) {
                    callback(null)
                    Log.e("BarangController", "Error: ${t.message}", t)
                }
            })
        }
    }
}