package com.example.meowieskotlin.requests

import com.example.meowieskotlin.modules.Actor
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
fun getMovieByNameAsync(name: String) = GlobalScope.async {
    getMovieByName(name)
}
suspend fun getMovieByName(name: String): List<Movie>?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse : HttpResponse = client.get("$apiAddress/movie/$name")
    return if (httpResponse.status.value in 200..299) {
        try {
            val movies : List<Movie> = httpResponse.body()
            client.close()
            movies
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
fun getMovieByIdAsync(id: String) = GlobalScope.async {
    getMovieById(id)
}
suspend fun getMovieById(id: String): Movie?  {
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
fun getActorByNameAsync(name: String) = GlobalScope.async {
    getActorByName(name)
}
suspend fun getActorByName(name: String): List<Actor>?  {
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
fun getActorByIdAsync(id: String) = GlobalScope.async {
    getActorById(id)
}
suspend fun getActorById(id: String): Actor?  {
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