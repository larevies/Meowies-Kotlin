package com.example.meowieskotlin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes (val route: String) {
    object WelcomeScreen : Routes("welcome_screen")
    object SignIn : Routes("sign_in")
    object SignUp : Routes("sign_up")
}