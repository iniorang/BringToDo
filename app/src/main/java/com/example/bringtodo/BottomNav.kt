package com.example.bringtodo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNav(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Acara: BottomNav(
        route = "ListAcara",
        title = "Acara",
        icon = Icons.Default.DateRange
    )
    object Barang: BottomNav(
        route = "ListBarang",
        title = "Barang",
        icon = Icons.Default.Create
    )
}
