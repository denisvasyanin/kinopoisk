package ru.disav.kinopoiskviewer.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.disav.kinopoiskviewer.data.db.FilmDatabase
import ru.disav.kinopoiskviewer.data.mappers.toFilm
import ru.disav.kinopoiskviewer.data.mappers.toFilmDetails
import ru.disav.kinopoiskviewer.data.network.KinopoiskApi

class FilmRepository(
    private val api: KinopoiskApi,
    private val db: FilmDatabase
) {
    private val filmDao = db.filmDao()

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularFilms() = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = FilmRemoteMediator(api, db),
        pagingSourceFactory = {
            filmDao.pagingSource()
        }
    )
        .flow
        .map {
            it.map { filmEntity -> filmEntity.toFilm() }
        }

    fun getFilmDetails(filmId: Int) = flow {
        val response = api.getFilmDetails(filmId)
        emit(response.toFilmDetails())
    }

    fun searchFilms(query: String) = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { SearchPagingSource(api, query) }
    ).flow


    companion object {
        const val PAGE_SIZE = 20
    }
}
