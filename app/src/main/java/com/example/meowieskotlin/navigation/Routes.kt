package com.example.meowieskotlin.navigation

sealed class Routes (val route: String) {
    object Welcome : Routes("welcome_screen")
    object SignIn : Routes("sign_in")
    object SignUp : Routes("sign_up")
    object Search : Routes("search")
    object Bookmarks : Routes("bookmarks")
    object Profile : Routes("profile")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
