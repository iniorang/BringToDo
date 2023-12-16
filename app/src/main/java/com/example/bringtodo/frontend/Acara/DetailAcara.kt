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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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

@Composable
fun DetailAcara(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp, 30.dp)) {
        Text(fontSize = 30.sp,text = "Beli Kasur")
        Spacer(modifier = Modifier.height(20.dp))

        Text(fontSize = 20.sp,text = "Tanggal Acara")
        Spacer(modifier = Modifier.height(5.dp))
        Text(fontSize = 30.sp,text = "12-05-2024")

        Spacer(modifier = Modifier.height(20.dp))
        Text(fontSize = 20.sp,text = "Waktu")
        Spacer(modifier = Modifier.height(5.dp))
        Text(fontSize = 30.sp,text = "12:00")

        Spacer(modifier = Modifier.height(20.dp))
        Text(fontSize = 20.sp,text = "Yang Perlu dibawa")
        Box {
            Spacer(modifier = Modifier.height(5.dp))
            Column(modifier = Modifier
                .size(200.dp, 150.dp)
                .verticalScroll(rememberScrollState())) {
                repeat(20) {
                    Text(text = "Barang Bawaan $it")
                }
            }
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