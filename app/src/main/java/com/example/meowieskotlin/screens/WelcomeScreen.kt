@file:Suppress("DEPRECATION")

package com.example.meowieskotlin.screens

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.ui.theme.backgroundColor
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium


@Composable
fun Welcome(navController: NavController) {

    val ruAppLocale = LocaleList.forLanguageTags("ru")
    val enAppLocale = LocaleList.forLanguageTags("en-US")

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val welcome = context.getString(R.string.welcome)
    val meowies = context.getString(R.string.app_name)
    val description = context.getString(R.string.description)
    val signIn = context.getString(R.string.sign_in)
    val signUp = context.getString(R.string.sign_up)

    val sharedPref = context.getSharedPreferences("MeowiesPref", Context.MODE_PRIVATE)
    val isSigned = sharedPref.getString("user_email", "")

    if (isSigned != "") {
        navController.navigate(Routes.Search.route)
    } else {

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
                val isExpanded = remember {
                    mutableStateOf(true)
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart
                ) {

                    Button(
                        modifier = Modifier.padding(15.dp),
                        contentPadding = PaddingValues(),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            isExpanded.value = !isExpanded.value
                        }
                    ) {
                        Image(painter = painterResource(id = R.drawable.language),
                            contentDescription = "Change language")
                    }
                    DropdownMenu(
                        expanded = isExpanded.value,
                        onDismissRequest = { isExpanded.value = !isExpanded.value},
                        modifier = Modifier
                            .background(fontLight)
                            .padding(15.dp)
                    ) {
                        Button(
                            contentPadding = PaddingValues(),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = {
                                context.getSystemService(
                                    LocaleManager::class.java
                                ).applicationLocales = enAppLocale
                            }) {
                            Image(painter = painterResource(id = R.drawable.english),
                                contentDescription = "English language")
                            Text(text = "English", style = TextStyle(fontDark, fontSize = 20.sp),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        Button(
                            contentPadding = PaddingValues(),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = {
                                context.getSystemService(
                                    LocaleManager::class.java
                                ).applicationLocales = ruAppLocale
                            }) {
                            Image(painter = painterResource(id = R.drawable.russian),
                                contentDescription = "Russian language")
                            Text(text = "Русский", style = TextStyle(fontDark, fontSize = 20.sp),
                                modifier = Modifier.padding(5.dp))
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(30.dp)
                ) {
                    Text(
                        text = welcome,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = TextStyle(
                            fontLight,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = meowies,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        style = TextStyle(
                            fontDark,
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0f),
                        style = TextStyle(
                            fontLight,
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            Image(
                                painter = painterResource(id = R.drawable.cats),
                                contentDescription = "Cats!",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.weight(2.5f)
                            )
                        }

                        Configuration.ORIENTATION_LANDSCAPE -> { }

                        Configuration.ORIENTATION_SQUARE -> { }

                        Configuration.ORIENTATION_UNDEFINED -> { }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1.0f)
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Routes.SignIn.route) {
                                    popUpTo(Routes.Welcome.route)
                                }
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
                                    text = signIn,
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
                                navController.navigate(Routes.SignUp.route) {
                                    popUpTo(Routes.Welcome.route)
                                }
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
                                    text = signUp,
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
}

@Preview
@Composable
fun WelcomePreview() {
    Welcome(navController = rememberNavController())
}