package com.example.meowieskotlin

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.WelcomeScreen.route) {
        composable(route = Routes.WelcomeScreen.route) {
            Welcome(navController = navController)
        }
        composable(route = Routes.SignIn.route)
        {
            SignIn(navController = navController)
        }
        composable(route = Routes.SignUp.route)
        {
            Slide(navController = navController)
        }
    }
}