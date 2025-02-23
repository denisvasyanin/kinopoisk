package ru.disav.kinopoiskviewer.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.disav.kinopoiskviewer.data.DataState
import ru.disav.kinopoiskviewer.data.FilmRepository
import ru.disav.kinopoiskviewer.data.provider.RepositoryProvider
import ru.disav.kinopoiskviewer.models.FilmDetails

class DetailsViewModel(
    private val repository: FilmRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _filmDetails = MutableStateFlow<DataState<FilmDetails>>(DataState.Loading)
    val filmDetails = _filmDetails.asStateFlow()

    init {
        loadFilmDetails()
    }

    fun loadFilmDetails() {
        val filmId = DetailsRoute.from(savedStateHandle).filmId

        viewModelScope.launch {
            repository
                .getFilmDetails(filmId)
                .catch { err ->
                    _filmDetails.update {
                        DataState.Failure(err)
                    }
                }
                .onStart {
                    _filmDetails.update {
                        DataState.Loading
                    }
                }
                .collect { details ->
                    _filmDetails.update {
                        DataState.Done(details)
                    }
                }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val savedStateHandle = createSavedStateHandle()
                DetailsViewModel(
                    (application as RepositoryProvider).provideRepository(),
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}