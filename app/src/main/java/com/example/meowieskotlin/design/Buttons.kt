package com.example.meowieskotlin.design

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.ui.theme.fontLight


@Composable
fun button(onClick: () -> Unit, text: String, background: Color) {
    Button(
        modifier = Modifier
            .heightIn(48.dp)
            .fillMaxWidth(),
        border = BorderStroke(2.dp, fontLight),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        onClick = onClick) {
        Text(
            text = text,
            style = TextStyle(
                fontLight,
                fontSize = 20.sp
            )
        )
    }
}/*
@Composable
fun buttonLight(onClick: () -> Unit, text: String) {
    Button(
        modifier = Modifier
            .heightIn(48.dp)
            .fillMaxWidth(),
        border = BorderStroke(2.dp, fontLight),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = fontMedium
        ),
        onClick = onClick) {
        Text(
            text = text,
            style = TextStyle(
                fontLight,
                fontSize = 20.sp
            )
        )
    }
}*/

@Composable
fun navigationButton(navController: NavController, route: String, image: Int, text: String, size: Dp) {
    Button(onClick = {
        navController.navigate(route)
    },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )//,
        //modifier = Modifier.offset(x = 10.dp, y = 25.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = text,
            Modifier.size(size)
        )
    }
}
@Composable
fun goBackButton(navController: NavController, route: String, text: String, size: Dp) {
    Button(onClick = {
        navController.navigate(route)
    },
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.offset(x = 10.dp, y = 25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = text,
            Modifier.size(size)
        )
    }
}