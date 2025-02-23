package ru.disav.kinopoiskviewer.data.provider

import android.content.Context
import androidx.room.Room
import ru.disav.kinopoiskviewer.data.db.FilmDatabase

object DbProvider {
    fun get(context: Context) = Room
        .databaseBuilder(
            context,
            FilmDatabase::class.java,
            "film_database"
        )
        .build()
}