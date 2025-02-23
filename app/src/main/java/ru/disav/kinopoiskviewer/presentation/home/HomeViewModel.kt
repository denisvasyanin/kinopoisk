package ru.disav.kinopoiskviewer.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.cachedIn
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.disav.kinopoiskviewer.data.FilmRepository
import ru.disav.kinopoiskviewer.data.provider.RepositoryProvider

class HomeViewModel(
    private val repository: FilmRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            popularFilms = repository
                .getPopularFilms()
                .cachedIn(viewModelScope)
        )
    )
    val uiState = _uiState.asStateFlow()

    private val effectsChannel = Channel<HomeEffects>()
    val effectsFlow = effectsChannel.receiveAsFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnSearchQueryChange -> onSearchQueryChange(event.query)
            is HomeUiEvent.FilmClick -> {
                effectsChannel.trySend(HomeEffects.NavigateToDetails(event.filmId))
            }

            HomeUiEvent.ShowError -> {
                effectsChannel.trySend(HomeEffects.ShowError)
            }
        }
    }

    private fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
        if (query.isNotBlank()) {
            searchFilms(query)
        } else {
            _uiState.update {
                it.copy(
                    searchResult = emptyFlow()
                )
            }
        }
    }

    private fun searchFilms(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchResult = repository.searchFilms(query).cachedIn(viewModelScope)
                )
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                HomeViewModel((application as RepositoryProvider).provideRepository())
            }
        }
    }
}