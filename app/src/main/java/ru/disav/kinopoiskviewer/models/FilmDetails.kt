package ru.disav.kinopoiskviewer.models

data class FilmDetails(
    val filmId: Int,
    val nameRu: String,
    val posterUrl: String,
    val description: String,
    val genres: List<String>,
    val countries: List<String>
)