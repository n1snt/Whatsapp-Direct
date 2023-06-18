package com.app.wadirect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.wadirect.ui.theme.WADirectTheme

class MainActivity : ComponentActivity() {

    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = SharedPrefs(this)
        setContent {
            WADirectTheme {
                // A surface container using the 'background' color from the theme
                StatusBarNavbarColors()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UI(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UI(modifier: Modifier = Modifier) {

        var countryCodeVal by remember { mutableStateOf(TextFieldValue(sharedPrefs.getCountryCode()!!)) }
        var phoneNumberVal by remember { mutableStateOf(TextFieldValue()) }
        var messageVal by remember { mutableStateOf(TextFieldValue()) }
        var countryCodeEmpty by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = countryCodeVal,
                    onValueChange = { cc ->
                        Log.d("Country code", cc.toString())
                        countryCodeVal = cc },
                    label = { Text(text = stringResource(R.string.xx)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .width(120.dp)
                        .padding(horizontal = 5.dp),
                    leadingIcon = { Text(text = stringResource(R.string.plus)) },
                    singleLine = true,
                    supportingText = { Text(text = stringResource(R.string.country_code)) }
                )

                OutlinedTextField(
                    value = phoneNumberVal,
                    onValueChange = { number ->
                        Log.d("Phone no", number.text)
                        phoneNumberVal = number },
                    label = { Text(text = stringResource(R.string.phone_number)) },
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
                    Log.d("Message", message.text)
                    messageVal = message },
                label = { Text(stringResource(R.string.message)) },
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
                    countryCodeEmpty = countryCodeVal.text.isBlank()
                    if (!countryCodeEmpty) {
                        sharedPrefs.setCountryCode(countryCodeVal.text)
                        sendRequest(countryCodeVal.text + phoneNumberVal.text, messageVal.text, applicationContext)
                    }
                          },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_send_24),
                    contentDescription = stringResource(R.string.send),
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    "Send",
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WADirectTheme {
            UI()
        }
    }

}
