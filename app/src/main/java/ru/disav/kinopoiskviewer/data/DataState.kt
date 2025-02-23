package ru.disav.kinopoiskviewer.data

sealed interface DataState<out T> {
    data object Loading : DataState<Nothing>
    data class Failure(val errorMessage: Throwable) : DataState<Nothing>
    data class Done<T>(val data: T) : DataState<T>
}