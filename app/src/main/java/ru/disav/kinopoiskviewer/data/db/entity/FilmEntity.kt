package ru.disav.kinopoiskviewer.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val filmId: String,
    val nameRu: String?,
    val posterUrlPreview: String?,
    val year: String?,
    val rating: String?
)

