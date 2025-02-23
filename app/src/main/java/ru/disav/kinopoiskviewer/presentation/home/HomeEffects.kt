package ru.disav.kinopoiskviewer.presentation.home

sealed interface HomeEffects {
    data class NavigateToDetails(val filmId: Int) : HomeEffects
    data object ShowError : HomeEffects
}