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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavController) {

    val password = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
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
            Image(
                painter = painterResource(id = R.drawable.background_meowies),
                contentDescription = "Meowies Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box {
                Button(
                    onClick = {
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
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center
            )
            {
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
                    text = "Please, fill in the following:",
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
                        cursorColor = fontLight,
                    ),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.email),
                            contentDescription = "Email",
                            modifier = Modifier.size(20.dp)
                        )
                    }
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
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.key),
                            contentDescription = "Confirm password",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    //, keyboardOptions = KeyboardOptions(
                    //    keyboardType = KeyboardType.Password
                    //)
                )
                Spacer(modifier = Modifier.padding(20.dp))

                val user = remember{
                    mutableStateOf("")
                }

                Button(
                    onClick = {
                        /*TODO*/
                    },
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

@Preview
@Composable
fun SignInPreview(){
    SignIn(
        navController = rememberNavController()
    )
}