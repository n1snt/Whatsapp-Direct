package com.app.wadirect

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun StatusBarNavbarColors() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.background, !isSystemInDarkTheme()
    )
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.background, !isSystemInDarkTheme()
    )
}