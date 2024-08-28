package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.modules.Movie
import com.example.meowieskotlin.modules.emptyMovie
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.getMovieByNameAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController, user: String?) {

    val focusManager = LocalFocusManager.current

    val isSearchResultVisible = remember {
        mutableStateOf(false)
    }

    val searchRequest = remember {
        mutableStateOf("")
    }

    val searchResult = remember {mutableListOf<Movie>()}

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







            OutlinedTextField(
                value = searchRequest.value,
                onValueChange = { searchRequest.value = it },
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = "movie, actor, etc"
                    )
                },

                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),


                colors = TextFieldDefaults.outlinedTextFieldColors(
                    fontDark,
                    unfocusedLabelColor = fontDark,
                    unfocusedBorderColor = fontLight,
                    focusedBorderColor = fontLight,
                    focusedLabelColor = fontDark,
                    cursorColor = fontLight
                ),
                leadingIcon = {
                    Button(
                        onClick = {
                            if (searchRequest.value != "") {
                                try {
                                    val success = getMovieByNameAsync(searchRequest.value)
                                    runBlocking {
                                        searchResult.clear()
                                        val movies = success.await()
                                        if (movies != null) {
                                            for (movie in movies) {
                                                searchResult.add(movie)
                                            }
                                            if (searchResult.size == 0) {
                                                searchResult.add(emptyMovie)
                                            }
                                        }
                                        isSearchResultVisible.value = true
                                    }
                                } catch (e: Exception) {
                                    searchRequest.value =
                                        "Check your internet connection and try again"
                                }
                            } else {
                                isSearchResultVisible.value = false
                            }
                        },
                        contentPadding = PaddingValues(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Image (
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "movie, actor, etc",
                            modifier = Modifier.height(20.dp)
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (isSearchResultVisible.value) {
                Box(modifier = Modifier
                    .weight(6f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 15.dp)) {
                    Column { searchResult.forEach{ movie ->
                            Row (modifier = Modifier.padding(10.dp)) {
                                Button(onClick = {
                                    try {
                                        navController.navigate(Routes.Film.withArgs(
                                            user.toString(),
                                            movie.id.toString()
                                            )
                                        )
                                    } catch (e: Exception) {
                                        print(e)
                                    }
                                    },
                                    contentPadding = PaddingValues(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ) {
                                    AsyncImage(
                                        model = movie.poster,
                                        contentDescription = movie.title,
                                        modifier = Modifier.size(60.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(text = movie.title,
                                        modifier = Modifier.padding(15.dp),
                                        style = TextStyle(
                                            fontDark
                                        )
                                    )
                                    Text(text = "·",
                                        style = TextStyle(
                                            fontLight
                                        )
                                    )
                                    Text(text = movie.year.toString(),
                                        modifier = Modifier.padding(15.dp).weight(1f),
                                        style = TextStyle(
                                            fontLight
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
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