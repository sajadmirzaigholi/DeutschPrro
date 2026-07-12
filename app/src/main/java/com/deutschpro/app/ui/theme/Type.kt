package com.deutschpro.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Font strategy
 * -------------
 * DeutschPro is designed to use "Vazirmatn" (SIL OFL licensed), a free
 * variable font with excellent Persian + Latin glyph coverage — Latin
 * coverage matters here because German words appear inline constantly.
 *
 * To enable it:
 *  1. Download the static weights from https://github.com/rastikerdar/vazirmatn
 *     (Regular, Medium, SemiBold, Bold .ttf files).
 *  2. Place them under app/src/main/res/font/ as:
 *     vazirmatn_regular.ttf, vazirmatn_medium.ttf,
 *     vazirmatn_semibold.ttf, vazirmatn_bold.ttf
 *  3. Replace the `FontFamily.Default` fallback below with:
 *
 *     val VazirmatnFontFamily = FontFamily(
 *         Font(R.font.vazirmatn_regular, FontWeight.Normal),
 *         Font(R.font.vazirmatn_medium, FontWeight.Medium),
 *         Font(R.font.vazirmatn_semibold, FontWeight.SemiBold),
 *         Font(R.font.vazirmatn_bold, FontWeight.Bold)
 *     )
 *
 * Until then the app builds and runs fine using the system font so this
 * is a drop-in, zero-risk upgrade rather than a blocker.
 */
val VazirmatnFontFamily = FontFamily.Default

val DeutschProTypography = Typography(
    displayLarge = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 42.sp),
    headlineLarge = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 36.sp),
    headlineMedium = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Medium, fontSize = 17.sp, lineHeight = 24.sp),
    bodyLarge = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 22.sp),
    labelLarge = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = VazirmatnFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp)
)
