package com.example.bringtodo.frontend.Barang

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.BarangController
import com.example.bringtodo.backend.model.Barang
import com.example.bringtodo.ui.theme.BringToDoTheme

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

@Composable
fun ListBarang(navController: NavController) {
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
    var jwt = sharedPreferences.getString("jwt", "")

    var barangs by remember {mutableStateOf<List<Barang>?>(null)}
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
            if (jwt != null) {
                BarangController.getBarangs(jwt) {response ->
                    barangs = response?.data
                }
            }
            barangs?.forEach{ barang -> item{
                Text(text = barang.attr.name)
                Button(onClick = {
                    if (jwt != null) {
                        BarangController.deleteBarangs(jwt, barang.id)
                    }
                    navController.navigate(Screen.Barang.route)
                }) {
                    Text(text = "Hapus")
                }
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