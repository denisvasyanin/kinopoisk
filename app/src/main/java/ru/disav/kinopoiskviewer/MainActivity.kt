package ru.disav.kinopoiskviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.disav.kinopoiskviewer.presentation.details.detailsScreen
import ru.disav.kinopoiskviewer.presentation.details.navigateToDetails
import ru.disav.kinopoiskviewer.presentation.home.HomeRoute
import ru.disav.kinopoiskviewer.presentation.home.homeScreen
import ru.disav.kinopoiskviewer.presentation.ui.theme.KinopoiskViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            KinopoiskViewerTheme {
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        startDestination = HomeRoute,
                    ) {
                        homeScreen(
                            onNavigateToDetails = { filmId ->
                                navController.navigateToDetails(filmId)
                            }
                        )

                        detailsScreen(
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}