package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.styledTextFieldButton
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium

@Composable
fun Search(navController: NavController, user: String?) {

    val focusManager = LocalFocusManager.current

    val isSearchResultVisible = remember {
        mutableStateOf(false)
    }

    val searchRequest = remember {
        mutableStateOf("")
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
        Column (modifier = Modifier.padding(horizontal = 30.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            textField(text = "Search!", size = 40, color = fontLight)
            Spacer(modifier = Modifier.padding(15.dp))
            styledTextFieldButton(
                navController = navController,
                value = searchRequest,
                hint = "movie, actor, etc",
                focusManager = focusManager,
                image = R.drawable.search,
                keyboardType = KeyboardType.Text
            )
            if (isSearchResultVisible.value) {
                Box(modifier = Modifier.weight(6f))
            } else {
                Image(painter = painterResource(id = R.drawable.space_explorer),
                    contentDescription = "",
                    modifier = Modifier.weight(6f))
            }
            Box(modifier = Modifier.weight(1f))
        }
        bottomNavigation(navController = navController, user.toString())
    }
}

@Preview
@Composable
fun SearchPreview() {
    Search(rememberNavController(), null)
}