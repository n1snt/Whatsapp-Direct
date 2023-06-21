package com.app.wadirect

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun ErrorDialog(
    Modifier: Modifier,
    onDismissRequest: Unit,
    showDialog: Boolean,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismissRequest },
            confirmButton = {},
            modifier = Modifier
        )

    }
}