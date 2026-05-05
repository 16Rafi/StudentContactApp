package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.databinding.ActivitySettingsBinding
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsManager: SettingsManager
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsManager = SettingsManager(this)
        prefManager = PrefManager(this)

        // Load saved settings
        binding.switchDarkMode.isChecked = settingsManager.isDarkMode
        binding.etFontSize.setText(settingsManager.fontSize.toString())
        binding.switchNotifications.isChecked = settingsManager.isNotificationEnabled

        // Save settings on change
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isDarkMode = isChecked
            Toast.makeText(this, getString(R.string.dark_mode_status, isChecked), Toast.LENGTH_SHORT).show()
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isNotificationEnabled = isChecked
            Toast.makeText(this, getString(R.string.notifications_status, isChecked), Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            prefManager.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        // Save font size when leaving
        val fontSizeStr = binding.etFontSize.text.toString()
        if (fontSizeStr.isNotEmpty()) {
            settingsManager.fontSize = fontSizeStr.toInt()
        }
    }
}
