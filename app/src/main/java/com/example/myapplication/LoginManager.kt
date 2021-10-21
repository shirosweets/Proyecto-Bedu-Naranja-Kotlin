package com.example.myapplication

import android.app.Activity
import android.content.Context

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

    fun isPasswordValid(password: String?): Boolean {
        return (password?.length ?: 0) > 7
    }

    fun getPasswordErrorHint(context: Context, password: String?): String? {
        return when {
            password.isNullOrEmpty() -> {
                context.getString(R.string.notice_incomplete_field)
            }
            password.length < 8 -> {
                context.getString(R.string.notice_password_characters_less_than_8)
            }
            else -> {
                null
            }
        }
    }
}