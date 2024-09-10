@file:Suppress("DEPRECATION")

package com.example.meowieskotlin.screens

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.meowieskotlin.design.tinyLogo
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.checkPasswordAsync
import com.example.meowieskotlin.requests.switchEmailAsync
import com.example.meowieskotlin.requests.switchNameAsync
import com.example.meowieskotlin.requests.switchPasswordAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontMedium
import com.example.meowieskotlin.viewmodels.ChangeViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun Change(navController: NavController) {

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val changeName = context.getString(R.string.change_name)
    val changeEmail = context.getString(R.string.change_email)
    val changePassword = context.getString(R.string.change_password)
    val newEmail = context.getString(R.string.new_email)
    val currentName = context.getString(R.string.current_name)
    val changeDescription = context.getString(R.string.change_description)
    val emailString = context.getString(R.string.email)
    val password = context.getString(R.string.password)
    val nameString = context.getString(R.string.name)
    val wrongEmail = context.getString(R.string.wrong_email)
    val newName = context.getString(R.string.new_name)
    val successString = context.getString(R.string.success)
    val currentEmail = context.getString(R.string.current_email)
    val doNotWatch = context.getString(R.string.do_not_watch)
    val newPassword = context.getString(R.string.new_password)
    val confirmNewPassword = context.getString(R.string.confirm_new_password)
    val oldPassword = context.getString(R.string.old_password)
    val passwordMatchOld = context.getString(R.string.matching_passwords_old)
    val passwordMatch = context.getString(R.string.matching_passwords)
    val celebration = context.getString(R.string.celebration)
    val almostForgot = context.getString(R.string.almost_forgot)
    val internetConnection = context.getString(R.string.internet_connection)

    val sharedPref = context.getSharedPreferences("MeowiesPref", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()

    val name = sharedPref.getString("user_name", "Kitty").toString()
    val email = sharedPref.getString("user_email", "email").toString()
    val picture = sharedPref.getInt("user_picture", 1)

    val viewModel = viewModel<ChangeViewModel>()

    val passwordMessage = remember {
        mutableStateOf("")
    }
    val emailMessage = remember {
        mutableStateOf("")
    }
    val nameMessage = remember {
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
    LaunchedEffect(viewModel.newPassword.value) {
        isPasswordValid.value = viewModel.newPassword.value == viewModel.newPasswordConfirmed.value
                && viewModel.newPassword.value != ""
    }
    LaunchedEffect(viewModel.newPasswordConfirmed.value) {
        isPasswordValid.value = viewModel.newPassword.value == viewModel.newPasswordConfirmed.value
                && viewModel.newPassword.value != ""
    }
    LaunchedEffect(viewModel.newEmail.value) {
        if (viewModel.newEmail.value != "" &&
            !viewModel.newEmail.value.matches(emailRegex.toRegex())
        ) {
            emailMessage.value = wrongEmail
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
        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                goBackButton(navController = navController,
                    route = Routes.Profile.route,
                    text = "Go back", 40.dp)
                logo()
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                goBackButton(navController = navController,
                    route = Routes.Profile.route,
                    text = "Go back", 20.dp)
                tinyLogo()
            }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
            when(configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    Spacer(modifier = Modifier.padding(20.dp))
                }
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Spacer(modifier = Modifier.padding(12.dp))
                }
                Configuration.ORIENTATION_SQUARE -> { }
                Configuration.ORIENTATION_UNDEFINED -> { }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                textFieldAligned(
                    text = changeDescription,
                    size = 30,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(15.dp))
                textField(text = nameString, size = 22, color = Color.White)
                textField(
                    text = "$currentName: $name",
                    size = 20,
                    color = Color.White
                )
                styledTextField(
                    value = viewModel.newName, hint = newName, focusManager = focusManager,
                    image = R.drawable.id, keyboardType = KeyboardType.Text
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = nameMessage.value, size = 15, color = fontDark)
                button(onClick = {
                        val success = switchNameAsync(
                            email,
                            viewModel.newName.value
                        )
                        runBlocking {
                            if (success.await()) {
                                nameMessage.value = successString
                                editor.apply { putString("user_name", viewModel.newName.value ) }
                                editor.apply()
                            } else {
                                Toast.makeText(context, internetConnection, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    text = changeName,
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(text = emailString, size = 22, color = Color.White)
                textField(
                    text = "$currentEmail: $email",
                    size = 20,
                    color = Color.White
                )
                styledTextField(
                    value = viewModel.newEmail, hint = newEmail, focusManager = focusManager,
                    image = R.drawable.email, keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = emailMessage.value, size = 15, color = fontDark)
                button(
                    onClick = {
                        if (isEmailValid.value) {
                            val success = switchEmailAsync(
                                email,
                                viewModel.newEmail.value
                            )
                            runBlocking {
                                if (success.await()) {
                                    emailMessage.value = successString
                                    editor.apply { putString("user_email", viewModel.newEmail.value ) }
                                    editor.apply()
                                } else {
                                    Toast.makeText(context, internetConnection, Toast.LENGTH_SHORT)
                                }
                            }
                        } else {
                            emailMessage.value = wrongEmail
                        }
                    },
                    text = changeEmail,
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(text = password, size = 22, color = Color.White)
                textField(text = doNotWatch, size = 20, color = Color.White)
                passwordField(
                    value = viewModel.oldPassword,
                    isVisible = isPasswordVisibleOld,
                    text = oldPassword,
                    focusManager = focusManager
                )
                passwordField(
                    value = viewModel.newPassword,
                    isVisible = isPasswordVisibleNew,
                    text = newPassword,
                    focusManager = focusManager
                )
                passwordField(
                    value = viewModel.newPasswordConfirmed,
                    isVisible = isPasswordVisibleConfirmed,
                    text = confirmNewPassword,
                    focusManager = focusManager
                )
                Spacer(modifier = Modifier.padding(5.dp))
                textFieldAligned(text = passwordMessage.value, size = 15, color = fontDark)
                button(
                    onClick = {
                        if (isPasswordValid.value) {
                            val match = checkPasswordAsync(email, viewModel.oldPassword.value)
                            runBlocking {
                                if (match.await()) {
                                    val change = switchPasswordAsync(email, viewModel.newPassword.value)
                                    runBlocking {
                                        if (change.await()) {
                                            passwordMessage.value = successString
                                        } else {
                                            Toast.makeText(context, internetConnection, Toast.LENGTH_SHORT)
                                        }
                                    }
                                } else {
                                    passwordMessage.value = passwordMatchOld
                                }
                            }
                        } else {
                            passwordMessage.value = passwordMatch
                        }
                    },
                    text = changePassword,
                    background = fontMedium
                )
                Spacer(modifier = Modifier.padding(30.dp))
                textField(
                    text = celebration,
                    size = 20,
                    color = Color.White
                )
                Spacer(modifier = Modifier.padding(20.dp))
                textField(
                    text = almostForgot,
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