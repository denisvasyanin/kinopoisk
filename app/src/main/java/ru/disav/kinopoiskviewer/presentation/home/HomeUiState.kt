package ru.disav.kinopoiskviewer.presentation.home

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.disav.kinopoiskviewer.models.Film

data class HomeUiState(
    val searchQuery: String = "",
    val popularFilms: Flow<PagingData<Film>> = emptyFlow(),
    val searchResult: Flow<PagingData<Film>> = emptyFlow()
)