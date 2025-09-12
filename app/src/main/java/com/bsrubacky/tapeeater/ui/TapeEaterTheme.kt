package com.bsrubacky.tapeeater.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(

)

private val LightColorScheme = lightColorScheme(
)


@Composable
fun TapeEaterTheme(content: @Composable() () -> Unit) {

    val inDarkMode: Boolean = isSystemInDarkTheme()

    val colors = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (inDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (inDarkMode) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )

}