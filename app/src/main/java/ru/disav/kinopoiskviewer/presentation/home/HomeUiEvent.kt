package ru.disav.kinopoiskviewer.presentation.home

sealed class HomeUiEvent {
    data class OnSearchQueryChange(val query: String) : HomeUiEvent()
    data class FilmClick(val filmId: Int) : HomeUiEvent()
    data object ShowError : HomeUiEvent()
}