package ru.disav.kinopoiskviewer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.disav.kinopoiskviewer.data.mappers.toFilm
import ru.disav.kinopoiskviewer.data.network.KinopoiskApi
import ru.disav.kinopoiskviewer.models.Film
import java.io.IOException

class SearchPagingSource(
    private val api: KinopoiskApi,
    private val query: String
) : PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        return try {
            val response = api.searchFilms(query, page)
            if (!response.isSuccessful) {
                return LoadResult.Error(HttpException(response))
            }

            val films = response.body()?.films?.map { it.toFilm() } ?: emptyList()
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (films.isNotEmpty()) page + 1 else null

            LoadResult.Page(
                data = films,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}