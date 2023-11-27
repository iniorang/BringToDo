package com.example.bringtodo.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.bringtodo.ui.theme.BringToDoTheme

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
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Tambah Barang") })
    }) {innerPadding ->
        var text by remember{ mutableStateOf("") }
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()) {
            OutlinedTextField(value = text, onValueChange = {text = it}, label = { Text(text = ("Nama Barang"))})
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