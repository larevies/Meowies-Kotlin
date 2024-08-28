package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.errorMessage
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.modules.UserNoPassword
import com.example.meowieskotlin.modules.emptyMovie
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.addBookmarkAsync
import com.example.meowieskotlin.requests.deleteBookmarkAsync
import com.example.meowieskotlin.requests.getBookmarkAsync
import com.example.meowieskotlin.requests.getMovieByIdAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun Film(navController: NavController, user: String?, movie: String?) {

    val userDecoded = Json.decodeFromString<UserNoPassword>(user.toString())

    val message = remember {
        mutableStateOf("")
    }

    val isBookmarked = remember {
        mutableStateOf(false)
    }
    val film = remember {
        mutableStateOf(emptyMovie)
    }
    val isVisible = remember {
        mutableStateOf(false)
    }

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                val job = getMovieByIdAsync(movie.toString())
                val success = getBookmarkAsync(
                    userDecoded.id,
                    movie.toString().toInt()
                )

                runBlocking {
                    if (job.await() != null) {
                        film.value = job.await()!!
                    }
                    isBookmarked.value = success.await()
                    isVisible.value = true
                }
            }
            else -> {}
        }
    }

    val scrollState = rememberScrollState()
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
            route = Routes.Search.withArgs(user.toString()),
            text = "Go back", 40.dp)
        logo()
        Spacer(modifier = Modifier.padding(100.dp))
        if (isVisible.value) {
            Column(
                modifier = Modifier
                    .padding(0.dp, 100.dp, 0.dp, 0.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                textFieldAligned(
                    text = film.value.title,
                    size = 50,
                    color = fontLight
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    AsyncImage(
                        model = film.value.poster,
                        contentDescription = "Poster Background",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(4f / 3f)
                            .blur(
                                radiusX = 10.dp,
                                radiusY = 10.dp,
                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(5.dp))
                            ),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = film.value.poster,
                        contentDescription = film.value.title,
                        modifier = Modifier
                            .height(300.dp)
                            .offset(y = 30.dp)
                            .aspectRatio(9f / 16f)
                            .clip(RoundedCornerShape(5.dp))
                            .border(2.dp, Color.White, RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.padding(15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (!isBookmarked.value) {
                        Button(
                            onClick = {
                                isBookmarked.value = !isBookmarked.value
                                val success = addBookmarkAsync(
                                    userDecoded.id,
                                    movie.toString().toInt()
                                )
                                runBlocking {
                                    success.await()
                                }
                            },
                            contentPadding = PaddingValues(),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.heart_empty),
                                contentDescription = "Not bookmarked",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = {
                                isBookmarked.value = !isBookmarked.value
                                val success = deleteBookmarkAsync(
                                    userDecoded.id,
                                    movie.toString().toInt()
                                )
                                runBlocking {
                                    success.await()
                                }
                            },
                            contentPadding = PaddingValues(),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.heart_filled),
                                contentDescription = "Bookmarked",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }

                    errorMessage(message = message)

                    Text(
                        text = film.value.slogan.toString(),
                        style = TextStyle(
                            fontDark, fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Production year: ",
                            style = TextStyle(Color.White), fontSize = 22.sp
                        )
                        Text(
                            text = film.value.year.toString(),
                            style = TextStyle(fontDark), fontSize = 22.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Age Rating: ",
                            style = TextStyle(Color.White), fontSize = 22.sp
                        )
                        Text(
                            text = "${film.value.ageRating.toString()}+",
                            style = TextStyle(fontDark), fontSize = 22.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Rating: ",
                            style = TextStyle(Color.White), fontSize = 22.sp
                        )
                        Text(
                            text = "${film.value.rating.toString()} ",
                            style = TextStyle(fontDark), fontSize = 22.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = "Star", modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "   (${film.value.votes.toString()} votes)",
                            style = TextStyle(fontDark), fontSize = 15.sp
                        )
                    }

                    Text(
                        text = film.value.description.toString(),
                        modifier = Modifier.padding(vertical = 24.dp),
                        style = TextStyle(
                            Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify
                        )
                    )

                    Text(
                        text = "Actors", modifier = Modifier.padding(vertical = 20.dp),
                        style = TextStyle(fontDark, fontSize = 22.sp, textAlign = TextAlign.Justify)
                    )

                    Column {
                        film.value.roles?.forEach { role ->
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        navController.navigate(
                                            Routes.Person.withArgs(
                                                user.toString(),
                                                role.idActor.toString()
                                            )
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    )
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = role.actorName,
                                            style = TextStyle(
                                                fontDark,
                                                textAlign = TextAlign.Center,
                                                fontSize = 22.sp
                                            )
                                        )
                                        Text(
                                            text = role.roleName,
                                            style = TextStyle(
                                                fontDark.copy(alpha = 0.5f),
                                                textAlign = TextAlign.Center,
                                                fontSize = 20.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.padding(50.dp))
                    Image(
                        painter = painterResource(id = R.drawable.pirate),
                        contentDescription = "Pirate cat"
                    )
                    Spacer(modifier = Modifier.padding(70.dp))
                }
            }

            bottomNavigation(navController = navController, arg = user.toString())
        } else {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.loading), contentDescription = "Loading",
                    modifier = Modifier.height(200.dp))
            }
        }
    }
}

@Preview
@Composable
fun FilmPreview() {
    Film(
        rememberNavController(),
        user = "{\"id\":36,\"name\":\"Kitty\",\"email\":\"meow@meow.ru\",\"birthday\":\"2024-07-06\",\"profilePicture\":7}",
        "1"
        /*"{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Avatar\",\n" +
                "  \"year\": 2009,\n" +
                "  \"ageRating\": 12,\n" +
                "  \"rating\": 8.0,\n" +
                "  \"votes\": 1097191,\n" +
                "  \"slogan\": \"Enter the World\",\n" +
                "  \"poster\": \"https://avatars.mds.yandex.net/get-kinopoisk-image/4774061/a615ba02-1152-4ae2-bac1-38ab4c588f2b/3840x\",\n" +
                "  \"description\": \"A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.\",\n" +
                "  \"roles\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"actorName\": \"Sam Worthington\",\n" +
                "      \"roleName\": \"Jake Sully\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"actorName\": \"Zoe Saldana\",\n" +
                "      \"roleName\": \"Neytiri\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"*/
        )
}
