package ru.disav.kinopoiskviewer.models

data class Film(
    val filmId: Int,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String?,
    val rating: String?
)