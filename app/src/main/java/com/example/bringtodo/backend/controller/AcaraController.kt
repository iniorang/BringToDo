package com.example.bringtodo.backend.controller

import android.content.Context
import android.widget.Toast
import androidx.work.WorkManager
import com.example.bringtodo.PreferencesManager
import com.example.bringtodo.backend.NotifHelper
import com.example.bringtodo.backend.Service.AcaraBody
import com.example.bringtodo.backend.Service.AcaraData
import com.example.bringtodo.backend.Service.AcaraService
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.backend.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AcaraController {
    companion object{
        private var acaraService : AcaraService = ApiClient.getService(AcaraService::class.java)
        fun createnotif(context: Context,date: Long,name: String,item:String){
            NotifHelper.notifAcara(context, date,0, name, item)
            NotifHelper.notifAcara(context, date,24 * 60 * 60 * 1000, name, item)
            NotifHelper.notifAcara(context, date,60 * 30 * 60 * 1000, name, item)
        }
        fun insertAcara(name: String, date: String, waktu:String, barangForms: List<String>,prefman: PreferencesManager, context: Context,  callback: (Acara?) -> Unit) {
            val bawaan = barangForms.joinToString(", ")
            val pembuat = prefman.getData("username")
            val AcaraData = AcaraData(AcaraBody(name, pembuat, date, waktu, bawaan))
            if (name.isEmpty() || date.isEmpty() || waktu.isEmpty()) {
                Toast.makeText(context, "Harap lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return
            }
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
//                            NotifHelper.notifAcara1hari(context, dateTimeMillis, studioname, bawaan)
//                            NotifHelper.notifAcara15menit(context, dateTimeMillis, studioname, bawaan)
//                            NotifHelper.notifAcara1Jam(context, dateTimeMillis, studioname, bawaan)
                            createnotif(context,dateTimeMillis,name,bawaan)
                            Toast.makeText(context, "Acara $name sukses dibuat", Toast.LENGTH_LONG).show()
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

        fun getAcaras(
            preferencesManager: PreferencesManager,
            callback: (ApiResponse<List<Acara>>?) -> Unit
        ) {
            val namaPembuat = preferencesManager.getData("username")
            val filters = createFilters(namaPembuat)

            acaraService.getall("createdAt:desc",filters).enqueue(object : Callback<ApiResponse<List<Acara>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<Acara>>>,
                    response: Response<ApiResponse<List<Acara>>>
                ) {
                    if (response.isSuccessful) {
                        println(response.body())
                        callback(response.body())
                    } else {
                        // Handle kesalahan respons
                        println("Unsuccessful request")
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<Acara>>>, t: Throwable) {
                    // Handle kesalahan jaringan atau permintaan
                    println(t)
                    callback(null)
                }
            })
        }

        private fun createFilters(pembuat: String): Map<String, String> {
            return mapOf("filters[pembuat][\$eq]" to pembuat)
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

        fun updateAcara(id: String?,old : String, name: String,date: String,waktu: String, barangForms: List<String>,prefman: PreferencesManager,context: Context,callback: (Acara?) -> Unit){
            val bawaan = barangForms.joinToString(", ")
            val pembuat = prefman.getData("username")
            val AcaraData = AcaraData(AcaraBody(name, pembuat, date, waktu, bawaan))
            if (name.isEmpty() || date.isEmpty() || waktu.isEmpty()) {
                Toast.makeText(context, "Harap lengkapi semua data!", Toast.LENGTH_LONG).show()
                return
            }
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
//                            NotifHelper.notifAcara1hari(context, dateTimeMillis, studioname,bawaan)
//                            NotifHelper.notifAcara15menit(context, dateTimeMillis, studioname, bawaan)
//                            NotifHelper.notifAcara1Jam(context, dateTimeMillis, studioname, bawaan)
                            createnotif(context, dateTimeMillis, name, bawaan)
                            Toast.makeText(context, "Acara berhasil dibuat", Toast.LENGTH_SHORT).show()
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