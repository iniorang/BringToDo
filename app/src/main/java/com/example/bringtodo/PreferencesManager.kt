package com.example.bringtodo

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager (context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String){
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String) : String{
        return sharedPreferences.getString(key, "") ?: ""
    }
}