package ru.disav.kinopoiskviewer.data.network.dto

data class FilmDto(
    val filmId: Int,
    val nameRu: String?,
    val posterUrlPreview: String?,
    val year: String?,
    val rating: String?
)