package com.example.bringtodo.backend.controller

import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.Barang
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class BarangController {
    companion object{
        private var barangService : BarangService = ApiClient.getService(BarangService::class.java)
        fun getBarangs(callback:(List<Barang>?)->Unit){
            barangService.getBarang().enqueue(object : Callback<List<Barang>>{
                override fun onResponse(call: Call<List<Barang>>,response: Response: Response<List<Barang>>): Unit =
                    if(response.isSuccessful){
                        callback(response.body())
                    }
            })
        }
    }
}