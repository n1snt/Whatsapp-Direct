package com.app.wadirect

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController


fun sendRequest(phoneNumber: String, message: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${phoneNumber}&text=${Uri.encode(message)}")
    context.startActivity(intent)
}

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