package com.example.bringtodo.frontend.Acara

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.bringtodo.Screen
import com.example.bringtodo.backend.controller.AcaraController
import com.example.bringtodo.ui.theme.BringToDoTheme
import java.text.SimpleDateFormat
import java.util.Calendar
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
fun AddEvent(navController: NavController,context: Context) {
    var selectedDate by remember { mutableStateOf("") }
    var timeEvent by remember { mutableStateOf("") }
    var addNameEvent by remember {mutableStateOf("")}
    var eventDesc by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    var isBarangRemembered by remember { mutableStateOf(false) }
    var barangForms by remember { mutableStateOf(listOf("")) }
    val context = LocalContext.current

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

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Tambah Acara") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Screen.Acara.route) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali ke daftar"
                    )
                }
            }
        )
    }) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
            ) {
            Column (
                modifier = Modifier
                    .padding(30.dp,10.dp,30.dp,0.dp),
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ){
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = selectedDate,
                        onValueChange = { newValue ->
                            selectedDate = newValue
                        },
                        label = { Text("Select Date") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        }
                    )
                    Button(
                        onClick = {isDatePickerVisible = true},
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
                    ) {
                        Text("Date")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f),
                        value = timeEvent,
                        onValueChange = { newValue ->
                            timeEvent = newValue
                        },
                        label = { Text("Select Time") }
                    )
                    IconButton(onClick = { isTimePickerVisible = true }) {
                        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Pilih Waktu", modifier = Modifier.size(30.dp))
                    }
                }

                OutlinedTextField(
                    value = addNameEvent,
                    onValueChange = {newValue ->
                        addNameEvent = newValue
                    },
                    label = { Text("Event Name") },

                )
                Text(modifier = Modifier.padding(5.dp,20.dp,0.dp,0.dp), text = "Tambah Barang")
                barangForms.forEachIndexed { index, barangForm ->
                    BarangFormInput(
                        barangForm = barangForm,
                        onValueChange = { newValue ->
                            barangForms = barangForms.toMutableList().also {
                                it[index] = newValue
                            }
                        },
                        onRemoveClicked = { removeBarangForm(index) },
                    )
                }
                TextButton(
                    onClick = { addBarangForm() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                ) {
                    Icon(
                        imageVector  = Icons.Default.AddCircle,
                        contentDescription = null
                    )
                    Text("Tambah Barang")
                }
            }
            Button(
                modifier = Modifier
                    .padding(30.dp, 30.dp,30.dp,0.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),

                onClick = {
                    AcaraController.insertAcara(addNameEvent,selectedDate,"$timeEvent:00.000",barangForms,context){
                            acara ->  if (acara != null) {
                        navController.navigate(Screen.Acara.route)
                    }}
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
                Text("Save")
            }

            if (isDatePickerVisible) {
                AlertDialog(
                    onDismissRequest = { isDatePickerVisible = false },
                    text = {
                        DatePickerCompose { date ->
                            selectedDate = date
                            isDatePickerVisible = false
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

@Composable
fun BarangFormInput(barangForm: String, onValueChange: (String) -> Unit, onRemoveClicked: () -> Unit) {
    var showDateTime by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = barangForm,
                onValueChange = { newValue ->
                    onValueChange(newValue)
                },
                label = { Text("Nama Barang") },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .weight(1f)
            )
            IconButton(onClick = onRemoveClicked) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus Barang")
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
                val todayCalendar = Calendar.getInstance()
                val todayMillis = todayCalendar.timeInMillis
                return utcTimeMillis >= todayMillis
            }
        }
    )

    val selectedDate = datePickerState.selectedDateMillis

    selectedDate?.let { date ->
        val formattedDate = convertMillisToDate(date)
        onDateSelected(formattedDate)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(state = datePickerState, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(24.dp))
        // Display selected date
        Text(
            text = "Selected Date: ${selectedDate?.let { convertMillisToDate(it) } ?: "Not selected"}",
            color = Color.Red
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerCompose(onTimeSelected: (String) -> Unit) {
    val timePickerState = rememberTimePickerState()
    val selectedTimeMillis: Long = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, timePickerState.hour)
        set(Calendar.MINUTE, timePickerState.minute)
    }.timeInMillis

    selectedTimeMillis?.let { time ->
        val formattedTime = convertMillisToTime(time)
        onTimeSelected(formattedTime)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TimePicker(state = timePickerState)
        Spacer(modifier = Modifier.height(32.dp))
        Text(
                text = "Selected Time: ${selectedTimeMillis?.let { convertMillisToTime(it) }}",
                color = Color.Red
        )
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