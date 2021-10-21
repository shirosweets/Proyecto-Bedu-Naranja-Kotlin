package com.example.myapplication

import android.app.Activity

object LoginManager {
    private const val LOGIN_KEY = "org.bedu.app.LoginManager.LOGIN_PREF_KEY"

    private fun setLogin(activity: Activity, loggedIn: Boolean) {
        ConfigManager.prefs(activity).edit().putBoolean(LOGIN_KEY, loggedIn).apply()
    }

    fun isLoggedIn(activity: Activity): Boolean {
        return ConfigManager.prefs(activity).getBoolean(LOGIN_KEY, false)
    }

    fun logIn(activity: Activity) = setLogin(activity, true)

    fun logOut(activity: Activity) = setLogin(activity, false)
}