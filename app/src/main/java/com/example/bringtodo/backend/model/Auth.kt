package com.example.bringtodo.backend.model

import com.google.gson.annotations.SerializedName
class Auth {
    var jwt : String = ""
    var user: User? = null
}