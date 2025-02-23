package ru.disav.kinopoiskviewer.presentation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    onNavigateToDetails: (Int) -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            onNavigateToDetails = onNavigateToDetails
        )
    }
}


