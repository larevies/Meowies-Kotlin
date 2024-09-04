package com.example.meowieskotlin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meowieskotlin.screens.Bookmarks
import com.example.meowieskotlin.screens.Change
import com.example.meowieskotlin.screens.Film
import com.example.meowieskotlin.screens.Person
import com.example.meowieskotlin.screens.Profile
import com.example.meowieskotlin.screens.ProfilePicture
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
        composable(route = Routes.Search.route) {
            Search(navController = navController)
        }
        composable(route = Routes.Bookmarks.route)
        {
            Bookmarks(navController = navController)
        }
        composable(route = Routes.Profile.route)
        {
            Profile(navController = navController)
        }
        composable(route = Routes.Change.route)
        {
            Change(navController = navController)
        }
        composable(route = Routes.Picture.route) {
            ProfilePicture(navController = navController)
        }
        composable(
            route = Routes.Film.route + "/{movie}",
            arguments = listOf(
                navArgument("movie") {
                    type = NavType.StringType
                    defaultValue = "{\"id\":36,\"name\":\"Kitty\",\"email\":\"meow@meow.ru\",\"birthday\":\"2024-07-06\",\"profilePicture\":7}"
                    nullable = true
                }
            )
        ) { entry ->
            Film(navController = navController,
                entry.arguments?.getString("movie")
            )
        }
        composable(
            route = Routes.Person.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ) {
                entry ->
            Person(navController = navController,
                entry.arguments?.getString("id")
            )
        }
    }
}
