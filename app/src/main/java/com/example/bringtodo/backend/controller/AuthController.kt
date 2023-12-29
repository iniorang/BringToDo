package com.example.bringtodo.backend.controller

import androidx.compose.runtime.remember
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
    companion object {
        private var authService : AuthService = ApiClient.getService(AuthService::class.java)

        fun login(username : String, password: String, navController: NavController, prefMan: PreferencesManager, callback: (Auth?) -> Unit) {
            authService.login(LoginData(username, password)).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>): Unit =
                    if (response.isSuccessful) {
                        val respBody = response.body()!!
                        val jwt = respBody.jwt
                        val respUser = respBody.user!!
                        val userID = respUser.id.toString()
                        prefMan.saveData("jwt", jwt)
                        prefMan.saveData("userID", userID)
                        prefMan.saveData("username", username)
                        prefMan.saveData("password", password)
                        println("Succesful login")
                        navController.navigate(Screen.Acara.route)
                    } else {
                        println("Unsuccesful login")
                        navController.navigate("auth-page")
                        callback(null)
                    }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }
        fun register(email : String, username : String, password: String, navController: NavController, prefMan: PreferencesManager,  callback: (Auth?) -> Unit) {
            authService.register(RegisterData(email, username, password)).enqueue(object : Callback<Auth> {
                override fun onResponse(call: Call<Auth>, response: Response<Auth>): Unit =
                    if (response.isSuccessful) {
                        val respBody = response.body()!!
                        val jwt = respBody.jwt
                        val respUser = respBody.user!!
                        val userID = respUser.id.toString()
                        prefMan.saveData("jwt", jwt)
                        prefMan.saveData("userID", userID)
                        prefMan.saveData("username", username)
                        prefMan.saveData("password", password)
                        println("Succesful register")
                        navController.navigate(Screen.Acara.route)
                    } else {
                        println("Unsuccesful register")
                        navController.navigate("auth-page")
                        callback(null)
                    }

                override fun onFailure(call: Call<Auth>, t: Throwable) {
//                    println(t)
                    callback(null)
                }
            })
        }
        fun logout (navController: NavController, prefMan: PreferencesManager) {
            navController.navigate("auth-page")
            prefMan.saveData("jwt","")
            prefMan.saveData("userID","")
            prefMan.saveData("username","")
            prefMan.saveData("password","")
        }
    }
}