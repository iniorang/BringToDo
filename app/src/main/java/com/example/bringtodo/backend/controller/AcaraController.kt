package com.example.bringtodo.backend.controller

import android.content.Context
import android.util.Log
import com.example.bringtodo.backend.NotifHelper
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

        fun insertAcara(jwt: String, studioname: String, date: String, waktu:String, barangForms: List<String>, context: Context,  callback: (Acara?) -> Unit) {
            val bawaan = barangForms.joinToString(", ")
            val AcaraData = AcaraData(AcaraBody(name = studioname, "", date, waktu, bawaan))
            var acaraService : AcaraService = ApiClient.getAuthService(AcaraService::class.java, jwt)

            acaraService.insert(AcaraData).enqueue(object : Callback<Acara> {
                override fun onResponse(call: Call<Acara>, response: Response<Acara>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                        val acara = response.body()
                        callback(acara)
                        if (acara != null) {
                            val dateTimeMillis = NotifHelper.convertDateTimeToMillis(date, waktu)
                            println("Converted DateTimeMillis: $dateTimeMillis")
                            NotifHelper.notifHelper(context, dateTimeMillis, studioname)
                        }else{
//                            Todo
                        }
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

        fun getAcaras(jwt:String, callback: (ApiResponse<List<Acara>>?) -> Unit){
            var acaraService : AcaraService = ApiClient.getAuthService(AcaraService::class.java, jwt)

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

        fun getAcaraById(jwt:String, id: String?, callback: (ApiResponse<Acara>?) -> Unit) {
            var acaraService : AcaraService = ApiClient.getAuthService(AcaraService::class.java, jwt)

            acaraService.getOneAcara(id).enqueue(object : Callback<ApiResponse<Acara>> {
                override fun onResponse(
                    call: Call<ApiResponse<Acara>>,
                    response: Response<ApiResponse<Acara>>
                ) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Acara>>, t: Throwable) {
                    callback(null)
                }
            })
        }


        fun deleteAcara(jwt:String, id: Int) {
            var acaraService : AcaraService = ApiClient.getAuthService(AcaraService::class.java, jwt)
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

        fun updateAcara(jwt:String, id: String?, studioname: String,desc: String,date: String,waktu: String, barangForms: List<String>,callback: (Acara?) -> Unit){
            val bawaan = barangForms.joinToString(", ")
            val AcaraData = AcaraData(AcaraBody(name = studioname, "", date, waktu, bawaan))
            var acaraService : AcaraService = ApiClient.getAuthService(AcaraService::class.java, jwt)

            acaraService.update(id,AcaraData).enqueue(object : Callback<Acara>{
                override fun onResponse(call: Call<Acara>, response: Response<Acara>): Unit =
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                        val acara = response.body()
                        callback(acara)
                        if (acara != null) {
                            val dateTimeMillis = NotifHelper.convertDateTimeToMillis(date, waktu)
                            println("Converted DateTimeMillis: $dateTimeMillis")
//                            NotifHelper.notifHelper(context, dateTimeMillis, studioname)
                        }else{
//                            Todo
                        }
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
    }
}