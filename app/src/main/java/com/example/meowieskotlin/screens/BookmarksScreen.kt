package com.example.meowieskotlin.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.modules.Bookmark
import com.example.meowieskotlin.modules.Movie
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.getAllBookmarksAsync
import com.example.meowieskotlin.requests.getMovieByIdAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking


val speech = """
    All the movies you add to bookmarks will appear on this page. 
    
    It's empty for now.
""".trimIndent()

@Composable
fun Bookmarks(navController: NavController) {

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MeowiesPref", Context.MODE_PRIVATE)
    val id = sharedPref.getInt("user_id", 1)

    val bookmarkPresence = remember {
        mutableStateOf(false)
    }

    val bookmarks = remember {
        mutableListOf<Bookmark>()
    }
    val movies = remember {
        mutableListOf<Movie>()
    }

    LaunchedEffect(bookmarks) {
        bookmarkPresence.value = bookmarks.size > 0
    }

    val i = remember {
        mutableStateOf(0)
    }
    runBlocking {
        val job = getAllBookmarksAsync(id)
        if (i.value == 0) {
            val result = job.await()
            if (result != null) {
                for (res in result) {
                    bookmarks.add(res)
                    val movie = getMovieByIdAsync(res.movieId.toString())
                    movies.add(movie.await()!!)
                }
                i.value++
            }
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
        logo()
        Column (modifier = Modifier.padding(30.dp, 100.dp, 30.dp, 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            textFieldAligned(text = "Bookmarks!", size = 40, color = fontLight)
            Spacer(modifier = Modifier.padding(15.dp))
            if (bookmarkPresence.value) {
                Box(modifier = Modifier
                    .weight(6f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 15.dp)) {
                    Column {
                        movies.forEach { movie ->
                            Row(modifier = Modifier.padding(10.dp)) {
                                Button(
                                    onClick = {
                                        try {
                                            navController.navigate(
                                                Routes.Film.withArgs(movie.id.toString())
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
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(RoundedCornerShape(5.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = movie.title,
                                        modifier = Modifier.padding(15.dp),
                                        style = TextStyle(
                                            fontDark
                                        )
                                    )
                                    Text(
                                        text = "·",
                                        style = TextStyle(
                                            fontLight
                                        )
                                    )
                                    Text(
                                        text = movie.year.toString(),
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .weight(1f),
                                        style = TextStyle(
                                            fontLight
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(id = R.drawable.mermaid),
                                contentDescription = "Mermaid cat")
                        }
                        Spacer(modifier = Modifier.padding(70.dp))
                    }
                }
            } else {
                Text(
                    text = speech,
                    style = TextStyle(
                        fontLight,
                        fontSize = 25.sp
                    )
                )
                Image(painter = painterResource(id = R.drawable.laptop_kitten),
                    contentDescription = "Laptop kitten",
                    modifier = Modifier.weight(6f))
            }
            Box(modifier = Modifier.weight(1f))
        }
        bottomNavigation(navController = navController)
    }
}

@Preview
@Composable
fun BookmarksPreview() {
    Bookmarks(rememberNavController())
}