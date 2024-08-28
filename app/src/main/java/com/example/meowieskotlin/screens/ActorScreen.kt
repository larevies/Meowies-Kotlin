package com.example.meowieskotlin.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.meowieskotlin.R
import com.example.meowieskotlin.design.background
import com.example.meowieskotlin.design.bottomNavigation
import com.example.meowieskotlin.design.goBackButton
import com.example.meowieskotlin.design.logo
import com.example.meowieskotlin.design.textFieldAligned
import com.example.meowieskotlin.modules.emptyActor
import com.example.meowieskotlin.navigation.Routes
import com.example.meowieskotlin.requests.getActorByIdAsync
import com.example.meowieskotlin.ui.theme.backgroundLight
import com.example.meowieskotlin.ui.theme.fontDark
import com.example.meowieskotlin.ui.theme.fontLight
import com.example.meowieskotlin.ui.theme.fontMedium
import kotlinx.coroutines.runBlocking

@Composable
fun Person(navController: NavController, user: String?, id: String?) {

    val actor = remember {
        mutableStateOf(emptyActor)
    }
    val job = getActorByIdAsync(id.toString())
    runBlocking {
        if (job.await() != null) {
            actor.value = job.await()!!
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
        goBackButton(
            navController = navController,
            route = Routes.Search.withArgs(user.toString()),
            text = "Go back", 40.dp
        )
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
                text = actor.value.name,
                size = 50,
                color = fontLight
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                AsyncImage(
                    model = actor.value.photo,
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
                    model = actor.value.photo,
                    contentDescription = actor.value.name,
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
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Birthday: ",
                        style = TextStyle(Color.White), fontSize = 22.sp)
                    Text(text = actor.value.birthday.toString(),
                        style = TextStyle(fontDark), fontSize = 22.sp)
                }

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text = "Born in: ",
                        style = TextStyle(Color.White), fontSize = 22.sp)
                    Text(text = actor.value.placeOfBirth.toString(),
                        style = TextStyle(fontDark), fontSize = 22.sp)
                }

                if (actor.value.death != null) {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(text = "Day of death: ",
                            style = TextStyle(Color.White), fontSize = 22.sp)
                        Text(text = actor.value.death.toString(),
                            style = TextStyle(fontDark), fontSize = 22.sp)
                    }
                }

                Text(
                    text = actor.value.biography.toString(), modifier = Modifier.padding(vertical = 24.dp),
                    style = TextStyle(Color.White, fontSize = 20.sp, textAlign = TextAlign.Justify)
                )

                Text(
                    text = "Movies", modifier = Modifier.padding(vertical = 20.dp),
                    style = TextStyle(fontDark, fontSize = 22.sp, textAlign = TextAlign.Justify)
                )

                Column {
                    actor.value.roles?.forEach {
                            role ->
                        Row (modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Button(
                                onClick = {
                                    navController.navigate(
                                        Routes.Film.withArgs(
                                            user.toString(),
                                            role.idMovie.toString()
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                )
                            ){
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = role.movieName, style = TextStyle(fontDark, textAlign = TextAlign.Center, fontSize = 22.sp))
                                    Text(text = role.roleName, style = TextStyle(fontDark.copy(alpha = 0.5f), textAlign = TextAlign.Center, fontSize = 20.sp))
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "Facts", modifier = Modifier.padding(vertical = 20.dp),
                    style = TextStyle(fontDark, fontSize = 24.sp, textAlign = TextAlign.Justify)
                )

                Column {
                    actor.value.facts?.forEach {
                            fact ->
                        Row (modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = fact, style = TextStyle(fontLight, textAlign = TextAlign.Center, fontSize = 20.sp))
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(50.dp))
                Image(painter = painterResource(id = R.drawable.hero),
                    contentDescription = "Pirate cat")
                Spacer(modifier = Modifier.padding(70.dp))
            }
        }
        bottomNavigation(navController = navController, arg = user.toString())
    }
}

@Preview
@Composable
fun PersonPreview() {
    Person(rememberNavController(), user = "", id = "1")
}