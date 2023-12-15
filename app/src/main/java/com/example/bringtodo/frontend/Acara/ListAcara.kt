package com.example.bringtodo.frontend.Acara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.ui.theme.BringToDoTheme

class ListAcara : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListAcara()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListAcara(navController: NavController) {
    var acaras by remember { mutableStateOf<List<Acara>?>(null) }

    Scaffold(floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Screen.AddEvent.route){
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    } }) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }}) {innerPadding->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            AcaraController.getAcaras { response ->
                acaras = response?.data
            }
            LazyColumn{
                acaras?.forEach { acara -> item {
                    CardEvent(acara, navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }}
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardEvent(acara:Acara, navController: NavController){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .combinedClickable(enabled = true, onClick = {
                navController.navigate(Screen.DetailAcara.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
    ) {
        Column(modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)) {
            Text(
                text = acara.attributes.name,
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            Text(
                text = acara.attributes.desc,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 0.dp, top = 5.dp)
            )
            Text(
                text = acara.attributes.date+"/"+acara.attributes.time,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 0.dp, top = 5.dp)
            )
            Row {
                Button(onClick = {
                    navController.navigate(Screen.Acara.route)
                }) {
                    Text(text = "Edit")
                }
                Button(onClick = {
                    AcaraController.deleteAcara(acara.id)
                    navController.navigate(Screen.Acara.route)
                }) {
                    Text(text = "Delete")
                }
            }
        }

    }
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview3() {
    BringToDoTheme {
        ListAcara()
    }
}