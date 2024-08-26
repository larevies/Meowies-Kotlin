package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.agreement
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.textField
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.modules.emptyMovie
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.getMovieByIdAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking

@Composable
fun Film(navController: NavController, user: String?, movie: String?) {
    //val id = movie?.toInt()
    val film = remember {
        mutableStateOf(emptyMovie)
    }
    val job = getMovieByIdAsync(movie.toString())
    runBlocking {
        if (job.await() != null) {
            film.value = job.await()!!
        }
    }

    //val movieDecoded = Json.decodeFromString<Movie>(movie.toString())
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
                color = fontLight)

            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 10.dp)) {
                AsyncImage(model = film.value.poster,
                    contentDescription = "Doggo_background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(5.dp))
                        ),
                    contentScale = ContentScale.Crop)
                AsyncImage(model = film.value.poster,
                    contentDescription = "Doggo_foreground",
                    modifier = Modifier
                        .height(300.dp)
                        .offset(y = 30.dp)
                        .aspectRatio(9f / 16f)
                        .clip(RoundedCornerShape(5.dp))
                        .border(2.dp, Color.White, RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop)
                /*Image(painter = painterResource(id = R.drawable.doggo),
                    contentDescription = "Doggo_background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(5.dp))
                        ),
                    contentScale = ContentScale.Crop)
                Image(painter = painterResource(id = R.drawable.doggo),
                    contentDescription = "Doggo_foreground",
                    modifier = Modifier
                        .height(300.dp)
                        .offset(y = 30.dp)
                        .border(2.dp, Color.White, RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop)*/
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {

                /*AsyncImage(model = movieDecoded.poster,
                    contentDescription = movieDecoded.title,
                    modifier = Modifier.height(400.dp).clip(RoundedCornerShape(5.dp)))
                */
                textField(text = agreement, size = 25, color = Color.White)

                Image(painter = painterResource(id = R.drawable.pirate),
                    contentDescription = "Pirate cat")
                Spacer(modifier = Modifier.padding(70.dp))
            }
        }

        bottomNavigation(navController = navController, arg = user.toString())
    }
}

@Preview
@Composable
fun FilmPreview() {
    Film(
        rememberNavController(),
        user = "{\"id\":36,\"name\":\"Kitty\",\"email\":\"meow@meow.ru\",\"birthday\":\"2024-07-06\",\"profilePicture\":7}",
        "{\n" +
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
                "      \"name\": \"Jake Sully\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"Neytiri\"\n" +
                "    }\n" +
                "  ]\n" +
                "}")
}
