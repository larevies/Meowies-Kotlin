package com.example.meowieskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.meowieskotlin.navigation.Navigation
import com.example.meowieskotlin.ui.theme.MeowiesKotlinTheme
import com.example.meowieskotlin.ui.theme.fontMedium
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MeowiesKotlinTheme {

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = fontMedium)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("this.moveTaskToBack(true)"))
    override fun onBackPressed() {
        this.moveTaskToBack(true)
    }
}
