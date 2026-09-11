package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = MayaPrimaryDark,
    onPrimary = MayaOnPrimaryDark,
    primaryContainer = MayaPrimaryContainerDark,
    onPrimaryContainer = MayaOnPrimaryContainerDark,
    secondary = MayaSecondaryDark,
    onSecondary = MayaOnSecondaryDark,
    secondaryContainer = MayaSecondaryContainerDark,
    onSecondaryContainer = MayaOnSecondaryContainerDark,
    background = MayaBackgroundDark,
    surface = MayaSurfaceDark,
    surfaceVariant = MayaSurfaceVariantDark,
    onSurfaceVariant = MayaOnSurfaceVariantDark,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MayaPrimaryLight,
    onPrimary = MayaOnPrimaryLight,
    primaryContainer = MayaPrimaryContainerLight,
    onPrimaryContainer = MayaOnPrimaryContainerLight,
    secondary = MayaSecondaryLight,
    onSecondary = MayaOnSecondaryLight,
    secondaryContainer = MayaSecondaryContainerLight,
    onSecondaryContainer = MayaOnSecondaryContainerLight,
    background = MayaBackgroundLight,
    surface = MayaSurfaceLight,
    surfaceVariant = MayaSurfaceVariantLight,
    onSurfaceVariant = MayaOnSurfaceVariantLight,
  )

@Composable
fun MayaAssistantTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  MayaAssistantTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
