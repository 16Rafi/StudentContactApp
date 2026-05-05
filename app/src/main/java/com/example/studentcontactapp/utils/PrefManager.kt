package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class PrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val USERNAME = "username"
        private const val IS_REMEMBER_ME = "isRememberMe"
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun setUsername(username: String) {
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(USERNAME, null)
    }

    fun setRememberMe(isRememberMe: Boolean) {
        editor.putBoolean(IS_REMEMBER_ME, isRememberMe)
        editor.apply()
    }

    fun isRememberMe(): Boolean {
        return sharedPreferences.getBoolean(IS_REMEMBER_ME, false)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
