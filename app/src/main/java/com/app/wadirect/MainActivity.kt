package com.app.wadirect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.wadirect.ui.theme.WADirectTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity: ComponentActivity() {

    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = SharedPrefs(this)
        getVibrator()
        setContent {
            WADirectTheme {
                // A surface container using the 'background' color from the theme
                StatusBarNavbarColors()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text(getString(R.string.whatsapp_direct_message),
                                    style = MaterialTheme.typography.titleMedium)
                            },
                                modifier = Modifier.padding(10.dp),
                            )
                        },
                    ) { paddingValues ->  
                        Text(text = getString(R.string.actionbar_sub_text),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(paddingValues)
                                .padding(horizontal = 20.dp)
                        )
                    }
                    UI(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }


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
                label = { Text(stringResource(R.string.message),
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
                    countryCodeEmpty = countryCodeVal.text.isBlank()
                    if (!countryCodeEmpty) {
                        sharedPrefs.setCountryCode(countryCodeVal.text)
                        vibrateOnButton()
                        sendRequest(countryCodeVal.text + phoneNumberVal.text, messageVal.text)
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
                    stringResource(R.string.send),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 5.dp),
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

    private fun getVibrator() {
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
    }

    private fun sendRequest(phoneNumber: String, message: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${phoneNumber}&text=${Uri.encode(message)}")
        startActivity(intent)
    }

    @Suppress("DEPRECATION")
    private fun vibrateOnButton() {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, 50))
            } else {
                vibrator.vibrate(500, null)
            }
        }
    }

}