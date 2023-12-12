package com.example.bringtodo.frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.Service.BarangService
import com.example.bringtodo.backend.model.BarangModel
import com.example.bringtodo.ui.theme.BringToDoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListBarang : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListBarang()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListBarang(navController: NavController) {
    var listBarang = remember{ mutableStateListOf<BarangModel>()}
    var baseUrl = "http://10.0.2.2:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BarangService::class.java)
    val call = retrofit.getBarang()
    call.enqueue(object : Callback<List<BarangModel>> {
        override fun onResponse(
            call: Call<List<BarangModel>>,
            response: Response<List<BarangModel>>
        ) {
            Log.d("ListBarang", "Response code: ${response.code()}")
            if (response.code() == 200) {
                Log.d("ListBarang", "Response code: ${response.code()}")
                listBarang.clear()
                response.body()?.forEach{ barangRes ->
                    listBarang.add(barangRes)
                }
            } else if (response.code() == 400) {
            }
        }
        override fun onFailure(call: Call<List<BarangModel>>, t: Throwable) {
            print(t.message)
        }

    })
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.TambahBarang.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
//                .verticalScroll(lazyListState)
        ) {
            listBarang.forEach{
                    barangRes -> item {
                Text(text = barangRes.namaBarang)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BringToDoTheme {
        ListBarang()
    }
}