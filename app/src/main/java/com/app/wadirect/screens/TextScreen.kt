package com.app.wadirect.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.wadirect.R
import com.app.wadirect.StatusBarNavbarColors
import com.app.wadirect.ui.theme.WADirectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextScreen(title: String, message: String, buttonCallback: () -> Unit) {
    WADirectTheme {
        StatusBarNavbarColors()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(
                            title,
                            style = MaterialTheme.typography.titleLarge)
                    },
                        modifier = Modifier
                            .padding(10.dp)
                            .padding(top = 10.dp),
                    )
                },
            ) { paddingValues ->


                Text(text = stringResource(R.string.actionbar_sub_text),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 22.dp)
                        .fillMaxHeight()
                ) {
                    // TODO: Add here
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextScreenPreview() {
    TextScreen("Error", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries") {}
}