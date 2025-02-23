package ru.disav.kinopoiskviewer.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.disav.kinopoiskviewer.data.network.dto.FilmDetailsDto
import ru.disav.kinopoiskviewer.data.network.dto.FilmsResponseDto

interface KinopoiskApi {
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopularFilms(
        @Query("page") page: Int
    ): FilmsResponseDto

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmDetails(
        @Path("id") id: Int
    ): FilmDetailsDto

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchFilms(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): Response<FilmsResponseDto>
}