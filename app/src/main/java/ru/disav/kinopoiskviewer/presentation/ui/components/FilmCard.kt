package ru.disav.kinopoiskviewer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.valentinilk.shimmer.shimmer
import ru.disav.kinopoiskviewer.R
import ru.disav.kinopoiskviewer.models.Film

@Composable
fun FilmCard(
    film: Film,
    onFilmClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onFilmClick(film.filmId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth(0.3f)
                    .height(150.dp),
                model = film.posterUrlPreview,
                loading = {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .fillMaxSize()
                            .background(Color.LightGray),
                    )
                },
                contentScale = ContentScale.Crop,
                contentDescription = film.nameRu,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = film.nameRu,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(R.string.year_s, film.year ?: stringResource(R.string.not_available)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(R.string.rating_s, film.rating ?: stringResource(R.string.not_available)),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}