package ru.disav.kinopoiskviewer

import android.app.Application
import ru.disav.kinopoiskviewer.data.provider.DbProvider
import ru.disav.kinopoiskviewer.data.FilmRepository
import ru.disav.kinopoiskviewer.data.provider.RepositoryProvider
import ru.disav.kinopoiskviewer.data.provider.RetrofitProvider

class MyApplication : Application(), RepositoryProvider {
    private lateinit var filmRepository: FilmRepository

    override fun onCreate() {
        super.onCreate()
        filmRepository = FilmRepository(
            api = RetrofitProvider.get(),
            db = DbProvider.get(this)
        )
    }

    override fun provideRepository() = filmRepository
}

