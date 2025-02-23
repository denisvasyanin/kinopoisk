package ru.disav.kinopoiskviewer.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ru.disav.kinopoiskviewer.models.Film

@Composable
fun FilmList(
    films: LazyPagingItems<Film>,
    onFilmClick: (Int) -> Unit
) = LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    items(films.itemCount) { index ->
        val film = films[index]
        if (film != null) {
            FilmCard(film = film, onFilmClick = onFilmClick)
        }
    }

    films.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
            }

            loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }

            loadState.refresh is LoadState.Error -> {
                item {
                    ErrorView(
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        retry()
                    }
                }
            }

            loadState.append is LoadState.Error -> {
                val e = films.loadState.append as LoadState.Error
                item {
                    ErrorItem(
                        message = e.error.localizedMessage!!,
                        onRetry = { retry() }
                    )
                }
            }
        }
    }
}
