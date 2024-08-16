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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.ui.theme.backgroundColor
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.meowieskotlin.ui.theme.yellowLight
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {

    val password = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }

    val confirmedPassword = remember {
        mutableStateOf("")
    }

    var showDatePicker by remember {
        mutableStateOf(false) /*TODO*/
    }

    val datePickerState = rememberDatePickerState()

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""




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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            )
            //.background(Color.Cyan)) // TODO
            {
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Happy to see you again!",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 44.sp
                    )
                )
                Spacer(modifier = Modifier.padding(15.dp))
                Text(
                    text = "Please, fill in the following",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "E-mail",
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                        )
                    },
                    value = email.value,
                    onValueChange = {
                        email.value = it
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
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "Email",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                )
                Spacer(modifier = Modifier.padding(10.dp))
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
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        fontDark,
                        unfocusedLabelColor = fontDark,
                        unfocusedBorderColor = fontLight,
                        focusedBorderColor = fontLight,
                        focusedLabelColor = fontDark,
                        cursorColor = fontLight
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "Password",
                            modifier = Modifier.size(20.dp)
                        )
                    },


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
                    value = confirmedPassword.value,
                    onValueChange = {
                        confirmedPassword.value = it
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "Confirm password",
                            modifier = Modifier.size(20.dp)
                        )
                    }
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
                    readOnly = true,
                    value = selectedDate,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showDatePicker = !showDatePicker
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
                if (showDatePicker) {
                    Popup(
                        onDismissRequest = { showDatePicker = false },
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

                Spacer(modifier = Modifier.padding(20.dp))

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            /*TODO*/
                        },
                        modifier = Modifier
                            .weight(1f),
                        contentPadding = PaddingValues(),
                        border = BorderStroke(2.dp, Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .heightIn(50.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Go back",
                                color = fontMedium,
                                style = TextStyle(
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            /*TODO*/
                        },
                        modifier = Modifier
                            .weight(1f),
                        contentPadding = PaddingValues(),
                        border = BorderStroke(2.dp, Color.White),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fontMedium
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .heightIn(50.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Log in",
                                color = fontLight,
                                style = TextStyle(
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

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview
@Composable
fun SignUpPreview(){
    SignUp(
        navController = rememberNavController()
    )
}