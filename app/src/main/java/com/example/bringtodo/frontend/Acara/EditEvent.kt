package com.example.bringtodo.frontend.Acara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.backend.controller.AcaraController.Companion.getAcaraById
import com.example.bringtodo.backend.model.Acara
import com.example.bringtodo.ui.theme.BringToDoTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


class EditEvent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EditEvent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEvent(navController: NavController,id : String?) {
    var selectedDate by remember { mutableStateOf("") }
    var timeEvent by remember { mutableStateOf("") }
    var addNameEvent by remember {mutableStateOf("")}
    var eventDesc by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    var barangForms by remember { mutableStateOf(listOf("")) }
    val context = LocalContext.current
    val (acaraDetails, setAcaraDetails) = remember { mutableStateOf<Acara?>(null) }
    var oldName by remember {mutableStateOf("")}

    LaunchedEffect(key1 = id) {
        AcaraController.getAcaraById(id) { response ->
            setAcaraDetails(response?.data)
        }
    }

    fun addBarangForm() {
        barangForms = barangForms + ""
    }

    fun removeBarangForm(index: Int) {
        if (barangForms.size > 1) {
            barangForms = barangForms.toMutableList().also {
                it.removeAt(index)
            }
        }
    }

    LaunchedEffect(id) {
        if (!id.isNullOrBlank()) {
            getAcaraById(id) { response ->
                response?.data?.let { acara ->
                    selectedDate = acara.attributes.date
                    timeEvent = convertTimeFormat(acara.attributes.time)
                    addNameEvent = acara.attributes.name
                    barangForms = acara.attributes.bawaan.split(",").map { it.trim() }
                        .filter { it.isNotBlank() }.toMutableList()
                    oldName = acara.attributes.name
                }
            }
        }
    }
    Scaffold() {  innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column (
                modifier = Modifier
                    .padding(30.dp,10.dp,30.dp,0.dp),
            ){
                Text(text = "Select Date")
                Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.End){
                    TextField(
                        value = selectedDate,
                        onValueChange = { newValue ->
                            selectedDate = newValue
                        },

                        )
                    Button(
                        onClick = {isDatePickerVisible = true},
                    ) {
                        Text("Date")
                    }
                }
            }
            Column(modifier = Modifier.padding(30.dp, 10.dp, 30.dp, 0.dp)) {
                Text(text = "Select Time")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    TextField(
                        value = timeEvent,
                        onValueChange = { newValue ->
                            timeEvent = newValue
                        },
                    )
                    IconButton(onClick = { isTimePickerVisible = true }) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Pilih Waktu", modifier = Modifier.size(30.dp))
                    }
                }
            }
            Column(
                modifier = Modifier.padding(30.dp,10.dp,30.dp,0.dp)
            ){
                Text(
                    text = "Nama Event"
                )
                TextField(
                    value = addNameEvent,
                    onValueChange = {newValue ->
                        addNameEvent = newValue

                    })
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                barangForms.forEachIndexed { index, barangForm ->
                    BarangFormInput(
//                        index = index,
                        barangForm = barangForm,
                        onValueChange = { newValue ->
                            barangForms = barangForms.toMutableList().apply {
                                this[index] = newValue
                            }
                        },
                        onRemoveClicked = {
                            removeBarangForm(index)
                        }
                    )
                }
                Button(
                    onClick = { addBarangForm() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Tambah Barang")
                }
            }


            Button(
                modifier = Modifier
                    .padding(0.dp, 30.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    AcaraController.updateAcara(id,oldName,addNameEvent,"",selectedDate,"$timeEvent:00.000",barangForms,context){
                            acara ->  if (acara != null) {
                        navController.navigate(Screen.Acara.route)
                    }
                    }
                },
            ) {
                Text("Save")
            }

            if (isDatePickerVisible) {
                AlertDialog(
                    onDismissRequest = { isDatePickerVisible = false },
                    title = { Text("Select Date") },
                    text = {
                        DatePickerCompose { date ->
                            selectedDate = date
                            isDatePickerVisible = false // Hide the date picker after selection if needed
                        }
                    },
                    confirmButton = {
                        Button(onClick = { isDatePickerVisible = false }) {
                            Text("Close")
                        }
                    },
                    dismissButton = null,
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                )
            }

            if (isTimePickerVisible) {
                AlertDialog(
                    onDismissRequest = { isTimePickerVisible = false },
                    title = { Text("Select Time") },
                    text = {
                        TimePickerCompose { time ->
                            timeEvent = time
                        }
                    },
                    confirmButton = {
                        Button(onClick = { isTimePickerVisible = false }) {
                            Text("Close")
                        }
                    },
                    dismissButton = null,
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                )
            }
        }
    }
}

fun convertTimeFormat(inputTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    return try {
        val parsedTime = LocalTime.parse(inputTime, inputFormatter)
        parsedTime.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        // Handle parsing exception
        ""
    }
}


