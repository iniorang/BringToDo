package com.example.bringtodo.backend.controller

import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.Barang
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class BarangController {
    companion object{
        private var barangService : BarangService = ApiClient.getService(BarangService::class.java)
        fun getBarang(callback:(List<Barang>?)->Unit){
            barangService.getBarang().enqueue(object : Callback<List<Barang>>{
                override fun onResponse(call: Call<List<Barang>>,response: Response<List<Barang>>): Unit =
                    if(response.isSuccessful){
                        callback(response.body())
                    }
                else{

                    }
                override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                    callback(null)
                }
            })
        }
    }
}