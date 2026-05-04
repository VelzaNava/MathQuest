package com.mathquest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MathQuestColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = PurpleSoft,
    secondary = Pink,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = Green,
    background = Cream,
    onBackground = TextDark,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = TextDark,
    error = WrongRed,
    onError = androidx.compose.ui.graphics.Color.White,
)

@Composable
fun MathQuestTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MathQuestColorScheme,
        typography = AppTypography,
        content = content
    )
}
