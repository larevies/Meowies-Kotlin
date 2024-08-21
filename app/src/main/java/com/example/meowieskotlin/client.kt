package com.example.meowieskotlin

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.meowieskotlin.modules.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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

const val apiAddress = "http://192.168.0.7:8080"


@OptIn(DelicateCoroutinesApi::class)
fun postRequestAsync() = GlobalScope.async {
    postRequest()
}
suspend fun postRequest(): Boolean {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 10000
            socketTimeout = 10000
        }
    }

    val httpResponse: HttpResponse = client.post("$apiAddress/user") {
        contentType(ContentType.Application.Json)
        setBody(
            User(
                name = "misha",
                email = "happy@happy.ha",
                password = "pswd",
                birthday = "2002-06-16",
                profilePicture = 5
            )
        )
    }

    if (httpResponse.status.value in 200..299) {
        client.close()
        return true
    }

    client.close()
    return false
}


suspend fun getRequest(text: MutableState<String>): User? {

    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            connectTimeout = 10000
            socketTimeout = 10000
        }
    }

    val httpResponse: HttpResponse = client.get("$apiAddress/user/user@user.user")
    val user: User = httpResponse.body()
    Log.i("ResponseStatus", httpResponse.call.toString())
    client.close()

    return user
}
