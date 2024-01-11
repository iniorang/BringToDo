package com.example.bringtodo.backend.controller

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.bringtodo.PreferencesManager
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.Service.AuthService
import com.example.bringtodo.backend.Service.LoginData
import com.example.bringtodo.backend.Service.RegisterData
import com.example.bringtodo.backend.model.Auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthController {
    companion object{
        private var authService : AuthService = ApiClient.getService(AuthService::class.java)
        fun login(context: Context,username : String, password: String, navController: NavController, prefMan: PreferencesManager, callback: (Auth?) -> Unit) {
            authService.login(LoginData(username, password)).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>): Unit =
                    if (response.isSuccessful) {
                        val respBody = response.body()!!
                        val jwt = respBody.jwt
                        val user = respBody.user!!
                        val id = user.id.toString()
                        prefMan.saveData("jwt", jwt)
                        prefMan.saveData("user", id)
                        prefMan.saveData("username", username)
                        prefMan.saveData("password", password)
                        navController.navigate(Screen.Acara.route)
                    } else {
                        Toast.makeText(context, "Username atau password salah!", Toast.LENGTH_LONG).show()
                        println("Unsuccesful login")
//                        navController.navigate(Screen.Auth.route)
                        callback(null)
                    }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }
        fun register(context: Context,email : String, username : String, password: String, navController: NavController, prefMan: PreferencesManager,  callback: (Auth?) -> Unit) {
            if (password.length < 6) {
                Toast.makeText(context, "Password harus memiliki minimal 8 karakter", Toast.LENGTH_LONG).show()
                callback(null)
                return
            }
            authService.register(RegisterData(email, username, password)).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>): Unit =
                    if (response.isSuccessful) {
                        val respBody = response.body()!!
                        val jwt = respBody.jwt
                        val user = respBody.user!!
                        val id = user.id.toString()
                        prefMan.saveData("jwt", jwt)
                        prefMan.saveData("user", id)
                        prefMan.saveData("username", username)
                        prefMan.saveData("password", password)
                        navController.navigate(Screen.Acara.route)
                    } else {
                        Toast.makeText(context, "Data Kurang Lengkap", Toast.LENGTH_LONG).show()
                        println("Unsuccesful register")
//                        navController.navigate(Screen.Auth.route)
                        callback(null)
                    }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }
    }

}