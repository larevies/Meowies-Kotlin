package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium


val speech = """
    All the movies you add to bookmarks will appear on this page. 
    
    It's empty for now.
""".trimIndent()
@Composable
fun Bookmarks(navController: NavController, user: String?) {

    val bookmarksVisibility = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush
                    .verticalGradient(
                        listOf(
                            fontMedium,
                            backgroundLight
                        )
                    ),
            )
    ) {
        background()
        Column (modifier = Modifier.padding(horizontal = 30.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            textField(text = "Bookmarks!", size = 40, color = fontLight)
            Spacer(modifier = Modifier.padding(15.dp))
            if (bookmarksVisibility.value) {
                Box(modifier = Modifier.weight(6f))
            } else {
                Text(
                    text = speech,
                    style = TextStyle(
                        fontLight,
                        fontSize = 25.sp
                    )
                )
                Image(painter = painterResource(id = R.drawable.laptop_kitten),
                    contentDescription = "Laptop kitten",
                    modifier = Modifier.weight(6f))
            }
            Box(modifier = Modifier.weight(1f))
        }
        bottomNavigation(navController = navController, user.toString())
    }
}

@Preview
@Composable
fun BookmarksPreview() {
    Bookmarks(rememberNavController(), null)
}