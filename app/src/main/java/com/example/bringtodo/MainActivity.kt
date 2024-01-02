package com.example.bringtodo

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bringtodo.frontend.Acara.AddEvent
import com.example.bringtodo.frontend.Acara.DetailAcara
import com.example.bringtodo.frontend.Acara.EditEvent
import com.example.bringtodo.frontend.Acara.ListAcara
import com.example.bringtodo.frontend.AuthPage
import com.example.bringtodo.frontend.Barang.FormTambahBarang
import com.example.bringtodo.frontend.Barang.ListBarang
import com.example.bringtodo.ui.theme.BringToDoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

data class IconForNav(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val routes : String = ""
)

sealed class Screen(val route: String){
    object Auth : Screen("Auth")
    object Acara : Screen("ListAcara")
    object Barang : Screen("ListBarang")
    object TambahBarang : Screen("TambahBarang")
    object DetailAcara : Screen("DetailAcara")
    object AddEvent :Screen("AddEvent")
    object EditEvent :Screen("EditEvent")
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BringToDoTheme {
                val postNotificationPermission=
                    rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                LaunchedEffect(key1 = true ){
                    if(!postNotificationPermission.status.isGranted){
                        postNotificationPermission.launchPermissionRequest()
                    }
                }
                Greeting(this)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(context: Context) {
    // A surface container using the 'background' color from the theme
    val navController = rememberNavController()
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
    var jwt = sharedPreferences.getString("jwt","")
    Log.d("BringToDo","JWT $jwt")
    var startDestination : String = if(jwt.equals("")){ Screen.Auth.route }else{ Screen.Acara.route }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController = navController,
            startDestination = startDestination){
            composable(Screen.Auth.route){
                AuthPage(navController)
            }
            composable(Screen.Acara.route){
                ListAcara(navController,context)
            }
            composable(Screen.Barang.route){
                ListBarang(navController)
            }
            composable(Screen.TambahBarang.route){
                FormTambahBarang(navController)
            }
            composable(Screen.AddEvent.route){
                AddEvent(navController,context)
            }
            composable(Screen.DetailAcara.route + "/{id}") { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val id = arguments.getString("id")
                DetailAcara(navController, id,context)
            }
            composable(Screen.EditEvent.route + "/{id}") { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val id = arguments.getString("id")
                EditEvent(navController,id)
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
//        Greeting()
    }
}