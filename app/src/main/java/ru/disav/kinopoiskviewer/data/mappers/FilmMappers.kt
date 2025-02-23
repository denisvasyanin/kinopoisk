package ru.disav.kinopoiskviewer.data.mappers

import ru.disav.kinopoiskviewer.data.db.entity.FilmEntity
import ru.disav.kinopoiskviewer.data.network.dto.FilmDto
import ru.disav.kinopoiskviewer.models.Film

fun FilmDto.toFilmEntity(): FilmEntity {
    return FilmEntity(
        filmId = filmId.toString(),
        nameRu = nameRu,
        posterUrlPreview = posterUrlPreview,
        year = year,
        rating = rating
    )
}

fun FilmEntity.toFilm(): Film {
    return Film(
        filmId = filmId.toInt(),
        nameRu = nameRu.orEmpty(),
        posterUrlPreview = posterUrlPreview.orEmpty(),
        year = year,
        rating = rating
    )
}

fun FilmDto.toFilm(): Film {
    return Film(
        filmId = filmId,
        nameRu = nameRu.orEmpty(),
        posterUrlPreview = posterUrlPreview.orEmpty(),
        year = year,
        rating = rating
    )
}