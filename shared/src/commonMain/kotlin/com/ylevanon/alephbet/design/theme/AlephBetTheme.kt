package com.ylevanon.alephbet.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import alephbet.shared.generated.resources.Res
import alephbet.shared.generated.resources.noto_sans_hebrew
import org.jetbrains.compose.resources.Font

@Immutable
data class AlephBetPalette(
    val canvas: Color,
    val surface: Color,
    val surfaceRaised: Color,
    val ink: Color,
    val inkMuted: Color,
    val line: Color,
    val primary: Color,
    val primarySoft: Color,
    val accent: Color,
    val accentSoft: Color,
    val gold: Color,
    val focus: Color,
)

@Immutable
data class AlephBetSpacing(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp,
    val xxl: Dp = 48.dp,
)

object AlephBetMotion {
    const val QuickMillis: Int = 140
    const val StandardMillis: Int = 240
    const val CelebrationMillis: Int = 520
}

object AlephBetType {
    val displayWeight: FontWeight = FontWeight(680)
    val bodyWeight: FontWeight = FontWeight(450)
    val niqqudMinimumRenderedSize = 28.sp
}

object AlephBetShape {
    val smallRadius = 12.dp
    val mediumRadius = 20.dp
    val largeRadius = 28.dp
    val pillRadius = 999.dp
}

private val LightPalette = AlephBetPalette(
    canvas = Color(0xFFF7F3EA),
    surface = Color(0xFFFFFCF7),
    surfaceRaised = Color(0xFFFFFFFF),
    ink = Color(0xFF18221F),
    inkMuted = Color(0xFF65706C),
    line = Color(0xFFD9DED8),
    primary = Color(0xFF177B72),
    primarySoft = Color(0xFFDCEBE6),
    accent = Color(0xFFE7735A),
    accentSoft = Color(0xFFF9DED5),
    gold = Color(0xFFD99A32),
    focus = Color(0xFF6558C8),
)

private val DarkPalette = AlephBetPalette(
    canvas = Color(0xFF101715),
    surface = Color(0xFF17211E),
    surfaceRaised = Color(0xFF1F2B27),
    ink = Color(0xFFF5F1E8),
    inkMuted = Color(0xFFA8B2AE),
    line = Color(0xFF33413C),
    primary = Color(0xFF6BC4B7),
    primarySoft = Color(0xFF203D37),
    accent = Color(0xFFF0957E),
    accentSoft = Color(0xFF4A2D28),
    gold = Color(0xFFF0BD63),
    focus = Color(0xFFAAA0F4),
)

private val LightColorScheme = lightColorScheme(
    primary = LightPalette.primary,
    onPrimary = LightPalette.surfaceRaised,
    primaryContainer = LightPalette.primarySoft,
    onPrimaryContainer = LightPalette.ink,
    secondary = LightPalette.accent,
    onSecondary = LightPalette.ink,
    secondaryContainer = LightPalette.accentSoft,
    onSecondaryContainer = LightPalette.ink,
    tertiary = LightPalette.gold,
    onTertiary = LightPalette.ink,
    background = LightPalette.canvas,
    onBackground = LightPalette.ink,
    surface = LightPalette.surface,
    onSurface = LightPalette.ink,
    surfaceVariant = LightPalette.surfaceRaised,
    onSurfaceVariant = LightPalette.inkMuted,
    outline = LightPalette.line,
    inverseSurface = DarkPalette.surface,
    inverseOnSurface = DarkPalette.ink,
    inversePrimary = DarkPalette.primary,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPalette.primary,
    onPrimary = DarkPalette.canvas,
    primaryContainer = DarkPalette.primarySoft,
    onPrimaryContainer = DarkPalette.ink,
    secondary = DarkPalette.accent,
    onSecondary = DarkPalette.canvas,
    secondaryContainer = DarkPalette.accentSoft,
    onSecondaryContainer = DarkPalette.ink,
    tertiary = DarkPalette.gold,
    onTertiary = DarkPalette.canvas,
    background = DarkPalette.canvas,
    onBackground = DarkPalette.ink,
    surface = DarkPalette.surface,
    onSurface = DarkPalette.ink,
    surfaceVariant = DarkPalette.surfaceRaised,
    onSurfaceVariant = DarkPalette.inkMuted,
    outline = DarkPalette.line,
    inverseSurface = LightPalette.surface,
    inverseOnSurface = LightPalette.ink,
    inversePrimary = LightPalette.primary,
)

private val AlephBetTypography = Typography(
    displayLarge = textStyle(AlephBetType.displayWeight, 57, 68),
    displayMedium = textStyle(AlephBetType.displayWeight, 45, 54),
    displaySmall = textStyle(AlephBetType.displayWeight, 36, 44),
    headlineLarge = textStyle(AlephBetType.displayWeight, 32, 40),
    headlineMedium = textStyle(AlephBetType.displayWeight, 28, 36),
    headlineSmall = textStyle(AlephBetType.displayWeight, 24, 32),
    titleLarge = textStyle(AlephBetType.displayWeight, 22, 28),
    titleMedium = textStyle(AlephBetType.displayWeight, 16, 24),
    titleSmall = textStyle(AlephBetType.displayWeight, 14, 20),
    bodyLarge = textStyle(AlephBetType.bodyWeight, 16, 24),
    bodyMedium = textStyle(AlephBetType.bodyWeight, 14, 20),
    bodySmall = textStyle(AlephBetType.bodyWeight, 12, 16),
    labelLarge = textStyle(AlephBetType.displayWeight, 14, 20),
    labelMedium = textStyle(AlephBetType.displayWeight, 12, 16),
    labelSmall = textStyle(AlephBetType.displayWeight, 11, 16),
)

private fun textStyle(weight: FontWeight, sizeSp: Int, lineHeightSp: Int) = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = weight,
    fontSize = sizeSp.sp,
    lineHeight = lineHeightSp.sp,
)

private val AlephBetShapes = Shapes(
    small = RoundedCornerShape(AlephBetShape.smallRadius),
    medium = RoundedCornerShape(AlephBetShape.mediumRadius),
    large = RoundedCornerShape(AlephBetShape.largeRadius),
    extraLarge = RoundedCornerShape(AlephBetShape.largeRadius),
)

private val LocalAlephBetPalette = staticCompositionLocalOf { LightPalette }
private val LocalAlephBetSpacing = staticCompositionLocalOf { AlephBetSpacing() }

val MaterialTheme.alephBetPalette: AlephBetPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalAlephBetPalette.current

val MaterialTheme.alephBetSpacing: AlephBetSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalAlephBetSpacing.current

@Composable
fun alephBetHebrewFontFamily(): FontFamily = FontFamily(
    Font(
        resource = Res.font.noto_sans_hebrew,
        weight = AlephBetType.displayWeight,
    ),
)

@Composable
fun AlephBetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) DarkPalette else LightPalette
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalAlephBetPalette provides palette,
        LocalAlephBetSpacing provides AlephBetSpacing(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AlephBetTypography,
            shapes = AlephBetShapes,
            content = content,
        )
    }
}
