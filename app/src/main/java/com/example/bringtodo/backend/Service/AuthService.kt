package com.example.bringtodo.backend.Service

import com.example.bringtodo.backend.model.Auth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginData (val identifier:String, val password: String)
data class RegisterData (val email:String, val username:String, val password: String)

interface AuthService {
    @POST("auth/local")
    fun login(@Body body: LoginData) : Call<Auth>
    @POST("auth/local/register")
    fun register(@Body body: RegisterData) : Call<Auth>
}