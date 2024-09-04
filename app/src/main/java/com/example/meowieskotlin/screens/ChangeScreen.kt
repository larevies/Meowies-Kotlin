package com.example.meowieskotlin.screens

import android.content.Context
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.button
import com.example.meowieskotlin.design.getImage
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.passwordField
import com.example.meowieskotlin.design.styledTextField
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.checkPasswordAsync
import com.example.meowieskotlin.requests.switchEmailAsync
import com.example.meowieskotlin.requests.switchNameAsync
import com.example.meowieskotlin.requests.switchPasswordAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking


@Composable
fun Change(navController: NavController) {

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MeowiesPref", Context.MODE_PRIVATE)

    val name = sharedPref.getString("user_name", "Kitty").toString()
    val email = sharedPref.getString("user_email", "email").toString()
    val picture = sharedPref.getInt("user_picture", 1)

    val newName = remember {
        mutableStateOf("")
    }
    val newEmail = remember {
        mutableStateOf("")
    }
    val oldPassword = remember {
        mutableStateOf("")
    }
    val newPassword = remember {
        mutableStateOf("")
    }
    val passwordMessage = remember {
        mutableStateOf("")
    }
    val emailMessage = remember {
        mutableStateOf("")
    }
    val nameMessage = remember {
        mutableStateOf("")
    }
    val newPasswordConfirmed = remember {
        mutableStateOf("")
    }
    val isPasswordVisibleOld = remember {
        mutableStateOf(false)
    }
    val isPasswordVisibleNew = remember {
        mutableStateOf(false)
    }
    val isPasswordVisibleConfirmed = remember {
        mutableStateOf(false)
    }
    val isPasswordValid = remember {
        mutableStateOf(false)
    }
    val isEmailValid = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(newPassword.value) {
        isPasswordValid.value = newPassword.value == newPasswordConfirmed.value
                && newPassword.value != ""
    }
    LaunchedEffect(newPasswordConfirmed.value) {
        isPasswordValid.value = newPassword.value == newPasswordConfirmed.value
                && newPassword.value != ""
    }
    LaunchedEffect(newEmail.value) {
        if (newEmail.value != "" &&
            !newEmail.value.matches(emailRegex.toRegex())
        ) {
            emailMessage.value = "Doesn't look like an e-mail"
            isEmailValid.value = false
        } else {
            emailMessage.value = ""
            isEmailValid.value = true
        }
    }

    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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
        logo()
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                goBackButton(
                    navController = navController,
                    route = Routes.Profile.route,
                    text = "Go back",
                    size = 40.dp
                )

                Button(
                    onClick = {
                        navController.navigate(Routes.Picture.route)
                              },
                    modifier = Modifier
                        .height(60.dp)
                        .offset(x = (-24).dp, y = 25.dp),
                    contentPadding = PaddingValues(),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Image(
                        painter = painterResource(id = getImage(id = picture)),
                        contentDescription = "Profile picture"
                    )
                }

            }
            Spacer(modifier = Modifier.padding(20.dp))
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                textFieldAligned(
                    text = "Here you can change your profile.",
                    size = 30,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(15.dp))
                textField(text = "Name", size = 22, color = Color.White)
                textField(
                    text = "Your current name: $name",
                    size = 20,
                    color = Color.White
                )
                styledTextField(
                    value = newName, hint = "New name", focusManager = focusManager,
                    image = R.drawable.id, keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = nameMessage.value, size = 15, color = fontDark)
                button(onClick = {
                        val success = switchNameAsync(
                            email,
                            newName.value
                        )
                        runBlocking {
                            if (success.await()) {
                                nameMessage.value = "Success!"
                            } else {
                                nameMessage.value = "Something went wrong."
                            }
                        }
                    },
                    text = "Change my name",
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(text = "Email", size = 22, color = Color.White)
                textField(
                    text = "Your current email: $email",
                    size = 20,
                    color = Color.White
                )
                styledTextField(
                    value = newEmail, hint = "New email", focusManager = focusManager,
                    image = R.drawable.email, keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = emailMessage.value, size = 15, color = fontDark)
                button(
                    onClick = {
                        if (isEmailValid.value) {
                            val success = switchEmailAsync(
                                email,
                                newEmail.value
                            )
                            runBlocking {
                                if (success.await()) {
                                    emailMessage.value = "Success!"
                                } else {
                                    emailMessage.value = "Something went wrong."
                                }
                            }
                        } else {
                            emailMessage.value = "E-mail is invalid."
                        }
                    },
                    text = "Change my email",
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(text = "Password", size = 22, color = Color.White)
                textField(text = "Don't worry, we don't watch", size = 20, color = Color.White)
                passwordField(
                    value = oldPassword,
                    isVisible = isPasswordVisibleOld,
                    text = "Old password",
                    focusManager = focusManager
                )
                passwordField(
                    value = newPassword,
                    isVisible = isPasswordVisibleNew,
                    text = "New password",
                    focusManager = focusManager
                )
                passwordField(
                    value = newPasswordConfirmed,
                    isVisible = isPasswordVisibleConfirmed,
                    text = "Confirm new password",
                    focusManager = focusManager
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = passwordMessage.value, size = 15, color = fontDark)
                button(
                    onClick = {
                        if (isPasswordValid.value) {
                            val match = checkPasswordAsync(email, oldPassword.value)
                            runBlocking {
                                if (match.await()) {
                                    val change = switchPasswordAsync(email, newPassword.value)
                                    runBlocking {
                                        if (change.await()) {
                                            passwordMessage.value = "Success!"
                                        } else {
                                            passwordMessage.value = "Something went wrong."
                                        }
                                    }
                                } else {
                                    passwordMessage.value = "Old password didn't match."
                                }
                            }
                        } else {
                            passwordMessage.value = "Passwords do not match"
                        }
                    },
                    text = "Change my password",
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(
                    text = "You can't change your birthday. We will celebrate it with you!",
                    size = 20,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(20.dp))
                textField(
                    text = "Almost forgot to tell you. You can also change your cat profile icon by clicking on it! Enjoy!",
                    size = 20,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.fishes),
                    contentDescription = "Cat with fish"
                )
                Spacer(modifier = Modifier.padding(70.dp))
            }
        }

        bottomNavigation(navController = navController)
    }
}


@Preview
@Composable
fun ChangePreview() {
    Change(navController = rememberNavController())
}