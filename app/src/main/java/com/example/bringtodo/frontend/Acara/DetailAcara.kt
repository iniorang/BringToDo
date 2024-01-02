package com.example.bringtodo.frontend.Acara

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bringtodo.Screen
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
fun DetailAcara(navController: NavController, id: String?,context: Context) {
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
            ),navigationIcon = {
                IconButton(onClick = { navController.navigate(Screen.Acara.route) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali ke daftar"
                    )
                }
            }, actions = {
                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Tampilkan Dropdown")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    acaraDetails?.let { acara ->
                        DropdownMenuItem(text = { Text(text = "Edit") }, onClick = {
                            navController.navigate("${Screen.EditEvent.route}/${acara.id}")
                            expanded = false
                        })
                        DropdownMenuItem(text = { Text(text = "Delete") }, onClick = {
                            DeleteConfirmation(navController,context, acara.id, acara.attributes.name)
                            expanded = false
                        })
                    }
                }
            }
        )
    }) { innerPadding ->
        acaraDetails?.let { acara ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(10.dp, 20.dp)
            ) {
                Text(fontSize = 30.sp, text = acara.attributes.name)
                Spacer(modifier = Modifier.height(20.dp))

                Text(fontSize = 20.sp, text = "Tanggal Acara")
                Spacer(modifier = Modifier.height(5.dp))
                Text(fontSize = 30.sp, text = acara.attributes.date)

                Spacer(modifier = Modifier.height(20.dp))
                Text(fontSize = 20.sp, text = "Waktu")
                Spacer(modifier = Modifier.height(5.dp))
                Text(fontSize = 30.sp, text = convertTimeFormat(acara.attributes.time))

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