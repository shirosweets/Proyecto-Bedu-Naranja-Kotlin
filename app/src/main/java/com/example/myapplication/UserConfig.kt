package com.example.myapplication

import android.content.Context
import androidx.preference.PreferenceManager


class UserConfig {
    companion object {
        private const val IS_DARK_THEME_DEFAULT: Boolean = true

        fun setTheme(context: Context, isDark: Boolean) {
            val userPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val darkThemePrefKey = context.getString((R.string.pref_key_DARK_THEME))
            userPreferences.edit().putBoolean(darkThemePrefKey, isDark).apply()
        }

        fun isDarkTheme(context: Context): Boolean {
            val userPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val darkThemePrefKey = context.getString((R.string.pref_key_DARK_THEME))
            return userPrefs.getBoolean(darkThemePrefKey, IS_DARK_THEME_DEFAULT)
        }

        fun getThemeResourceId(context: Context): Int {
            return if(isDarkTheme(context)) R.style.Theme_Dark else R.style.Theme_Light
        }

        fun switchTheme(context: Context) = setTheme(context, !isDarkTheme(context))
    }
}
