@file:Suppress("DEPRECATION")

package com.example.meowieskotlin.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.button
import com.example.meowieskotlin.design.errorMessage
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.passwordField
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.design.textFieldOneIcon
import com.example.meowieskotlin.design.tinyLogo
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.authorizeAsync
import com.example.meowieskotlin.ui.theme.backgroundColor
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import com.example.meowieskotlin.viewmodels.SignInViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun SignIn(navController: NavController) {

    val viewModel = viewModel<SignInViewModel>()

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val sharedPref = context.getSharedPreferences("MeowiesPref", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()

    val errorMessage = remember {
        mutableStateOf("")
    }

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    val message = remember {
        mutableStateOf("")
    }

    val buttonText = remember {
        mutableStateOf("Log in")
    }

    val isEmailValid = remember {
        mutableStateOf(false)
    }

    val isPasswordValid = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.password.value) {
        isPasswordValid.value = viewModel.password.value != ""
    }

    LaunchedEffect(viewModel.email.value) {
        if (viewModel.email.value != "" &&
            !viewModel.email.value.matches(emailRegex.toRegex())
        ) {
            message.value = "Doesn't look like an e-mail"
            isEmailValid.value = false
        } else {
            message.value = ""
            isEmailValid.value = true
        }
    }

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
            background()
            when(configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    goBackButton(navController = navController, route = Routes.Welcome.route,
                        text = "Go back", 40.dp)
                    logo()
                }
                Configuration.ORIENTATION_LANDSCAPE -> {

                    goBackButton(navController = navController, route = Routes.Welcome.route,
                        text = "Go back", 20.dp)
                    tinyLogo()
                }
                Configuration.ORIENTATION_SQUARE -> { }
                Configuration.ORIENTATION_UNDEFINED -> { }
            }
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        textFieldAligned(
                            text = "Happy to see you again!",
                            size = 44, color = Color.White
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Please, fill in the following:",
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(
                                fontLight,
                                fontSize = 23.sp
                            )
                        )
                    }
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Configuration.ORIENTATION_SQUARE -> { }
                    Configuration.ORIENTATION_UNDEFINED -> { }
                }
                textFieldOneIcon(
                    value = viewModel.email,
                    hint = "E-mail",
                    focusManager = focusManager,
                    image = R.drawable.email,
                    keyboardType = KeyboardType.Email
                )
                errorMessage(message = message)
                passwordField(
                    value = viewModel.password,
                    isVisible = isPasswordVisible,
                    text = "Password",
                    focusManager = focusManager
                )
                Spacer(modifier = Modifier.padding(20.dp))
                button(
                    onClick = {
                        if (!isEmailValid.value) {
                            errorMessage.value = "E-mail is invalid"
                        } else if (!isPasswordValid.value) {
                            errorMessage.value = "Passwords do not match"
                        } else {
                            try {
                                val user = authorizeAsync(viewModel.email.value,
                                    viewModel.password.value)
                                runBlocking {
                                    val loggedUser = user.await()
                                    if (loggedUser != null) {

                                        editor.apply { putInt("user_id", loggedUser.id ) }
                                        editor.apply { putString("user_email", loggedUser.email ) }
                                        editor.apply { putString("user_birthday", loggedUser.birthday ) }
                                        editor.apply { putInt("user_picture", loggedUser.profilePicture ) }
                                        editor.apply { putString("user_name", loggedUser.name ) }
                                        editor.apply()

                                        navController.navigate(Routes.Profile.route)


                                        buttonText.value = "Redirecting"
                                    } else {
                                        errorMessage.value = "Wrong credentials"
                                    }
                                }
                            } catch (e: Exception) {
                                buttonText.value = "Internal error. App will be closed soon"
                            }
                        }
                    },
                    text = buttonText.value,
                    background = fontMedium)
                errorMessage(
                    message = errorMessage
                )
            }
        }
    }
}

@Preview
@Composable
fun SignInPreview(){
    SignIn(
        navController = rememberNavController()
    )
}