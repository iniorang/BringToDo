package com.example.bringtodo.frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.Service.BarangData
import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.BarangModel
import com.example.bringtodo.ui.theme.BringToDoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FormTambahBarang : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormTambahBarang()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTambahBarang(navController: NavController) {
    var namaBarang by remember{ mutableStateOf(TextFieldValue("")) }
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .padding(20.dp, 0.dp)
        ) {
            OutlinedTextField(
                value = namaBarang,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newText -> namaBarang = newText },
                label = { Text(text = ("Nama Barang")) })
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    var baseUrl = "http://10.0.2.2:1337/api/"
                    val retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(BarangService::class.java)
                    val call = retrofit.saveBarang(BarangData(namaBarang.text))
                    call.enqueue(object : Callback<BarangModel>{
                        override fun onResponse(
                            call: Call<BarangModel>,
                            response: Response<BarangModel>
                        ) {
//                            Log.d("ListBarang", "Response code: ${response.code()}")
                            if (response.code() == 200) {
                                navController.navigate(Screen.Barang.route)
                            } else if (response.code() == 400) {

                            }
                        }

                        override fun onFailure(call: Call<BarangModel>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })

                    navController.navigate(Screen.Barang.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Simpan")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    BringToDoTheme {
        FormTambahBarang()
    }
}