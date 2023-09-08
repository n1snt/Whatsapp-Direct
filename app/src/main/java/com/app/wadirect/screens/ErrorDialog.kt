package com.app.wadirect.screens

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.wadirect.R

@Composable
fun ErrorDialog(body: String, visible: MutableState<Boolean>) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        visible.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.close))
                }
            },
            title = { Text(text = stringResource(R.string.error)) },
            text = { Text(text = body) },
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ErrorDialogPreview() {
    ErrorDialog(body = "Error Occurred", mutableStateOf(true))
}
