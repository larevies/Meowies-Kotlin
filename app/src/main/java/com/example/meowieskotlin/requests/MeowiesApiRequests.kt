package com.example.meowieskotlin.requests

import com.example.meowieskotlin.modules.Intel
import com.example.meowieskotlin.modules.NewEmail
import com.example.meowieskotlin.modules.Picture
import com.example.meowieskotlin.modules.User
import com.example.meowieskotlin.modules.UserName
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

    val picture = (1..12).random()

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

@OptIn(DelicateCoroutinesApi::class)
fun switchPictureAsync(email: String, pic: Int) = GlobalScope.async {
    switchPicture(email, pic)
}

suspend fun switchPicture(email: String, pic: Int): Boolean {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/change/pic") {
        contentType(ContentType.Application.Json)
        setBody(
            Picture(
                email = email,
                picNum = pic
            )
        )
    }

    return httpResponse.status.value in 200..299
}

@OptIn(DelicateCoroutinesApi::class)
fun switchPasswordAsync(email: String, newPassword: String) = GlobalScope.async {
    switchPassword(email, newPassword)
}

suspend fun switchPassword(email: String, newPassword: String): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/change/password") {
        contentType(ContentType.Application.Json)
        setBody(
            Intel(
                email = email,
                password = newPassword
            )
        )
    }

    return httpResponse.status.value in 200..299
}

@OptIn(DelicateCoroutinesApi::class)
fun switchEmailAsync(email: String, newEmail: String) = GlobalScope.async {
    switchEmail(email, newEmail)
}

suspend fun switchEmail(email: String, newEmail: String): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/change/email") {
        contentType(ContentType.Application.Json)
        setBody(
            NewEmail(
                email = email,
                newEmail = newEmail
            )
        )
    }

    return httpResponse.status.value in 200..299
}

@OptIn(DelicateCoroutinesApi::class)
fun switchNameAsync(email: String, newName: String) = GlobalScope.async {
    switchName(email, newName)
}

suspend fun switchName(email: String, newName: String): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/change/name") {
        contentType(ContentType.Application.Json)
        setBody(
            UserName(
                email = email,
                name = newName
            )
        )
    }

    return httpResponse.status.value in 200..299
}

@OptIn(DelicateCoroutinesApi::class)
fun checkPasswordAsync(email: String, oldPassword: String) = GlobalScope.async {
    checkPassword(email, oldPassword)
}

suspend fun checkPassword(email: String, oldPassword: String): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 3000
            socketTimeout = 3000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/check") {
        contentType(ContentType.Application.Json)
        setBody(
            Intel(
                email = email,
                password = oldPassword
            )
        )
    }

    return httpResponse.status.value in 200..299
}