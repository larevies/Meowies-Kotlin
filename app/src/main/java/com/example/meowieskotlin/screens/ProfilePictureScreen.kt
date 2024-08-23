package com.example.meowieskotlin.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.button
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.modules.UserNoPassword
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.switchPictureAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfilePicture(navController: NavController, user: String?) {

    val userDecoded = Json.decodeFromString<UserNoPassword>(user.toString())
    val userMutable = remember {
        mutableStateOf(userDecoded)
    }

    val kitties = listOf(
        R.drawable.icon1,
        R.drawable.icon2,
        R.drawable.icon3,
        R.drawable.icon4,
        R.drawable.icon5,
        R.drawable.icon6,
        R.drawable.icon7,
        R.drawable.icon8,
        R.drawable.icon9,
        R.drawable.icon10,
        R.drawable.icon11,
        R.drawable.icon12,
    )
    val pageCount = kitties.size * 20
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount }
    )
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
        goBackButton(navController = navController,
            route = Routes.Profile.withArgs(Json.encodeToString(userMutable.value)),
            text = "Go back", 40.dp)
        logo()
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {

        }
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.padding(50.dp))
            textFieldAligned(text = "Pick any!", size = 40, color = Color.White)
            Spacer(modifier = Modifier.padding(20.dp))
            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(40.dp),
                    pageSpacing = 10.dp,
                    beyondBoundsPageCount = 1
                ) {
                        index ->
                    kitties.getOrNull(
                        index % (kitties.size)
                    )?.let { kitty ->
                        KittyBox(kitty)
                    }
                }
            }
            val text = remember {
                mutableStateOf("")
            }
            Box(modifier = Modifier.padding(30.dp)) {
                button(
                    onClick = {
                        try {
                            val kittyId = (pagerState.currentPage % kitties.size) + 1
                            userMutable.value.profilePicture = kittyId
                            val success = switchPictureAsync(
                                userMutable.value.email,
                                kittyId
                            )
                            runBlocking {
                                if (success.await()) {
                                    text.value = "Gotcha!"
                                } else {
                                    text.value = "Some error occurred."
                                }
                            }
                        } catch (e: Exception) {
                            text.value = "Internal error"
                        }
                    },
                    text = "Pick this kitty!",
                    background = fontMedium
                )
            }
            textFieldAligned(text = text.value, size = 20, color = Color.White)
            bottomNavigation(navController = navController,
                arg = Json.encodeToString(userMutable.value))
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        var initPage = pageCount / 2
        while (initPage % kitties.size != 0) {
            initPage++
        }
        pagerState.scrollToPage(initPage)
    })

}

@Composable
fun KittyBox(kitty: Int) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = kitty),
            contentDescription = "Kitty",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(280.dp)
        )
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    ProfilePicture(rememberNavController(),
        user = "{\"id\":36,\"name\":\"Kitty\",\"email\":\"meow@meow.ru\",\"birthday\":\"2024-07-06\",\"profilePicture\":7}")
}