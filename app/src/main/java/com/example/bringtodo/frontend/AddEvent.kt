package com.example.bringtodo.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import com.example.bringtodo.ui.theme.BringToDoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddEvent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddEvent()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvent(navController: NavController) {
    var selectedDate by remember { mutableStateOf("") }
    var addNameEvent by remember {mutableStateOf("")}
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Add Event") })
        }
    ) { innerPadding ->
        Column(
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
                    Button(
                        onClick = {isDatePickerVisible = true},
                    ) {
                        Text("Date")
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
                modifier = Modifier.padding(0.dp,30.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {},
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCompose(onDateSelected: (String) -> Unit) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return true
            }
        }
    )

    val selectedDate = datePickerState.selectedDateMillis

    selectedDate?.let { date ->
        val formattedDate = convertMillisToDate(date)
        onDateSelected(formattedDate)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(state = datePickerState)
        Spacer(modifier = Modifier.height(32.dp))
        // Display selected date
        Text(
            text = "Selected Date: ${selectedDate?.let { convertMillisToDate(it) } ?: "Not selected"}",
            color = Color.Red
        )
    }
}
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}