package com.example.bringtodo.frontend.Acara

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bringtodo.PreferencesManager
import com.example.bringtodo.R
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.backend.controller.AcaraController.Companion.deleteWorkManagerJobs
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAcara(navController: NavController, context: Context) {
    var acaras by remember { mutableStateOf<List<Acara>?>(null) }
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val preferencesManager = remember { PreferencesManager(context = context) }
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "BringToDo",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { LogoutConfirmation(navController,context,preferencesManager) }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Localized description"
                        )
                    }
                },
//                scrollBehavior = scrollBehavior
            )
        },floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddEvent.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(0.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            AcaraController.getAcaras(preferencesManager) { response ->
                acaras = response?.data
            }
            LazyColumn {
                acaras?.forEach { acara ->
                    item {
                        CardEvent(acara, navController, context)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardEvent(acara: Acara, navController: NavController, context: Context) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
            .combinedClickable(enabled = true, onClick = {
                Log.d("CardEvent", "Acara ID: ${acara.id}")
                navController.navigate("${Screen.DetailAcara.route}/${acara.id}") {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }, onLongClick = {
                expanded = true
            })
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
            Text(
                text = acara.attributes.name,
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
            Text(
                text = acara.attributes.date + "/" + convertTimeFormat(acara.attributes.time),
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 0.dp, top = 5.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Edit") },
                    onClick = {
                    navController.navigate("${Screen.EditEvent.route}/${acara.id}")
                    expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = {
    //                    AcaraController.deleteAcara(acara.id,acara.attributes.name,context)
                        DeleteConfirmation(navController,context, acara.id, acara.attributes.name)
                        navController.navigate(Screen.Acara.route)
                        expanded = false
                     },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                )
            }

        }

    }
}

fun DeleteConfirmation(navController: NavController,context: Context, id: Int, name: String) {
    val alertDialogBuilder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_BringToDo))

    alertDialogBuilder.setTitle("Konfirmasi Hapus")
    alertDialogBuilder.setMessage("Apakah Anda yakin ingin menghapus acara '$name'?")

    alertDialogBuilder.setPositiveButton("Ya") { dialog, _ ->
        AcaraController.deleteAcara(id, name, context){
            dialog.dismiss()
            navController.navigate(Screen.Acara.route)
        }
        dialog.dismiss()
    }

    alertDialogBuilder.setNegativeButton("Batal") { dialog, _ ->
        dialog.dismiss()
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
}


fun LogoutConfirmation(navController: NavController,context: Context, preferencesManager: PreferencesManager) {
    val alertDialogBuilder =
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.Theme_BringToDo))

    alertDialogBuilder.setTitle("Konfirmasi Log-out")
    alertDialogBuilder.setMessage("Apakah Anda yakin ingin untuk keluar?")

    alertDialogBuilder.setPositiveButton("Ya") { dialog, _ ->
        preferencesManager.saveData("jwt","")
        dialog.dismiss()
        navController.navigate(Screen.Auth.route)
    }

    alertDialogBuilder.setNegativeButton("Batal") { dialog, _ ->
        dialog.dismiss()
    }

    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview3() {
    BringToDoTheme {
        ListAcara()
    }
}