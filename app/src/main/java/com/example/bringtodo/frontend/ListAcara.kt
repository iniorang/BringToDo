package com.example.bringtodo.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bringtodo.Screen
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
                .padding(paddingValues = innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            repeat(2){
                CardEvent(navController)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .combinedClickable(enabled = true, onClick = {
                            navController.navigate(Screen.DetailAcara.route){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }})
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                        Text(
                            text = "Event Name",
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp
                        )
                        Text(
                            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start = 0.dp, top = 5.dp)
                        )

                    }

                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardEvent(navController: NavController){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .height(150.dp)
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
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            Text(
                text = "Event Name",
                textAlign = TextAlign.Center,
                fontSize = 30.sp
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 0.dp, top = 5.dp)
            )

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