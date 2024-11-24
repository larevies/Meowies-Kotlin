package com.example.meowieskotlin.requests

import com.example.meowieskotlin.modules.Actor
import com.example.meowieskotlin.modules.KPMovie
import com.example.meowieskotlin.modules.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
fun getMovieByNameKinopoiskAsync(name: String) = GlobalScope.async {
    getMovieByNameKinopoisk(name)
}
suspend fun getMovieByNameKinopoisk(name: String): KPMovie?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }


    val httpResponse : HttpResponse = client.get("https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=10&selectFields=id&selectFields=name&selectFields=shortDescription&selectFields=slogan&selectFields=year&selectFields=rating&selectFields=ageRating&selectFields=votes&selectFields=poster&selectFields=persons&query=$name&token=$token")
    return if (httpResponse.status.value in 200..299) {
        try {
            val kpMovie : KPMovie = httpResponse.body()
            client.close()
            kpMovie
        } catch (e:Exception) {
            client.close()
            null
        }
    } else {
        client.close()
        null
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun getMovieByIdKinopoiskAsync(id: String) = GlobalScope.async {
    getMovieByIdKinopoisk(id)
}
suspend fun getMovieByIdKinopoisk(id: String): Movie?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse : HttpResponse = client.get("$apiAddress/movie/id/$id")
    return if (httpResponse.status.value in 200..299) {
        try {
            val movie : Movie = httpResponse.body()
            client.close()
            movie
        } catch (e:Exception) {
            client.close()
            null
        }
    } else {
        client.close()
        null
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun getActorByNameKinopoiskAsync(name: String) = GlobalScope.async {
    getActorByNameKinopoisk(name)
}
suspend fun getActorByNameKinopoisk(name: String): List<Actor>?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse : HttpResponse = client.get("$apiAddress/actor/$name")
    return if (httpResponse.status.value in 200..299) {
        try {
            val actors : List<Actor> = httpResponse.body()
            client.close()
            actors
        } catch (e:Exception) {
            client.close()
            null
        }
    } else {
        client.close()
        null
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun getActorByIdKinopoiskAsync(id: String) = GlobalScope.async {
    getActorByIdKinopoisk(id)
}
suspend fun getActorByIdKinopoisk(id: String): Actor?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse : HttpResponse = client.get("$apiAddress/actor/id/$id")
    return if (httpResponse.status.value in 200..299) {
        try {
            val actor : Actor = httpResponse.body()
            client.close()
            actor
        } catch (e:Exception) {
            client.close()
            null
        }
    } else {
        client.close()
        null
    }
}