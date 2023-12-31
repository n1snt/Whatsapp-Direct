package com.app.wadirect

import android.content.Context

/**
 * This class provides an interface to read & write to SharedPreferences
 * @param context
 */
class SharedPrefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("WADirectPrefs", Context.MODE_PRIVATE)

    fun setCountryCode(countryCode: String) {
        val editor = sharedPreferences.edit()
        editor.putString("country_code", countryCode)
        editor.apply()
    }

    fun getCountryCode(): String? {
        return sharedPreferences.getString("country_code", "")
    }

}