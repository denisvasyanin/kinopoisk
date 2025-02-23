package ru.disav.kinopoiskviewer.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.disav.kinopoiskviewer.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_message),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onTryAgainClick()
            },
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(
                    text = stringResource(R.string.try_again),
                )
            }
        )
    }
}
