package ru.disav.kinopoiskviewer.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.disav.kinopoiskviewer.data.db.entity.FilmEntity

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmEntity>)

    @Query("SELECT * FROM films")
    fun pagingSource(): PagingSource<Int, FilmEntity>

    @Query("DELETE FROM films")
    suspend fun clearAll()
}