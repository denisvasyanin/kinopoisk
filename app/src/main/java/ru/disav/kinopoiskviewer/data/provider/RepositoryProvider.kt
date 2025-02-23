package ru.disav.kinopoiskviewer.data.provider

import ru.disav.kinopoiskviewer.data.FilmRepository

interface RepositoryProvider {
    fun provideRepository(): FilmRepository
}