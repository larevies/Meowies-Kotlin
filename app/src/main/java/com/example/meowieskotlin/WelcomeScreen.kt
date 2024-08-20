package com.example.meowieskotlin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.ui.theme.backgroundColor
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun Welcome(navController: NavController){
    Surface(
        color = backgroundColor,
        modifier = Modifier
            .fillMaxSize()
    ) {
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
            Column (
                modifier = Modifier.padding(30.dp)
            ) {
                Text(
                    text = "Welcome to",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontLight,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "Meowies!",
                    modifier = Modifier
                        .fillMaxWidth().weight(0.5f),
                    style = TextStyle(
                        fontDark,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "The best platform ever for saving every " +
                            "movie you'd like to watch!",
                    modifier = Modifier
                        .fillMaxWidth().weight(1.0f),
                    style = TextStyle(
                        fontLight,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Image (
                    painter = painterResource(id = R.drawable.cats),
                    contentDescription = "Cats!",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.weight(2.5f)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp).weight(1.0f)
                ){
                    Button(
                        onClick = {
                            navController.navigate(Routes.SignIn.route)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(),
                        border = BorderStroke(2.dp, fontLight),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign in",
                                style = TextStyle(
                                    fontMedium,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            navController.navigate(Routes.SignUp.route)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(),
                        border = BorderStroke(2.dp, fontLight),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fontMedium
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sign up",
                                style = TextStyle(
                                    fontLight,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun WelcomePreview() {
    Welcome(navController = rememberNavController())
}