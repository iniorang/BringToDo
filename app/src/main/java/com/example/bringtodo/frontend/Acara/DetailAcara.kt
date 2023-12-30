package com.example.bringtodo.frontend.Acara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.ui.theme.BringToDoTheme

class DetailAcara : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailAcara()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAcara(navController: NavController, id: String?) {
    val (acaraDetails, setAcaraDetails) = remember { mutableStateOf<Acara?>(null) }

    LaunchedEffect(key1 = id) {
        AcaraController.getAcaraById(id) { response ->
            setAcaraDetails(response?.data)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { acaraDetails?.attributes?.let { Text(text = it.name) } },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->
        acaraDetails?.let { acara ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(fontSize = 30.sp, text = acara.attributes.name)
                Spacer(modifier = Modifier.height(20.dp))

                Text(fontSize = 20.sp, text = "Tanggal Acara")
                Spacer(modifier = Modifier.height(5.dp))
                Text(fontSize = 30.sp, text = acara.attributes.date)

                Spacer(modifier = Modifier.height(20.dp))
                Text(fontSize = 20.sp, text = "Waktu")
                Spacer(modifier = Modifier.height(5.dp))
                Text(fontSize = 30.sp, text = acara.attributes.time)

                Spacer(modifier = Modifier.height(20.dp))
                Text(fontSize = 20.sp, text = "Yang Perlu dibawa")
                Box {
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        modifier = Modifier
                            .size(200.dp, 150.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = acara.attributes.bawaan.split(", ")
                                .joinToString(separator = "\n") { it.trim() }
                        )
                    }
                }
            }
        } ?: run {
            Text(text = "Loading")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    BringToDoTheme {
        DetailAcara()
    }
}