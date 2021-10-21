package com.example.myapplication

import android.content.Context
import androidx.preference.PreferenceManager
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*


class UserConfig {
    companion object {
        private const val IS_DARK_THEME_DEFAULT: Boolean = true
        private const val DEFAULT_LANGUAGE: String = "es"

        fun setLocale(activity: Activity, languageCode: String?) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val resources: Resources = activity.resources
            val config: Configuration = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        fun setLanguage(context: Context, iso_lan: String){
            val userPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            userPrefs.edit().putString(
                context.getString(R.string.pref_key_ISO_Language), iso_lan).apply()
        }

        fun getLanguage(context: Context): String{
            val userPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val isoLanguage = context.getString(R.string.pref_key_ISO_Language)
            return userPrefs.getString(isoLanguage, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        }

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
