package com.example.meowieskotlin.modules

import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val type: Int? = null,
    val id: Int? = null,
    val photo: String? = null,
    val name: String,
    val year: String? = null
)


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
    val roles: List<Role>? = null
)


@Serializable
data class Actor(
    val id: Int? = null,
    val name: String,
    val roles: List<Role>? = null,//TODO
    val facts: List<String>? = null,//TODO
    val birthday: String? = null,
    val death: String? = null,
    val placeOfBirth: String? = null,
    val photo: String? = null,
    val biography: String? = null//TODO
)


@Serializable
data class Role(
    val id: Int,
    val actorName: String,
    val roleName: String,
    val movieName: String,
    val idActor: Int,
    val idMovie: Int
)


val emptyMovie = Movie(
    id = null,
    title = "Getting the info for you",
    year = null,
    ageRating = null,
    rating = null,
    votes = null,
    slogan = null,
    poster = "",
    description = null,
    roles = null
)


val emptyActor = Actor(
    id = null,
    name = "No actor found with this id",
    roles = null,
    facts = null,
    birthday = null,
    death = null,
    placeOfBirth = null,
    photo = null,
    biography = null
)
