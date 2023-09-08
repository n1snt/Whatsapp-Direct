package com.app.wadirect

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.wadirect.screens.MainScreen
import com.app.wadirect.screens.TextScreen

class MainActivity: ComponentActivity() {

    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = SharedPrefs(this)
        getVibrator()

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = MAIN_SCREEN) {
                composable(MAIN_SCREEN) {
                    MainScreen(countryCodeSharedPref = sharedPrefs.getCountryCode().toString(),
                        buttonOnClick = { countryCodeVal: String, phoneNumberVal: String, messageVal: String ->
                            onSend(countryCodeVal, phoneNumberVal, messageVal)
                        }
                    )
                }
                composable(TEXT_SCREEN) {
                    TextScreen(title = "Error", message = "Whatsapp is not installed") {
                        Log.d("HI", "HELLO")
                    }
                }
            }
        }
    }

    private fun onSend(countryCodeVal: String, phoneNumberVal: String, messageVal: String) {
        sharedPrefs.setCountryCode(countryCodeVal)
        vibrateOnButton()
        sendRequest(countryCodeVal + phoneNumberVal, messageVal)
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

    companion object {
        const val MAIN_SCREEN = "main"
        const val TEXT_SCREEN = "text_screen"
    }

}