package com.example.bringtodo.frontend

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.ui.theme.BringToDoTheme
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
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

@Composable
fun EditEvent(navController: NavController, id: String?,) {
    var selectedDate by remember { mutableStateOf("") }
    var timeEvent by remember { mutableStateOf("") }
    var addNameEvent by remember {mutableStateOf("")}
    var eventDesc by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var isTimePickerVisible by remember { mutableStateOf(false) }

    Scaffold(
    ) {  innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),

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
                    IconButton(
                        onClick = {isDatePickerVisible = true},
                    ) {
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pilih Tanggal", modifier = Modifier.size(30.dp))
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
                    text = "Add Name Event"
                )
                TextField(
                    value = addNameEvent,
                    onValueChange = {newValue ->
                        addNameEvent = newValue

                    })
            }
            Button(
                modifier = Modifier
                    .padding(0.dp, 30.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    AcaraController.updateAcara(id,addNameEvent,eventDesc,selectedDate,timeEvent){
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
                        DatePickerCompose{ date ->
                            selectedDate = date
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


private fun convertMillisToTime(timeMillis: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}