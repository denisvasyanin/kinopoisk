package ru.disav.kinopoiskviewer.data.network.dto

data class FilmDetailsDto(
    val filmId: Int,
    val nameRu: String?,
    val posterUrl: String?,
    val description: String?,
    val genres: List<GenreDto>?,
    val countries: List<CountryDto>?
)