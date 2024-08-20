package com.example.meowieskotlin.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meowieskotlin.R
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMistake

@Composable
fun logo() {
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
}

@Composable
fun errorMessage(message: MutableState<String>) {
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
