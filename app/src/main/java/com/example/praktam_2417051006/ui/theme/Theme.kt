package com.example.praktam_2417051006.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    secondary = PinkSecondary,
    background = CreamBackground,
    surfaceVariant = LilacContainer,
    secondaryContainer = BlueContainer,
    onPrimary = OnPrimaryText
)

@Composable
fun PrakTAM_2417051006Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
