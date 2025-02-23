package ru.disav.kinopoiskviewer.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ru.disav.kinopoiskviewer.R
import ru.disav.kinopoiskviewer.presentation.ui.components.FilmList
import ru.disav.kinopoiskviewer.presentation.ui.components.SearchBarView

@Composable
internal fun HomeScreen(
    onNavigateToDetails: (Int) -> Unit
) {
    val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effectsFlow.collect {
            when (it) {
                is HomeEffects.NavigateToDetails -> onNavigateToDetails(it.filmId)
                HomeEffects.ShowError -> {
                    Toast.makeText(context, context.getString(R.string.error_message), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    HomeScene(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScene(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    val popularFilms = uiState.popularFilms.collectAsLazyPagingItems()
    val searchResult = uiState.searchResult.collectAsLazyPagingItems()
    var isRefreshing by remember { mutableStateOf(false) }

    val films = if (uiState.searchQuery.isNotEmpty()) {
        searchResult
    } else {
        popularFilms
    }

    LaunchedEffect(popularFilms.loadState.refresh) {
        if (popularFilms.loadState.refresh !is LoadState.Loading) {
            isRefreshing = false
        }

        if (popularFilms.loadState.refresh is LoadState.Error) {
            onEvent(HomeUiEvent.ShowError)
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            popularFilms.refresh()
        }
    )

    Column {
        SearchBarView(
            query = uiState.searchQuery,
            onQueryChange = {
                onEvent(HomeUiEvent.OnSearchQueryChange(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            FilmList(
                films = films,
                onFilmClick = {
                    onEvent(HomeUiEvent.FilmClick(it))
                }
            )

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
