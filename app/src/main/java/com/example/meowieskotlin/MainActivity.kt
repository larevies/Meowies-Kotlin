package com.example.meowieskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meowieskotlin.ui.theme.MeowiesKotlinTheme
import com.example.meowieskotlin.ui.theme.backgroundColor
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowiesKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Greeting(name = "asdf")//Navigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    // move to some other file
    val password = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }

    Surface(
        color = backgroundColor,
        modifier = modifier
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
                    .padding(30.dp)
            )
                    //.background(Color.Cyan)) // TODO
            {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "Happy to see you again!",
                    modifier = modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 44.sp
                    )
                )
                Text(
                    text = "Please, fill in the following",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 28.sp
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
                        )},
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = fontDark,
                        unfocusedBorderColor = fontLight,
                        focusedBorderColor = fontLight,
                        focusedLabelColor = fontDark,
                        cursorColor = fontLight,
                        textColor = fontDark
                    )
                    //, keyboardOptions = KeyboardOptions(
                    //    keyboardType = KeyboardType.Password
                    //)
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
                        )},
                    value = password.value,
                    onValueChange = {
                        password.value = it
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedLabelColor = fontDark,
                        unfocusedBorderColor = fontLight,
                        focusedBorderColor = fontLight,
                        focusedLabelColor = fontDark,
                        cursorColor = fontLight,
                        textColor = fontDark
                    )

                //, keyboardOptions = KeyboardOptions(
                    //    keyboardType = KeyboardType.Password
                    //)
                )
                Spacer(modifier = Modifier.padding(20.dp))

                val user = remember{
                    mutableStateOf("")
                }
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
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .heightIn(50.dp)
                                .fillMaxWidth()
                                .background(backgroundColor),
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
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .heightIn(50.dp)
                                .fillMaxWidth()
                                .background(
                                    fontMedium,
                                    shape = RoundedCornerShape(50.dp)
                                ),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeowiesKotlinTheme {
        Greeting("asdf") //Greeting("hehe")
    }
}