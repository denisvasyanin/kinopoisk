package ru.disav.kinopoiskviewer.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import ru.disav.kinopoiskviewer.data.db.FilmDatabase
import ru.disav.kinopoiskviewer.data.db.entity.FilmEntity
import ru.disav.kinopoiskviewer.data.db.entity.RemoteKeys
import ru.disav.kinopoiskviewer.data.mappers.toFilmEntity
import ru.disav.kinopoiskviewer.data.network.KinopoiskApi
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FilmRemoteMediator(
    private val api: KinopoiskApi,
    private val db: FilmDatabase
) : RemoteMediator<Int, FilmEntity>() {

    private val filmDao = db.filmDao()
    private val remoteKeysDao = db.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val filmsResponseDto = api.getPopularFilms(page)
            val films = filmsResponseDto.films.map { it.toFilmEntity() }

            val endOfPaginationReached = filmsResponseDto.pagesCount == page

            val prevPage = if (page == 1) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    filmDao.clearAll()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = filmsResponseDto.films.map {
                    RemoteKeys(
                        id = it.filmId,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                filmDao.insertAll(films)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FilmEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.filmId?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id.toInt())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, FilmEntity>
    ): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(id = it.filmId.toInt())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, FilmEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                remoteKeysDao.getRemoteKeys(id = it.filmId.toInt())
            }
    }

}