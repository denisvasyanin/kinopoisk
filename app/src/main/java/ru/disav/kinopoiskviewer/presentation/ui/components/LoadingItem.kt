package ru.disav.kinopoiskviewer.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.disav.kinopoiskviewer.presentation.ui.theme.KinopoiskViewerTheme

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp)
    )
}

@Preview
@Composable
private fun LoadingItemPreview() = KinopoiskViewerTheme {
    LoadingItem()
}