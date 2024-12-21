package com.example.leitner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.leitner.R



// Light theme colors
private val LightColors = lightColorScheme(
    primary = AnkiBlue,
    onPrimary = Color.White,
    primaryContainer = AnkiLightBlue,
    onPrimaryContainer = AnkiBlack,
    secondary = Color(0xFFFAF0E6),
    onSecondary = Color(0xFFFAF0E6),
    secondaryContainer = Color(0xFFFAF0E6),
    onSecondaryContainer = AnkiBlack,
    background = Color.White,
    onBackground = AnkiBlack,
    surface = Color.White,
    onSurface = AnkiBlack,
    error = AnkiRed,
    onError = Color.White,
    surfaceVariant = Color.White,
    onSurfaceVariant = untherJetWhite,
    scrim = untherJetWhite
)

// Dark theme colors
private val DarkColors = darkColorScheme(
    primary = AnkiLightBlue,
    onPrimary = AnkiBlack,
    primaryContainer = AnkiBlue,
    onPrimaryContainer = Color.White,
    secondary = AnkiGrey,
    onSecondary = AnkiBlack,
    secondaryContainer = AnkiGrey,
    onSecondaryContainer = Color.White,
    background = AnkiBlack,
    onBackground = Color.White,
    surface = AnkiBlack,
    onSurface = Color.White,
    error = AnkiRed,
    onError = AnkiBlack,
    surfaceVariant = jetBlack,
    onSurfaceVariant = untherJetBlack,
    scrim = boxReviewColor
)

val iranyekanmobile = FontFamily(
    Font(R.font.iranyekanmobilebold),
    Font(R.font.iranyekanmobileextrabold),
    Font(R.font.iranyekanmobilemedium),
    Font(R.font.iranyekanmobileregular),
)

// تعریف تایپوگرافی
private val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = iranyekanmobile,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        fontFamily = iranyekanmobile,
        letterSpacing = 1.25.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        fontFamily = iranyekanmobile,
        letterSpacing = 0.4.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = iranyekanmobile,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = iranyekanmobile,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = iranyekanmobile,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

@Composable
fun LeitnerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
