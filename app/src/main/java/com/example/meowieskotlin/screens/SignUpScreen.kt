@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@file:Suppress("DEPRECATION")

package com.example.meowieskotlin.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.meowieskotlin.design.tinyLogo
import com.example.meowieskotlin.design.userAgreement
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.registerAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import com.example.meowieskotlin.viewmodels.SignUpViewModel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val wrongEmail = context.getString(R.string.wrong_email)
    val step = context.getString(R.string.step)
    val next = context.getString(R.string.next)
    val takenEmail = context.getString(R.string.taken_email)
    val agree = context.getString(R.string.agree)
    val redirecting = context.getString(R.string.redirecting)
    val passwordMatch = context.getString(R.string.matching_passwords)

    val viewModel = viewModel<SignUpViewModel>()

    Surface (
        modifier = Modifier.fillMaxSize()
    ) {

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

        LaunchedEffect(viewModel.password.value) {
            isPasswordValid.value = viewModel.password.value != ""
        }

        LaunchedEffect(viewModel.email.value) {
            if (viewModel.email.value != "" &&
                !viewModel.email.value.matches(emailRegex.toRegex())
            ) {
                message.value = wrongEmail
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when(configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
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
                                        text = "$step ${index + 1}",
                                        modifier = Modifier
                                            .padding(vertical = 8.dp),
                                        style = TextStyle(
                                            fontSize = 20.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Configuration.ORIENTATION_SQUARE -> { }
                    Configuration.ORIENTATION_UNDEFINED -> { }
                    Configuration.ORIENTATION_LANDSCAPE -> { }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(9f),
                ) { currentPage ->
                    when (currentPage) {
                        0 -> ScreenOne(message, viewModel, configuration, context)
                        1 -> ScreenTwo(viewModel, configuration, context)
                        2 -> ScreenThree(selectedDate, datePickerState, showDatePicker,
                            viewModel, configuration, context)
                    }
                }
                val text = remember {
                    mutableStateOf(next)
                }
                button(onClick = {
                    if (selectedTab.value < 2) {
                        selectedTab.value++
                    } else {
                        if (!isEmailValid.value) {
                            errorMessage.value = wrongEmail
                        } else if (!isPasswordValid.value) {
                            errorMessage.value = passwordMatch
                        } else if (!viewModel.checked.value) {
                            errorMessage.value = agree
                        } else {
                            try {
                                val success = registerAsync(
                                    viewModel.name.value, viewModel.email.value,
                                    viewModel.password.value, selectedDate
                                )
                                runBlocking {
                                    if (success.await()) {
                                        text.value = redirecting
                                        navController.navigate(Routes.SignIn.route)
                                    } else {
                                        errorMessage.value = takenEmail
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
                when(configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        Spacer(modifier = Modifier.weight(3f))
                    }
                    Configuration.ORIENTATION_LANDSCAPE -> { }
                    Configuration.ORIENTATION_SQUARE -> { }
                    Configuration.ORIENTATION_UNDEFINED -> { }
                }
            }
        }
    }
}

@Composable
fun ScreenOne(message: MutableState<String>, viewModel: SignUpViewModel, configuration: Configuration, context: Context) {

    val focusManager = LocalFocusManager.current
    val nameQuestion = context.getString(R.string.name_question)
    val name = context.getString(R.string.name)
    val email = context.getString(R.string.email)
    val enterEmail = context.getString(R.string.enter_email)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Text(
                    text = nameQuestion,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontLight,
                        fontSize = 23.sp
                    )
                )
            }
            Configuration.ORIENTATION_LANDSCAPE -> { }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }

        textFieldOneIcon(
            value = viewModel.name,
            hint = name,
            focusManager = focusManager,
            image = R.drawable.id,
            KeyboardType.Text
        )

        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = enterEmail,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontLight,
                        fontSize = 23.sp
                    )
                )
            }
            Configuration.ORIENTATION_LANDSCAPE -> { }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }
        textFieldOneIcon(
            value = viewModel.email,
            hint = email,
            focusManager = focusManager,
            image = R.drawable.email,
            KeyboardType.Email
        )
        errorMessage(message = message)
    }
}

@Composable
fun ScreenTwo(viewModel: SignUpViewModel, configuration: Configuration, context: Context) {

    val focusManager = LocalFocusManager.current

    val enterPassword = context.getString(R.string.enter_password)
    val password = context.getString(R.string.password)
    val confirmPassword = context.getString(R.string.confirm_password)
    val passwordMatch = context.getString(R.string.matching_passwords)

    val message = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    val passwordRepeatedVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.password.value) {
        if (viewModel.password.value != viewModel.passwordRepeated.value &&
            viewModel.passwordRepeated.value != "" &&
            viewModel.password.value != "") {
            message.value = passwordMatch
        } else {
            message.value = ""
        }
    }

    LaunchedEffect(viewModel.passwordRepeated.value) {
        if (viewModel.password.value != viewModel.passwordRepeated.value &&
            viewModel.passwordRepeated.value != "" &&
            viewModel.password.value != "") {
            message.value = passwordMatch
        } else {
            message.value = ""
        }
    }


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                textField(
                    text = enterPassword,
                    size = 23,
                    color = fontLight)
                Spacer(modifier = Modifier.padding(15.dp))
            }
            Configuration.ORIENTATION_LANDSCAPE -> { }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }

        passwordField(
            value = viewModel.password,
            isVisible = passwordVisible,
            text = password,
            focusManager = focusManager
        )
        Spacer(modifier = Modifier.padding(10.dp))
        passwordField(
            value = viewModel.passwordRepeated,
            isVisible = passwordRepeatedVisible,
            text = confirmPassword,
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
                viewModel: SignUpViewModel,
                configuration: Configuration,
                context: Context
) {

    val focusManager = LocalFocusManager.current

    val enterBirthday = context.getString(R.string.enter_birthday)
    val agree = context.getString(R.string.agree)
    val consent = context.getString(R.string.consent)

    val screenWidthDp = configuration.screenWidthDp

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                textField(text = enterBirthday, size = 23, color = fontLight)
                Spacer(modifier = Modifier.padding(5.dp))
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                Spacer(modifier = Modifier.padding(10.dp))
            }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }
        val dateText = context.getString(R.string.birthday)
        dateField(selectedDate = selectedDate, showDatePicker = showDatePicker, focusManager, dateText)

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

                    when(configuration.orientation) {
                        Configuration.ORIENTATION_PORTRAIT -> {
                            styledDatePicker(datePickerState = datePickerState,
                                Modifier.fillMaxWidth().width((screenWidthDp * 0.8).dp))
                        }
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            styledDatePicker(datePickerState = datePickerState,
                                Modifier.fillMaxHeight().width((screenWidthDp * 0.4).dp))
                        }
                        Configuration.ORIENTATION_SQUARE -> { }
                        Configuration.ORIENTATION_UNDEFINED -> { }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        when(configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                textField(
                    text = agree,
                    size = 23,
                    color = fontLight)
            }
            Configuration.ORIENTATION_LANDSCAPE -> { }
            Configuration.ORIENTATION_SQUARE -> { }
            Configuration.ORIENTATION_UNDEFINED -> { }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            styledCheckBox(checked = viewModel.checked)
            textField(
                text = consent,
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
