package ru.disav.kinopoiskviewer.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.rippledClickable(onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(),
        onClick = onClick
    )
}

fun Modifier.rippledClickable(
    enabled: Boolean,
    onClick: () -> Unit
) = composed {
    if (enabled) this.rippledClickable(onClick) else this
}

fun Modifier.noRippledClickable(onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}
