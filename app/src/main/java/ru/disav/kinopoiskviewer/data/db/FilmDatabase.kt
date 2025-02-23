package ru.disav.kinopoiskviewer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.disav.kinopoiskviewer.data.db.entity.FilmEntity
import ru.disav.kinopoiskviewer.data.db.entity.RemoteKeys

@Database(
    entities = [FilmEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
    abstract fun remoteKeysDao(): RemoteKeyDao
}