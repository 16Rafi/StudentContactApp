package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class SettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SettingsPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val DARK_MODE = "dark_mode"
        private const val FONT_SIZE = "font_size"
        private const val NOTIFICATION_ENABLED = "notification_enabled"
    }

    var isDarkMode: Boolean
        get() = sharedPreferences.getBoolean(DARK_MODE, false)
        set(value) {
            editor.putBoolean(DARK_MODE, value)
            editor.apply()
        }

    var fontSize: Int
        get() = sharedPreferences.getInt(FONT_SIZE, 14)
        set(value) {
            editor.putInt(FONT_SIZE, value)
            editor.apply()
        }

    var isNotificationEnabled: Boolean
        get() = sharedPreferences.getBoolean(NOTIFICATION_ENABLED, true)
        set(value) {
            editor.putBoolean(NOTIFICATION_ENABLED, value)
            editor.apply()
        }
}
