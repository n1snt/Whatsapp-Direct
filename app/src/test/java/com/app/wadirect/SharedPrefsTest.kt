package com.app.wadirect

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class SharedPrefsTest {

    @Test
    fun `setCountryCode should save the country code in shared preferences`() {
        val mockContext = mock(Context::class.java)
        val mockSharedPreferencesEditor = mock(SharedPreferences.Editor::class.java)
        val mockSharedPreferences = mock(SharedPreferences::class.java)

        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor)

        val sharedPrefs = SharedPrefs(mockContext)
        val countryCode = "1"

        sharedPrefs.setCountryCode(countryCode)

        verify(mockSharedPreferencesEditor).putString("country_code", countryCode)
        verify(mockSharedPreferencesEditor).apply()
    }

    @Test
    fun `getCountryCode should return the saved country code from shared preferences`() {
        val mockContext = mock(Context::class.java)
        val mockSharedPreferences = mock(SharedPreferences::class.java)

        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.getString("country_code", "")).thenReturn("1")

        val sharedPrefs = SharedPrefs(mockContext)

        val countryCode = sharedPrefs.getCountryCode()

        assertEquals("1", countryCode)
    }
}