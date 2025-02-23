package ru.disav.kinopoiskviewer.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import ru.disav.kinopoiskviewer.R
import ru.disav.kinopoiskviewer.data.DataState
import ru.disav.kinopoiskviewer.models.FilmDetails
import ru.disav.kinopoiskviewer.presentation.ui.components.ErrorView
import ru.disav.kinopoiskviewer.presentation.ui.components.noRippledClickable
import ru.disav.kinopoiskviewer.presentation.ui.theme.KinopoiskViewerTheme

@Composable
internal fun DetailsScreen(
    onNavigateBack: () -> Unit
) {
    val viewModel = viewModel<DetailsViewModel>(factory = DetailsViewModel.Factory)

    val filmDetails by viewModel.filmDetails.collectAsStateWithLifecycle()

    DetailsScene(
        state = filmDetails,
        onBackClick = onNavigateBack,
        onRetry = viewModel::loadFilmDetails
    )
}

@Composable
private fun DetailsScene(
    state: DataState<FilmDetails>,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier.noRippledClickable {
                    onBackClick()
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = (state as? DataState.Done)?.data?.nameRu.orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        when (state) {
            is DataState.Done -> {
                val filmDetails = state.data

                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {

                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            model = filmDetails.posterUrl,
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .shimmer()
                                        .padding(horizontal = 32.dp)
                                        .height(350.dp)
                                        .fillMaxWidth()
                                        .background(Color.LightGray),
                                )
                            },
                            contentDescription = null,
                        )

                        Text(
                            text = stringResource(R.string.film_description),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = filmDetails.description,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = stringResource(R.string.film_genre),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = filmDetails.genres.joinToString(", "),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = stringResource(R.string.film_country),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = filmDetails.countries.joinToString(", "),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                }
            }

            is DataState.Failure -> {
                ErrorView(
                    onTryAgainClick = onRetry
                )
            }

            DataState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScenePreview() {
    KinopoiskViewerTheme {
        DetailsScene(
            state = DataState.Loading,
//            state = DataState.Done(
//                FilmDetails(
//                    filmId = 1,
//                    nameRu = "Film Name",
//                    posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1346361.jpg",
//                    description = "Film Description",
//                    genres = listOf("Action", "Drama"),
//                    countries = listOf("USA", "Canada")
//                )
//            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}