package com.example.meowieskotlin.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.button
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.modules.UserNoPassword
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.serialization.json.Json

@Composable
fun Profile(navController: NavController, user: String?) {
    val userNoPassword = Json.decodeFromString<UserNoPassword>(user.toString())
    val userMutable = remember {
        mutableStateOf(userNoPassword)
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
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(onClick = {
                navController.navigate(Routes.Welcome.route)
            },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.offset(x = 20.dp, y = 25.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sign_out),
                    contentDescription = "Sign out",
                    Modifier.size(40.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .height(60.dp)
                    .offset(x = (-24).dp, y = 25.dp)
            )
        }
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            textField(text = "Hi, ${userMutable.value.name}!", size = 40, color = fontDark)
            Spacer(modifier = Modifier.padding(15.dp))
            textField(text = "That's your profile. Want to change something?", size = 25, color = fontLight)
            Spacer(modifier = Modifier.padding(15.dp))
            button(onClick = { /*TODO*/ }, text = "Change password", background = Color.Transparent)
            Spacer(modifier = Modifier.padding(10.dp))
            button(onClick = { /*TODO*/ }, text = "Change name", background = Color.Transparent)
            Spacer(modifier = Modifier.padding(10.dp))
            button(onClick = { /*TODO*/ }, text = "Change email", background = Color.Transparent)
            Spacer(modifier = Modifier.padding(10.dp))
            button(onClick = { /*TODO*/ }, text = "Can't change birthday", background = Color.Transparent)
            Spacer(modifier = Modifier.padding(15.dp))
            Image(painter = painterResource(id = R.drawable.pet), contentDescription = "Kitty",
                modifier = Modifier.height(100.dp))
        }
        bottomNavigation(navController = navController, user.toString())
    }
}


@Preview
@Composable
fun ProfilePreview(){
    Profile(rememberNavController(), null)
}