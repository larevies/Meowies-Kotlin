package com.example.meowieskotlin.design

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
fun tinyLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.paw),
            contentDescription = "Meowies",
            modifier = Modifier.height(25.dp)
        )
        Spacer(modifier = Modifier.padding(3.dp))
        Text(
            text = "Meowies",
            modifier = Modifier.padding(top = 3.dp),
            style = TextStyle(
                fontLight,
                fontSize = 22.sp
            )
        )
    }
}

@Composable
fun background() {
    Image(
        painter = painterResource(id = R.drawable.background_meowies),
        contentDescription = "Meowies Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
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
            .padding(top = 10.dp)
    )
}

@Composable
fun getImage(id: Int?): Int {
    return when(id) {
        1 -> R.drawable.icon1
        2 -> R.drawable.icon2
        3 -> R.drawable.icon3
        4 -> R.drawable.icon4
        5 -> R.drawable.icon5
        6 -> R.drawable.icon6
        7 -> R.drawable.icon7
        8 -> R.drawable.icon8
        9 -> R.drawable.icon9
        10 -> R.drawable.icon10
        11 -> R.drawable.icon11
        12 -> R.drawable.icon12
        else -> R.drawable.cat
    }
}