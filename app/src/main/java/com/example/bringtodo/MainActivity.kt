package com.example.bringtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.bringtodo.ui.theme.BringToDoTheme
data class IconForNav(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val route : String
)


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val items = listOf(
                    IconForNav(
                        title = "Acara",
                        selectedIcon = Icons.Default.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                        route = "ListAcara"
                    ),
                    IconForNav(
                        title = "Barang",
                        selectedIcon = Icons.Filled.Edit,
                        unselectedIcon = Icons.Outlined.Edit,
                        route = "ListBarang"
                    ),
                )
                var selectedItem by rememberSaveable {
                    mutableIntStateOf(0)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {

                    },bottomBar = {
                        NavigationBar {
                            items.forEachIndexed{
                                index,item -> NavigationBarItem(
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
//                                    navController.navigate(item.route)
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
                    }, floatingActionButton = {
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Add,contentDescription = "Add")
                        }
                    }) {innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            repeat(30){
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    ),
                                    modifier = Modifier
                                        .height(150.dp)
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp)
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