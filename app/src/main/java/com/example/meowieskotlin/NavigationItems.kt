package com.example.meowieskotlin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItems (val route: String, val icon: ImageVector, val label: String) {
    object Profile : NavigationItems("profile", Icons.Default.Person, "Profile")
    object Search : NavigationItems("search", Icons.Default.Search, "Search")
    object Bookmarks : NavigationItems("bookmarks", Icons.Default.FavoriteBorder, "Bookmarks")
}