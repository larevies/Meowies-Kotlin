@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.meowieskotlin.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.button
import com.example.meowieskotlin.design.dateField
import com.example.meowieskotlin.design.errorMessage
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.passwordField
import com.example.meowieskotlin.design.styledCheckBox
import com.example.meowieskotlin.design.styledDatePicker
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.design.textFieldOneIcon
import com.example.meowieskotlin.design.userAgreement
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.registerAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {



    Surface (
        modifier = Modifier.fillMaxSize()
    ) {

        val email = remember {
            mutableStateOf("")
        }

        val name = remember {
            mutableStateOf("")
        }

        val password = remember {
            mutableStateOf("")
        }

        val passwordRepeated = remember {
            mutableStateOf("")
        }

        val showDatePicker = remember {
            mutableStateOf(false)
        }

        val datePickerState = rememberDatePickerState()

        val selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: ""

        val pagerState = rememberPagerState(
            pageCount = { 3 },
            initialPage = 0
        )

        val selectedTab = remember {
            mutableIntStateOf(pagerState.currentPage)
        }

        val checked = remember {
            mutableStateOf(false)
        }

        val isEmailValid = remember {
            mutableStateOf(false)
        }

        val isPasswordValid = remember {
            mutableStateOf(false)
        }

        val message = remember {
            mutableStateOf("")
        }

        val errorMessage = remember {
            mutableStateOf("")
        }

        LaunchedEffect(password.value) {
            isPasswordValid.value = password.value != ""
        }

        LaunchedEffect(email.value) {
            if (email.value != "" &&
                !email.value.matches(emailRegex.toRegex())
            ) {
                message.value = "Doesn't look like an e-mail"
                isEmailValid.value = false
            } else {
                message.value = ""
                isEmailValid.value = true
            }
        }

        LaunchedEffect(selectedTab.value) {
            pagerState.scrollToPage(selectedTab.value)
        }

        LaunchedEffect(pagerState.currentPage) {
            selectedTab.value = pagerState.currentPage
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
            goBackButton(navController = navController, route = Routes.Welcome.route,
                text = "Go back", 40.dp)
            logo()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(30.dp))
                TabRow(
                    selectedTabIndex = selectedTab.value,
                    containerColor = Color.Transparent,
                    contentColor = fontLight
                ) {
                    for (index in 0 until pagerState.pageCount) {
                        Tab(
                            selected = index == selectedTab.value,
                            onClick = {
                                selectedTab.value = index
                            },
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Text(
                                text = "Step ${index + 1}",
                                modifier = Modifier
                                    .padding(vertical = 8.dp),
                                style = TextStyle(
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(9f),
                ) { currentPage ->
                    when (currentPage) {
                        0 -> ScreenOne(name, email, message)
                        1 -> ScreenTwo(password, passwordRepeated)
                        2 -> ScreenThree(selectedDate, datePickerState, showDatePicker, checked)
                    }
                }
                val text = remember {
                    mutableStateOf("Next")
                }
                button(onClick = {
                    if (selectedTab.value < 2) {
                        selectedTab.value++
                    } else {
                        if (!isEmailValid.value) {
                            errorMessage.value = "E-mail is invalid"
                        } else if (!isPasswordValid.value) {
                            errorMessage.value = "Passwords do not match"
                        } else if (!checked.value) {
                            errorMessage.value = "To continue you have to agree " +
                                    "to the user agreement"
                        } else {
                            try {
                                val success = registerAsync(
                                    name.value, email.value,
                                    password.value, selectedDate
                                )
                                runBlocking {
                                    if (success.await()) {
                                        text.value = "Redirecting"
                                        navController.navigate(Routes.SignIn.route)
                                    } else {
                                        errorMessage.value = "This email is already taken!"
                                    }
                                }
                            } catch (e: Exception) {
                                print(e.message)
                            }
                        }
                    }
                }, text = text.value, background = fontMedium)
                errorMessage(
                    message = errorMessage
                )
                Spacer(modifier = Modifier.weight(3f))
            }
        }
    }
}

@Composable
fun ScreenOne(name: MutableState<String>, email: MutableState<String>,
              message: MutableState<String>) {

    val focusManager = LocalFocusManager.current

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        textFieldOneIcon(
            text = "What's your name?",
            value = name,
            hint = "Name",
            focusManager = focusManager,
            image = R.drawable.id,
            KeyboardType.Text
        )
        Spacer(modifier = Modifier.padding(20.dp))
        textFieldOneIcon(
            text = "Please, enter your e-mail:",
            value = email,
            hint = "E-mail",
            focusManager = focusManager,
            image = R.drawable.email,
            KeyboardType.Email
        )
        errorMessage(message = message)
    }
}

@Composable
fun ScreenTwo(password: MutableState<String>,
              passwordRepeated: MutableState<String>) {

    val focusManager = LocalFocusManager.current

    val message = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val passwordRepeatedVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(password.value) {
        if (password.value != passwordRepeated.value &&
            passwordRepeated.value != "" &&
            password.value != "") {
            message.value = "Passwords do not match"
        } else {
            message.value = ""
        }
    }

    LaunchedEffect(passwordRepeated.value) {
        if (password.value != passwordRepeated.value &&
            passwordRepeated.value != "" &&
            password.value != "") {
            message.value = "Passwords do not match"
        } else {
            message.value = ""
        }
    }


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        textField(
            text = "Please, enter your password.\nDon't forget to confirm it:",
            size = 23,
            color = fontLight)
        Spacer(modifier = Modifier.padding(15.dp))
        passwordField(
            value = password,
            isVisible = passwordVisible,
            text = "Password",
            focusManager = focusManager
        )
        Spacer(modifier = Modifier.padding(10.dp))
        passwordField(
            value = passwordRepeated,
            isVisible = passwordRepeatedVisible,
            text = "Confirm password",
            focusManager = focusManager
        )
        errorMessage(message = message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenThree(selectedDate: String,
                datePickerState: DatePickerState,
                showDatePicker: MutableState<Boolean>,
                checked: MutableState<Boolean>) {

    val focusManager = LocalFocusManager.current

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        textField(text = "Please, tell us your birthday:", size = 23, color = fontLight)
        Spacer(modifier = Modifier.padding(5.dp))
        dateField(selectedDate = selectedDate, showDatePicker = showDatePicker, focusManager)

        if (showDatePicker.value) {
            Popup(
                onDismissRequest = { showDatePicker.value = false },
                alignment = Alignment.TopStart
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(fontLight)
                        .padding(16.dp)
                ) {
                    styledDatePicker(datePickerState = datePickerState)
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        textField(
            text = "To continue you have to agree to the user agreement.",
            size = 23,
            color = fontLight)
        Spacer(modifier = Modifier.padding(10.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            styledCheckBox(checked = checked)
            textField(
                text = "I consent to the processing of personal data described below.",
                size = 18,
                color = fontDark)
        }
        Spacer(modifier = Modifier.padding(12.dp))
        userAgreement()
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun SlidePreview(){
    val navController = rememberNavController()
    SignUp(navController = navController)
}
