package com.example.meowieskotlin.design

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.ui.theme.fontMedium


@Composable
fun bottomNavigation(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Row (
            modifier = Modifier
                .height(60.dp)
                .border(2.dp, Color.White)
                .fillMaxWidth()
                .background(fontMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            navigationButton(
                navController = navController,
                route = Routes.Search.route,
                image = R.drawable.search,
                text = "Search",
                size = 35.dp)
            Spacer(modifier = Modifier.padding(20.dp))
            navigationButton(
                navController = navController,
                route = Routes.Bookmarks.route,
                image = R.drawable.heart,
                text = "Favorites",
                size = 35.dp)
            Spacer(modifier = Modifier.padding(20.dp))
            navigationButton(
                navController = navController,
                route = Routes.Profile.route,
                image = R.drawable.cat,
                text = "Profile",
                size = 50.dp)
        }
    }
}
