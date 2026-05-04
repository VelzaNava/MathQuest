package com.mathquest.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.mathquest.app.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val Baloo2 = FontFamily(
    Font(googleFont = GoogleFont("Baloo 2"), fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(googleFont = GoogleFont("Baloo 2"), fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = GoogleFont("Baloo 2"), fontProvider = provider, weight = FontWeight.Medium),
)

val Nunito = FontFamily(
    Font(googleFont = GoogleFont("Nunito"), fontProvider = provider, weight = FontWeight.ExtraBold),
    Font(googleFont = GoogleFont("Nunito"), fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = GoogleFont("Nunito"), fontProvider = provider, weight = FontWeight.SemiBold),
    Font(googleFont = GoogleFont("Nunito"), fontProvider = provider, weight = FontWeight.Normal),
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 52.sp,
        color = TextDark
    ),
    displayMedium = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
        color = TextDark
    ),
    displaySmall = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        color = TextDark
    ),
    headlineLarge = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = TextDark
    ),
    headlineMedium = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp,
        color = TextDark
    ),
    headlineSmall = TextStyle(
        fontFamily = Baloo2,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = TextDark
    ),
    titleLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextDark
    ),
    titleMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = TextDark,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        color = TextDark,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = TextDark,
        lineHeight = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = TextMid,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = TextLight
    )
)
