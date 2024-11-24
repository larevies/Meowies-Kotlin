package com.example.meowieskotlin.modules

data class KPMovie(
    val docs: List<Doc?>?,
    val total: Long?,
    val limit: Long?,
    val page: Long?,
    val pages: Long?
)

data class Doc(
    val id: Long?,
    val name: String?,
    val alternativeName: String?,
    val enName: String?,
    val type: String?,
    val year: Long?,
    val description: String?,
    val shortDescription: String?,
    val movieLength: Long?,
    val isSeries: Boolean?,
    val ticketsOnSale: Boolean?,
    val totalSeriesLength: Any?,
    val seriesLength: Any?,
    val ratingMpaa: Any?,
    val ageRating: Any?,
    val top10: Any?,
    val top250: Any?,
    val typeNumber: Long?,
    val status: String?,
    val names: List<Name?>?,
    val externalId: ExternalId?,
    val logo: Logo?,
    val poster: Poster?,
    val backdrop: Backdrop?,
    val rating: Rating?,
    val votes: Votes?,
    val genres: List<Genre?>?,
    val countries: List<Country?>?,
    val releaseYears: List<ReleaseYear?>?
)

data class Name(
    val name: String?
)

data class ExternalId(
    val imdb: String?,
    val kpHd: Any?
)

data class Logo(
    val url: Any?
)

data class Poster(
    val url: String?,
    val previewUrl: String?
)

data class Backdrop(
    val url: Any?,
    val previewUrl: Any?
)

data class Rating(
    val kp: Long?,
    val imdb: Double?,
    val filmCritics: Long?,
    val russianFilmCritics: Long?,
    val await: Long?
)

data class Votes(
    val kp: Long?,
    val imdb: Long?,
    val filmCritics: Long?,
    val russianFilmCritics: Long?,
    val await: Long?
)

data class Genre(
    val name: String?
)

data class Country(
    val name: String?
)

data class ReleaseYear(
    val start: Long?,
    val end: Long?
)
