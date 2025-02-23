package ru.disav.kinopoiskviewer.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val filmId: Int) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<DetailsRoute>()
    }
}

fun NavController.navigateToDetails(filmId: Int) {
    this.navigate(DetailsRoute(filmId))
}

fun NavGraphBuilder.detailsScreen(
    onNavigateBack: () -> Unit
) {
    composable<DetailsRoute> {
        DetailsScreen(
            onNavigateBack = onNavigateBack
        )
    }
}


