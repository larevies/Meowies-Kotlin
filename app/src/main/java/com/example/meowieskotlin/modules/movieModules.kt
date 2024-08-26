package com.example.meowieskotlin.modules

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int? = null,
    val title: String,
    val year: Int? = null,
    val ageRating: Int? = null,
    val rating: Double? = null,
    val votes: Int? = null,
    val slogan: String? = null,
    val poster: String? = null,
    val description: String? = null,
    val roles: List<Role>? = null  // actor ID
)

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val movies: List<Int>, // movie ID
    val facts: List<String>,
    val birthday: String,
    val death: String? = null,
    val placeOfBirth: String? = null,
    val photo: String,
    val biography: String
)


@Serializable
data class Role(
    val id: Int,
    val name: String
)

val emptyMovie = Movie(
    id = null,
    title = "No movies correspond to your request!",
    year = null,
    ageRating = null,
    rating = null,
    votes = null,
    slogan = null,
    poster = null,
    description = null,
    roles = null
)