package com.example.meowieskotlin

sealed class Routes (val route: String) {
    object WelcomeScreen : Routes("welcome_screen")
    object SignIn : Routes("sign_in")
    object SignUp : Routes("sign_up")
}