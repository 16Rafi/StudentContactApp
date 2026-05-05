package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.databinding.ActivityLoginBinding
import com.example.studentcontactapp.utils.PrefManager

/**
 * Created by R. Rafi Yudi Pramana (F1D02310132)
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)

        if (prefManager.isLoggedIn() && prefManager.isRememberMe()) {
            moveToMainActivity()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val rememberMe = binding.cbRememberMe.isChecked

            if (username == "admin" && password == "123456") {
                prefManager.setLoggedIn(true)
                prefManager.setUsername(username)
                prefManager.setRememberMe(rememberMe)
                
                Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                moveToMainActivity()
            } else {
                Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
