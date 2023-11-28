package com.example.bringtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Text(
        text = "Ini Detail Acara"
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    BringToDoTheme {
        DetailAcara()
    }
}