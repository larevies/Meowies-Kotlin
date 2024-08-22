package com.example.meowieskotlin.modules

import kotlinx.serialization.Serializable

@Serializable
data class Bookmark(
    val id: Int? = null,
    val movieId: Int,
    val userId: Int
)

@Serializable
data class Intel(
    val email: String,
    val password: String
)

@Serializable
data class Name(
    val email: String,
    val name: String
)

@Serializable
data class Picture(
    val email: String,
    val picNum: Int
)

@Serializable
data class NewEmail(
    val email: String,
    val newEmail: String
)

@Serializable
data class User(
    //val id: Int? = null,
    val name: String,
    val email: String,
    val password: String,
    val birthday: String,
    val profilePicture: Int
)

@Serializable
data class UserNoPassword(
    val id: Int? = null,
    val name: String,
    val email: String,
    val birthday: String,
    val profilePicture: Int

)