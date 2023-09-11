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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.wadirect.screens.ErrorScreen
import com.app.wadirect.screens.MainScreen

class MainActivity: ComponentActivity() {

    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = SharedPrefs(this)
        getVibrator()
        val whatsappInstalled = isWhatsAppInstalled(this)
        setContent {
            val navController = rememberNavController()
            val startDestination = if (whatsappInstalled) { MAIN_SCREEN } else { TEXT_SCREEN }
            Navigation(navController = navController, startDestination = startDestination)
        }
    }

    @Composable
    private fun Navigation(navController: NavHostController, startDestination: String) {
        val context = LocalContext.current
        NavHost(navController = navController, startDestination = startDestination) {
            composable(MAIN_SCREEN) {
                MainScreen(countryCodeSharedPref = sharedPrefs.getCountryCode().toString(),
                    buttonOnClick = { countryCodeVal: String, phoneNumberVal: String, messageVal: String ->
                        onSend(countryCodeVal, phoneNumberVal, messageVal)
                    }
                )
            }
            composable(TEXT_SCREEN) {
                ErrorScreen(
                    title = stringResource(R.string.error),
                    message = stringResource(R.string.whatsapp_not_installed_description),
                    onButtonClick = { openPlayStore(context, "com.whatsapp") },
                )
            }
        }

        val lifecycleEvent = rememberLifecycleEvent()
        LaunchedEffect(lifecycleEvent) {
            if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
                navigateOnStateChange(navController)
            }
        }
    }

    /**
     * Navigates to MAIN_SCREEN if whatsapp is installed
     * Else navigate to TEXT_SCREEN
     */
    private fun navigateOnStateChange(navController: NavHostController) {
        navController.popBackStack()
        if (isWhatsAppInstalled(this)) {
            navController.navigate(MAIN_SCREEN)
        } else {
            navController.navigate(TEXT_SCREEN)
        }
    }

    /**
     * Launches whatsapp of the phone number passed
     * @param countryCodeVal
     * @param phoneNumber
     * @param message
     */
    private fun onSend(countryCodeVal: String, phoneNumber: String, message: String) {
        sharedPrefs.setCountryCode(countryCodeVal)
        vibrateOnButton()
        val intent = Intent(Intent.ACTION_VIEW)
        val phoneNumberVal = countryCodeVal + phoneNumber
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${phoneNumberVal}&text=${Uri.encode(message)}")
        startActivity(intent)
    }


    /**
     * Initializes vibration.
     */
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

    /**
     * This function vibrates the device when button is pressed.
     */
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