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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.getActorByNameAsync
import com.example.meowieskotlin.requests.getMovieByNameAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val type: Int? = null,
    val id: Int? = null,
    val photo: String? = null,
    val name: String,
    val year: String? = null
)

@Composable
fun Search(navController: NavController) {

    val isSearchResultVisible = remember {
        mutableStateOf(false)
    }

    val searchRequest = remember {
        mutableStateOf("")
    }

    val searchResults = remember { mutableListOf<Item>() }

    fun searchButton() {
        if (searchRequest.value != "") {
            try {
                val successMovie = getMovieByNameAsync(searchRequest.value)
                val successActor = getActorByNameAsync(searchRequest.value)
                runBlocking {
                    searchResults.clear()
                    val movies = successMovie.await()
                    if (movies != null) {
                        for (movie in movies) {
                            searchResults.add(Item(
                                type = 0,
                                id = movie.id,
                                photo = movie.poster,
                                name = movie.title,
                                year = movie.year.toString()
                            )
                            )
                        }
                    }
                    val actors = successActor.await()
                    if (actors != null) {
                        for (actor in actors) {
                            searchResults.add(Item(
                                type = 1,
                                id = actor.id,
                                photo = actor.photo,
                                name = actor.name,
                                year = actor.birthday
                            )
                            )
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

            OutlinedTextField(
                value = searchRequest.value,
                onValueChange = { searchRequest.value = it },
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = "movie, actor, etc"
                    )
                },

                keyboardActions = KeyboardActions(
                    onDone = {
                        searchButton()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),


                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = fontDark,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = fontLight,
                    focusedBorderColor = fontLight,
                    unfocusedBorderColor = fontLight,
                    focusedLabelColor = fontDark,
                    unfocusedLabelColor = fontDark
                ),
                leadingIcon = {
                    Button(
                        onClick = { searchButton() },
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
                    Column { searchResults.forEach{ item ->
                            Row (modifier = Modifier.padding(10.dp)) {
                                Button(onClick = {
                                    try {
                                        if (item.type == 0) {
                                            navController.navigate(
                                                Routes.Film.withArgs(item.id.toString()))
                                        } else if (item.type == 1) {
                                            navController.navigate(
                                                Routes.Person.withArgs(item.id.toString()))
                                        }
                                    } catch (e: Exception) {
                                        searchRequest.value =
                                            "Check your internet connection and try again"
                                    }
                                    },
                                    contentPadding = PaddingValues(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(5.dp)
                                ) {
                                    AsyncImage(
                                        model = item.photo,
                                        contentDescription = item.name,
                                        modifier = Modifier.size(60.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(text = item.name,
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
                                    Text(text = item.year.toString(),
                                        softWrap = false,
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
        bottomNavigation(navController = navController)
    }
}

@Preview
@Composable
fun SearchPreview() {
    Search(rememberNavController())
}