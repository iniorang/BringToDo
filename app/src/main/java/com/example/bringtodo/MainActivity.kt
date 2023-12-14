package com.example.bringtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bringtodo.frontend.AddEvent
import com.example.bringtodo.frontend.EditEvent
import com.example.bringtodo.frontend.FormTambahBarang
import com.example.bringtodo.frontend.ListAcara
import com.example.bringtodo.frontend.ListBarang
import com.example.bringtodo.ui.theme.BringToDoTheme
data class IconForNav(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val routes : String = ""
)

sealed class Screen(val route: String){
    object Acara : Screen("ListAcara")
    object Barang : Screen("ListBarang")
    object TambahBarang : Screen("TambahBarang")
    object DetailAcara : Screen("DetailAcara")
    object AddEvent :Screen("AddEvent")
    object EditEvent :Screen("EditEvent/{id}"){
        const val PARAM_ID = "id"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                Greeting()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    // A surface container using the 'background' color from the theme
    val navController = rememberNavController()
    val items = listOf(
        IconForNav(
            title = "Acara",
            selectedIcon = Icons.Default.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
            routes = Screen.Acara.route
        ),
        IconForNav(
            title = "Barang",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            routes = Screen.Barang.route
        ),
    )
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    var titleCur by remember{ mutableStateOf(items[selectedItem].title) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                     TopAppBar(title = { Text(text = titleCur)})
            },
            bottomBar = {
            BottomBar(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    titleCur = items[index].title
                },
                navController
            )
        },) {innerPadding ->
            NavHost(navController = navController,
                startDestination = Screen.Acara.route, modifier = Modifier.padding(paddingValues = innerPadding)){
                composable(Screen.Acara.route){
                    ListAcara(navController)
                }
                composable(Screen.Barang.route){
                    ListBarang(navController)
                }
                composable(Screen.TambahBarang.route){
                    FormTambahBarang(navController)
                }
                composable(Screen.AddEvent.route){
                    AddEvent(navController)
                }
                composable(Screen.DetailAcara.route){
                    DetailAcara(navController)
                }
                composable(Screen.EditEvent.route) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString(Screen.EditEvent.PARAM_ID)
                    EditEvent(navController, id)
                }
            }
        }
    }
}

@Composable
fun AddButton(onClick: ()->Unit,navController: NavController){
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add,contentDescription = "Add")
    }
}


@Composable
fun BottomBar(
    items: List<IconForNav>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController : NavController
    ){
    NavigationBar {
        items.forEachIndexed{
                index,item -> NavigationBarItem(
            selected = selectedItem == index,
            onClick = {onItemSelected(index)
                      navController.navigate(item.routes){
                          popUpTo(navController.graph.findStartDestination().id){
                              saveState = true
                          }
                          launchSingleTop = true
                          restoreState = true
                      }},
            label = {Text(text = item.title)},
            icon = {
                Icon(
                    imageVector = if(index == selectedItem) {
                        item.selectedIcon}
                    else item.unselectedIcon,
                    contentDescription = item.title) })
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BringToDoTheme {
        Greeting()
    }
}