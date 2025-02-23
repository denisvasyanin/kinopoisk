package ru.disav.kinopoiskviewer.data.network.dto

data class FilmsResponseDto(
    val pagesCount: Int,
    val films: List<FilmDto>
)