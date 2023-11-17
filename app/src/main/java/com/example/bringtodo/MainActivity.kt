package com.example.bringtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.bringtodo.ui.theme.BringToDoTheme
data class IconForNav(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                val items = listOf(
                    IconForNav(
                        title = "Acara",
                        selectedIcon = Icons.Default.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange
                    ),
                    IconForNav(
                        title = "Barang",
                        selectedIcon = Icons.Filled.Edit,
                        unselectedIcon = Icons.Outlined.Edit
                    ),
                )
                var selectedItem by rememberSaveable {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = {
                        NavigationBar {
                            items.forEachIndexed{
                                index,item -> NavigationBarItem(
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                }, label = {
                                        Text(text = item.title)
                                },
                                icon = {
                                    Icon(
                                        imageVector = if(index == selectedItem) {
                                            item.selectedIcon}
                                    else item.unselectedIcon,
                                        contentDescription = item.title) })
                            }
                        }
                    }) {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BringToDoTheme {
        Greeting("Android")
    }
}