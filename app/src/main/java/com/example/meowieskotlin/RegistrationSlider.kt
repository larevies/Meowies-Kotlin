@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.meowieskotlin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import com.example.meowieskotlin.ui.theme.fontMistake
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Slide(navController: NavController) {
    Surface (
        modifier = Modifier.fillMaxSize()
    ) {

        var email = remember {
            mutableStateOf("")
        }

        val password = remember {
            mutableStateOf("")
        }

        val passwordRepeated = remember {
            mutableStateOf("")
        }

        var showDatePicker = remember {
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

        var selectedTab = remember {
            mutableIntStateOf(pagerState.currentPage)
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
            Box {
                Button(onClick = {
                    navController.navigate(Routes.WelcomeScreen.route)
                },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.offset(x = 10.dp, y = 25.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Go back",
                        Modifier.size(40.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.paw),
                    contentDescription = "Meowies",
                    modifier = Modifier.height(40.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Meowies",
                    modifier = Modifier.padding(top = 5.dp),
                    style = TextStyle(
                        fontLight,
                        fontSize = 30.sp
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.Center
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
                        0 -> ScreenOne(email)
                        1 -> ScreenTwo(password, passwordRepeated)
                        2 -> ScreenThree(selectedDate, datePickerState, showDatePicker)
                    }
                }
                Button(
                    modifier = Modifier
                        .heightIn(48.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(2.dp, fontLight),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fontMedium
                    ),
                    onClick = {
                        if (selectedTab.value < 2) {
                            selectedTab.value++
                        } else {

                        }
                    }) {
                    Text(
                        text = "Next",
                        style = TextStyle(
                            fontLight,
                            fontSize = 20.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(5f))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenOne(email: MutableState<String>) {

    val focusManager = LocalFocusManager.current
    
    var message = remember {
        mutableStateOf("")
    }

    val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"

    LaunchedEffect(email.value) {
        if (email.value != "" &&
            !email.value.matches(emailRegex.toRegex())
            ) {
            message.value = "Doesn't look like an e-mail"
        } else {
            message.value = ""
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Please, enter your e-mail:",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontLight,
                fontSize = 23.sp
            )
        )
        Spacer(modifier = Modifier.padding(15.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            shape = RoundedCornerShape(20.dp),
            label = {
                Text(
                    text = "E-mail"
                )
            },

            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Email),


            colors = TextFieldDefaults.outlinedTextFieldColors(
                fontDark,
                unfocusedLabelColor = fontDark,
                unfocusedBorderColor = fontLight,
                focusedBorderColor = fontLight,
                focusedLabelColor = fontDark,
                cursorColor = fontLight
            ),
            leadingIcon = {
                Image (
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "E-mail",
                    modifier = Modifier.height(20.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = message.value,
            style = TextStyle(
                fontMistake,
                fontSize = 15.sp,
            ),
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTwo(password: MutableState<String>,
              passwordRepeated: MutableState<String>) {

    val focusManager = LocalFocusManager.current

    var message = remember {
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
        Text(
            text = "Please, enter your password.\nDon't forget to confirm it:",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontLight,
                fontSize = 23.sp
            )
        )
        Spacer(modifier = Modifier.padding(15.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Password",
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            },
            value = password.value,
            onValueChange = {
                password.value = it
            },

            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),

            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                fontDark,
                unfocusedLabelColor = fontDark,
                unfocusedBorderColor = fontLight,
                focusedBorderColor = fontLight,
                focusedLabelColor = fontDark,
                cursorColor = fontLight
            ),

            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.key),
                    contentDescription = "Password",
                    modifier = Modifier.size(20.dp)
                )
            },

            trailingIcon = {
                val icon = if (passwordVisible.value) {
                    R.drawable.witness
                } else {
                    R.drawable.visible
                }

                val description = if (passwordVisible.value) {
                    "Visibility On"
                } else {
                    "Visibility Off"
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = description,
                        modifier = Modifier.height(20.dp)
                    )
                }
            },

            visualTransformation =
                if (passwordVisible.value) VisualTransformation.None
                else PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Confirm password",
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            },
            value = passwordRepeated.value,
            onValueChange = {
                passwordRepeated.value = it
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                fontDark,
                unfocusedLabelColor = fontDark,
                unfocusedBorderColor = fontLight,
                focusedBorderColor = fontLight,
                focusedLabelColor = fontDark,
                cursorColor = fontLight
            ),

            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),

            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.key),
                    contentDescription = "Confirm password",
                    modifier = Modifier.size(20.dp)
                )
            },

            trailingIcon = {
                val icon = if (passwordRepeatedVisible.value) {
                    R.drawable.witness
                } else {
                    R.drawable.visible
                }

                val description = if (passwordRepeatedVisible.value) {
                    "Visibility On"
                } else {
                    "Visibility Off"
                }

                IconButton(onClick = { passwordRepeatedVisible.value = !passwordRepeatedVisible.value }) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = description,
                        modifier = Modifier.height(20.dp)
                    )
                }
            },
            visualTransformation =
                if (passwordRepeatedVisible.value) VisualTransformation.None
                else PasswordVisualTransformation()
        )
        Text(
            text = message.value,
            style = TextStyle(
                fontMistake,
                fontSize = 15.sp,
            ),
            modifier = Modifier
                .padding(top = 20.dp)
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenThree(selectedDate: String,
                datePickerState: DatePickerState,
                showDatePicker: MutableState<Boolean>) {

    val focusManager = LocalFocusManager.current

    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                if (interaction is PressInteraction.Release) {
                    showDatePicker.value = true
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Please, tell us your birthday:",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontLight,
                fontSize = 23.sp
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(
                    text = "Birthday",
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
            },
            interactionSource = interactionSource,
            readOnly = true,
            value = selectedDate,
            trailingIcon = {
                IconButton(
                    onClick = {
                        showDatePicker.value = !showDatePicker.value
                    }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        tint = fontDark
                    )
                }
            },
            onValueChange = { },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                fontDark,
                unfocusedLabelColor = fontDark,
                unfocusedBorderColor = fontLight,
                focusedBorderColor = fontLight,
                focusedLabelColor = fontDark,
                cursorColor = fontLight
            ),
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.balloon),
                    contentDescription = "Birthday",
                    modifier = Modifier.size(20.dp)
                )
            }
        )
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
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                        title = null,
                        colors = DatePickerColors(
                            containerColor = fontLight,
                            titleContentColor = fontMedium,
                            headlineContentColor = fontDark,
                            weekdayContentColor = fontDark,
                            subheadContentColor = fontMedium,
                            navigationContentColor = fontMedium,
                            yearContentColor = fontDark,
                            disabledYearContentColor = fontDark,
                            currentYearContentColor = fontDark,
                            selectedYearContentColor = fontLight,
                            disabledSelectedYearContentColor = fontMedium,
                            selectedYearContainerColor = fontDark,
                            disabledSelectedYearContainerColor = fontDark,
                            dayContentColor = fontDark,
                            disabledDayContentColor = fontMedium,
                            selectedDayContentColor = fontLight,
                            disabledSelectedDayContentColor = fontLight,
                            selectedDayContainerColor = fontDark,
                            disabledSelectedDayContainerColor = fontDark,
                            todayContentColor = fontDark,
                            todayDateBorderColor = fontDark,
                            dayInSelectionRangeContainerColor = fontDark,
                            dayInSelectionRangeContentColor = fontLight,
                            dividerColor = fontMedium,
                            dateTextFieldColors = TextFieldDefaults.textFieldColors(
                                focusedTextColor = fontDark,
                                unfocusedTextColor = fontDark
                            )
                        )
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun SlidePreview(){
    val navController = rememberNavController()
    Slide(navController = navController)
}
