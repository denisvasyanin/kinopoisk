package ru.disav.kinopoiskviewer.data.mappers

import ru.disav.kinopoiskviewer.data.network.dto.FilmDetailsDto
import ru.disav.kinopoiskviewer.models.FilmDetails

fun FilmDetailsDto.toFilmDetails(): FilmDetails {
    return FilmDetails(
        filmId = filmId,
        nameRu = nameRu.orEmpty(),
        posterUrl = posterUrl.orEmpty(),
        description = description.orEmpty(),
        genres = genres?.map { it.genre ?: "" } ?: emptyList(),
        countries = countries?.map { it.country ?: "" } ?: emptyList()
    )
}