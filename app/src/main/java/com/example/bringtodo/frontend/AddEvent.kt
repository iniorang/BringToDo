package com.example.bringtodo.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun AddEvent (navController: NavController){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Edit User") })
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                Button(onClick = { isDatePickerVisible = true }) {
                    Text("Filled")
                }
                if (isDatePickerVisible) {
                    DatePickerCompose { date ->
                        selectedDate = date
                        isDatePickerVisible = false // Hide the date picker after selection if needed
                    }
                }
                TextField(
                    value = selectedDate,
                    onValueChange = { newValue ->
                        selectedDate = newValue
                    }
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