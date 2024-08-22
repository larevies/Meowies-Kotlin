package com.example.meowieskotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meowieskotlin.screens.Bookmarks
import com.example.meowieskotlin.screens.Profile
import com.example.meowieskotlin.screens.Search
import com.example.meowieskotlin.screens.SignIn
import com.example.meowieskotlin.screens.SignUp
import com.example.meowieskotlin.screens.Welcome

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Welcome.route) {
        composable(route = Routes.Welcome.route) {
            Welcome(navController = navController)
        }
        composable(route = Routes.SignIn.route)
        {
            SignIn(navController = navController)
        }
        composable(route = Routes.SignUp.route)
        {
            SignUp(navController = navController)
        }
        composable(
            route = Routes.Search.route + "/{user}",
            arguments = listOf(
                navArgument("user") {
                    type = NavType.StringType
                    defaultValue = "Kitty"
                    nullable = true
                }
            )
        ) { entry ->
            Search(navController = navController,
                user = entry.arguments?.getString("user"))
        }
        composable(
            route = Routes.Bookmarks.route + "/{user}",
            arguments = listOf(
                navArgument("user") {
                    type = NavType.StringType
                    defaultValue = "Kitty"
                    nullable = true
                }
            )
        )
        {entry ->
            Bookmarks(navController = navController,
                entry.arguments?.getString("user"))
        }
        composable(
            route = Routes.Profile.route + "/{user}",
            arguments = listOf(
                navArgument("user") {
                    type = NavType.StringType
                    defaultValue = "Kitty"
                    nullable = true
                }
            )
        )
        {entry ->
            Profile(navController = navController,
                entry.arguments?.getString("user"))
        }
    }
}
