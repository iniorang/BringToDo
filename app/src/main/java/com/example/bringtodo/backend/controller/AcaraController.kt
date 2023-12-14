package com.example.bringtodo.backend.controller

import com.example.bringtodo.backend.Service.AcaraBody
import com.example.bringtodo.backend.Service.AcaraData
import com.example.bringtodo.backend.Service.AcaraService
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.backend.model.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class AcaraController {
    companion object{
        private var acaraService : AcaraService = ApiClient.getService(AcaraService::class.java)
        fun insertAcara(studioname: String,desc : String, date: String,time:String,  callback: (Acara?) -> Unit) {
            val acaraData = AcaraData(
                AcaraBody(studioname, "", "", "")
            )
            acaraService.insert(acaraData).enqueue(object : Callback<Acara> {
                override fun onResponse(call: Call<Acara>, response: Response<Acara>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                    } else {
                        println("HTTP Request Failed: ${response.code()} - ${response.message()}")
                        callback(null)
                    }

                override fun onFailure(call: Call<Acara>, t: Throwable) {
                    println("HTTP Request Failure: ${t.message}")
                    callback(null)
                }
            })
        }

        fun getAcaras(callback: (ApiResponse<List<Acara>>?) -> Unit){
            acaraService.getall().enqueue(object : Callback<ApiResponse<List<Acara>>> {
                override fun onResponse(call: Call<ApiResponse<List<Acara>>>, response: Response<ApiResponse<List<Acara>>>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                    } else {
//                        println("Empty")
                        callback(null)
                    }

                override fun onFailure(call: Call<ApiResponse<List<Acara>>>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }

        fun deleteAcara(id: Int) {
            acaraService.delete(id).enqueue(object : Callback<ApiResponse<Acara>>{
                override fun onResponse(
                    call: Call<ApiResponse<Acara>>,
                    response: Response<ApiResponse<Acara>>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                    } else {
//                        println("Empty")
                    }

                override fun onFailure(
                    call: Call<ApiResponse<Acara>>,
                    t: Throwable
                ) {
                    print(t.message)
                }
            })
        }
    }
}