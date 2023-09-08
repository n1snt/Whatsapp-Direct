package com.app.wadirect.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.wadirect.R
import com.app.wadirect.StatusBarNavbarColors
import com.app.wadirect.ui.theme.WADirectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier,
               countryCodeSharedPref: String,
               buttonOnClick: (
                   countryCodeVal: String,
                   phoneNumberVal: String,
                   messageVal: String) -> Unit) {

    var countryCodeVal by remember { mutableStateOf(TextFieldValue(countryCodeSharedPref)) }
    var phoneNumberVal by remember { mutableStateOf(TextFieldValue()) }
    var messageVal by remember { mutableStateOf(TextFieldValue()) }

    var errorDialogBody = remember { "" }
    val errorDialogVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                            stringResource(R.string.whatsapp_direct_message),
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
                    modifier = modifier
                        .padding(paddingValues)
                        .padding(horizontal = 22.dp)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedTextField(
                            value = countryCodeVal,
                            onValueChange = { cc ->
                                countryCodeVal = cc },
                            label = { Text(text = stringResource(R.string.xx),
                                style = MaterialTheme.typography.bodyMedium) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .width(120.dp)
                                .padding(horizontal = 5.dp),
                            leadingIcon = { Text(text = stringResource(R.string.plus),
                                style = MaterialTheme.typography.labelLarge) },
                            singleLine = true,
                            supportingText = { Text(text = stringResource(R.string.country_code),
                                style = MaterialTheme.typography.labelSmall) }
                        )

                        OutlinedTextField(
                            value = phoneNumberVal,
                            onValueChange = { number ->
                                phoneNumberVal = number },
                            label = { Text(text = stringResource(R.string.phone_number),
                                style = MaterialTheme.typography.bodyMedium) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 5.dp),
                            singleLine = true
                        )

                    }

                    OutlinedTextField(
                        value = messageVal,
                        onValueChange = { message ->
                            messageVal = message },
                        label = { Text(
                            stringResource(R.string.message),
                            style = MaterialTheme.typography.bodyMedium) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                            .padding(top = 5.dp)
                            .height(100.dp),
                        maxLines = 5
                    )

                    Button(
                        onClick = {
                            if (countryCodeVal.text.isBlank()) {
                                errorDialogBody = context.resources.getString(R.string.country_code_is_empty)
                                errorDialogVisible.value = true
                            } else if(phoneNumberVal.text.isBlank()) {
                                errorDialogBody = context.resources.getString(R.string.phone_number_is_empty)
                                errorDialogVisible.value = true
                            } else {
                                buttonOnClick(
                                    countryCodeVal.text,
                                    messageVal.text,
                                    phoneNumberVal.text
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.outline_send_24),
                            contentDescription = stringResource(R.string.send),
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            stringResource(R.string.send),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(
                                vertical = 10.dp,
                                horizontal = 5.dp),
                        )
                    }
                    ErrorDialog(
                        body = errorDialogBody,
                        visible = errorDialogVisible
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(countryCodeSharedPref = "", buttonOnClick = { _,_,_ -> })
}
