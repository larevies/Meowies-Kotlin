package com.example.meowieskotlin.requests

import com.example.meowieskotlin.modules.Intel
import com.example.meowieskotlin.modules.User
import com.example.meowieskotlin.modules.UserNoPassword
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

const val apiAddress = "http://192.168.0.7:8080"

@OptIn(DelicateCoroutinesApi::class)
fun authorizeAsync(email: String, password: String) = GlobalScope.async {
    authorize(email, password)
}

suspend fun authorize(email: String, password: String): UserNoPassword? {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/login") {
        contentType(ContentType.Application.Json)
        setBody(
            Intel(
                email = email,
                password = password
            )
        )
    }

    return if (httpResponse.status.value in 200..299) {
        val user: UserNoPassword = httpResponse.body()
        client.close()
        user
    } else {
        client.close()
        null
    }

}

@OptIn(DelicateCoroutinesApi::class)
fun registerAsync(name: String, email: String, password: String, birthday: String) = GlobalScope.async {
    register(name, email, password, birthday)
}

suspend fun register(name: String, email: String, password: String, birthday: String): Boolean {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val picture = (1..11).random()

    val httpResponse: HttpResponse = client.post("$apiAddress/user") {
        contentType(ContentType.Application.Json)
        setBody(
            User(
                name = name,
                email = email,
                password = password,
                birthday = birthday,
                profilePicture = picture
            )
        )
    }

    client.close()
    return httpResponse.status.value in 200..299

}