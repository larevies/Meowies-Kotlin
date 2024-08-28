package com.example.meowieskotlin.requests

import com.example.meowieskotlin.modules.Bookmark
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
fun getBookmarkAsync(userId: Int?, movieId: Int?) = GlobalScope.async {
    getBookmark(userId, movieId)
}
suspend fun getBookmark(userId: Int?, movieId: Int?): Boolean  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 5000
            socketTimeout = 5000
        }
    }

    val bookmark = Bookmark(userId = userId, movieId = movieId)
    val httpResponse: HttpResponse = client.post("$apiAddress/bookmark/find") {
        contentType(ContentType.Application.Json)
        setBody(bookmark)
    }

    return if (httpResponse.status.value in 200..299) {
        client.close()
        true
    } else {
        client.close()
        false
    }
}
@OptIn(DelicateCoroutinesApi::class)
fun addBookmarkAsync(userId: Int?, movieId: Int?) = GlobalScope.async {
    addBookmark(userId, movieId)
}
suspend fun addBookmark(userId: Int?, movieId: Int?): Boolean  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val bookmark = Bookmark(userId = userId, movieId = movieId)
    val httpResponse: HttpResponse = client.post("$apiAddress/bookmark") {
        contentType(ContentType.Application.Json)
        setBody(bookmark)
    }

    return if (httpResponse.status.value in 200..299) {
        client.close()
        true
    } else {
        client.close()
        false
    }
}
@OptIn(DelicateCoroutinesApi::class)
fun deleteBookmarkAsync(userId: Int?, movieId: Int?) = GlobalScope.async {
    deleteBookmark(userId, movieId)
}
suspend fun deleteBookmark(userId: Int?, movieId: Int?): Boolean  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val bookmark = Bookmark(userId = userId, movieId = movieId)
    val httpResponse: HttpResponse = client.delete("$apiAddress/bookmark") {
        contentType(ContentType.Application.Json)
        setBody(bookmark)
    }

    return if (httpResponse.status.value in 200..299) {
        client.close()
        true
    } else {
        client.close()
        false
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun getAllBookmarksAsync(userId: Int?) = GlobalScope.async {
    getAllBookmarks(userId)
}
suspend fun getAllBookmarks(userId: Int?): List<Bookmark>?  {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.get("$apiAddress/bookmark/$userId")
    val bookmarks : List<Bookmark> = httpResponse.body()

    return if (httpResponse.status.value in 200..299) {
        client.close()
        bookmarks
    } else {
        client.close()
        null
    }
}
