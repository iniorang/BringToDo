package com.example.bringtodo.backend.controller

import android.content.Context
import androidx.work.WorkManager
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
        private var acaraService : AcaraService = ApiClient.getService(AcaraService::class.java)
        fun insertAcara(studioname: String, date: String, waktu:String, barangForms: List<String>, context: Context,  callback: (Acara?) -> Unit) {
            val bawaan = barangForms.joinToString(", ")
            val AcaraData = AcaraData(AcaraBody(name = studioname, "", date, waktu, bawaan))
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
                            NotifHelper.notifAcara1hari(context, dateTimeMillis, studioname, bawaan)
                            NotifHelper.notifAcara15menit(context, dateTimeMillis, studioname, bawaan)
                            NotifHelper.notifAcara1Jam(context, dateTimeMillis, studioname, bawaan)
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

        fun getAcaraById(id: String?, callback: (ApiResponse<Acara>?) -> Unit) {
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

        fun deleteWorkManagerJobs(name: String,context: Context) {
            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWorkByTag("acara_$name")
        }

        fun deleteAcara(id: Int, name: String, context: Context, callback: () -> Unit = {}) {
            acaraService.delete(id).enqueue(object : Callback<ApiResponse<Acara>> {
                override fun onResponse(
                    call: Call<ApiResponse<Acara>>,
                    response: Response<ApiResponse<Acara>>
                ) {
                    if (response.isSuccessful) {
                        println(response.body())
                        deleteWorkManagerJobs(name, context)
                        callback.invoke()
                    } else {
                        // Handle respons yang tidak berhasil
                        println("HTTP Request Failed: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<Acara>>,
                    t: Throwable
                ) {
                    // Handle kegagalan koneksi atau respons
                    println("HTTP Request Failure: ${t.message}")
                }
            })
        }


        fun updateAcara(id: String?,old : String, studioname: String,desc: String,date: String,waktu: String, barangForms: List<String>,context: Context,callback: (Acara?) -> Unit){
            val bawaan = barangForms.joinToString(", ")
            val AcaraData = AcaraData(AcaraBody(name = studioname, "", date, waktu, bawaan))
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
                            deleteWorkManagerJobs(old, context)
                            NotifHelper.notifAcara1hari(context, dateTimeMillis, studioname,bawaan)
                            NotifHelper.notifAcara15menit(context, dateTimeMillis, studioname, bawaan)
                            NotifHelper.notifAcara1Jam(context, dateTimeMillis, studioname, bawaan)
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